// app.js
import setupAxiosInterceptors from './interceptors/axios-interceptor';
import authService from './services/auth-service';

// Thiết lập interceptors
setupAxiosInterceptors();

// Kiểm tra authentication khi load trang
document.addEventListener('DOMContentLoaded', async () => {
    if (authService.isAuthenticated()) {
        try {
            const isValid = await authService.introspect();
            if (!isValid) {
                authService.logout();
                if (!window.location.pathname.includes('/login')) {
                    window.location.href = '/login';
                }
            }
        } catch (error) {
            console.error('Authentication check failed:', error);
            authService.logout();
            if (!window.location.pathname.includes('/login')) {
                window.location.href = '/login';
            }
        }
    } else if (!window.location.pathname.includes('/login')) {
        window.location.href = '/login';
    }
}); 