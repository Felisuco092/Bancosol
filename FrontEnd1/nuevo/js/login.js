document.getElementById('login-form').addEventListener('submit', (e) => {
    e.preventDefault();
    // Simulación simple para navegación
    sessionStorage.setItem('user', 'admin');
    window.location.href = 'dashboard.html';
});
