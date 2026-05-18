/**
 * login.js — Simple frontend-only login
 * 
 * - No backend required
 * - No Tomcat required
 * - Redirects directly to dashboard.html
 */

document.addEventListener('DOMContentLoaded', function () {

    const loginForm = document.getElementById('loginForm');
    const loginBtn = document.getElementById('loginBtn');

    loginForm.addEventListener('submit', function (e) {
        e.preventDefault();

        // Get input values
        const username = document.getElementById('username').value.trim();
        const password = document.getElementById('password').value.trim();

        // Simple validation
        if (!username || !password) {
            alert('Please enter username and password.');
            return;
        }

        // Loading state
        loginBtn.disabled = true;
        loginBtn.innerHTML = 'Signing In...';

        // Save demo session
        sessionStorage.setItem('isLoggedIn', 'true');
        sessionStorage.setItem('fullName', username);

        // Redirect to dashboard
        setTimeout(() => {
            window.location.href = 'dashboard.html';
        }, 500);
    });

});