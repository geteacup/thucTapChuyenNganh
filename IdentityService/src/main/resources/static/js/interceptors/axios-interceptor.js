// axios-interceptor.js
import axios from 'axios';
import authService from '../services/auth-service';

const setupAxiosInterceptors = () => {
    axios.interceptors.request.use(
        (config) => {
            if (authService.isAuthenticated()) {
                config.headers.Authorization = `Bearer ${authService.getToken()}`;
            }
            return config;
        },
        (error) => {
            return Promise.reject(error);
        }
    );

    axios.interceptors.response.use(
        (response) => response,
        async (error) => {
            const originalRequest = error.config;
            if (error.response.status === 401 && !originalRequest._retry) {
                originalRequest._retry = true;
                try {
                    const newToken = await authService.refreshToken();
                    originalRequest.headers.Authorization = `Bearer ${newToken}`;
                    return axios(originalRequest);
                } catch (refreshError) {
                    authService.logout();
                    throw refreshError;
                }
            }
            return Promise.reject(error);
        }
    );
};

export default setupAxiosInterceptors; 