// auth-service.js
class AuthService {
    constructor() {
        this.baseUrl = '/auth';
        this.token = localStorage.getItem('token');
    }

    getHeaders() {
        const headers = {
            'Content-Type': 'application/json'
        };
        if (this.token) {
            headers['Authorization'] = `Bearer ${this.token}`;
        }
        return headers;
    }

    async handleResponse(response) {
        const data = await response.json();
        if (!response.ok) {
            if (response.status === 401) {
                this.logout();
            }
            throw new Error(data.message || 'Có lỗi xảy ra');
        }
        return data;
    }

    async login(username, password) {
        try {
            const response = await fetch(`${this.baseUrl}/login`, {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify({ username, password })
            });

            const data = await this.handleResponse(response);
            if (data.result && data.result.token) {
                this.token = data.result.token;
                localStorage.setItem('token', data.result.token);
                return data.result;
            }
            throw new Error('Token không hợp lệ');
        } catch (error) {
            console.error('Login error:', error);
            throw error;
        }
    }

    async introspect() {
        try {
            const response = await fetch(`${this.baseUrl}/introspect`, {
                method: 'POST',
                headers: this.getHeaders(),
                body: JSON.stringify({ token: this.token })
            });

            const data = await this.handleResponse(response);
            return data.result.valid;
        } catch (error) {
            console.error('Introspect error:', error);
            throw error;
        }
    }

    async refreshToken() {
        try {
            const response = await fetch(`${this.baseUrl}/refresh`, {
                method: 'POST',
                headers: this.getHeaders(),
                body: JSON.stringify({ token: this.token })
            });

            const data = await this.handleResponse(response);
            if (data.result && data.result.token) {
                this.token = data.result.token;
                localStorage.setItem('token', data.result.token);
                return data.result;
            }
            throw new Error('Không thể làm mới token');
        } catch (error) {
            console.error('Refresh token error:', error);
            throw error;
        }
    }

    async logout() {
        try {
            if (this.token) {
                await fetch(`${this.baseUrl}/logout`, {
                    method: 'POST',
                    headers: this.getHeaders(),
                    body: JSON.stringify({ token: this.token })
                });
            }
        } catch (error) {
            console.error('Logout error:', error);
        } finally {
            this.token = null;
            localStorage.removeItem('token');
        }
    }

    isAuthenticated() {
        return !!this.token;
    }

    getToken() {
        return this.token;
    }
}

export default new AuthService(); 