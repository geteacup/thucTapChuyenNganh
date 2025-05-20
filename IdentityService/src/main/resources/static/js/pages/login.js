// login.js
import authService from '../services/auth-service';

document.addEventListener('DOMContentLoaded', () => {
    const loginForm = document.getElementById('loginForm');
    const errorMessage = document.getElementById('errorMessage');

    loginForm.addEventListener('submit', async (e) => {
        e.preventDefault();
        
        const username = document.getElementById('username').value;
        const password = document.getElementById('password').value;
        
        try {
            const result = await authService.login(username, password);
            if (result.authenticated) {
                // Redirect to dashboard or home page after successful login
                window.location.href = '/dashboard';
            } else {
                errorMessage.textContent = 'Đăng nhập không thành công';
                errorMessage.style.display = 'block';
            }
        } catch (error) {
            errorMessage.textContent = error.message || 'Có lỗi xảy ra khi đăng nhập';
            errorMessage.style.display = 'block';
        }
    });
}); 