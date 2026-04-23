// FrontEnd1/nuevo/main.js

document.addEventListener('DOMContentLoaded', () => {
    console.log('Bancosol App Initialized');
    
    // Simulación de protección de rutas (excepto index.html que es login)
    const isLoginPage = window.location.pathname.endsWith('index.html') || window.location.pathname === '/FrontEnd1/nuevo/';
    const user = sessionStorage.getItem('user');

    if (!isLoginPage && !user) {
        // window.location.href = 'index.html';
    }

    // Lógica para cerrar sesión
    const logoutBtn = document.getElementById('logout-btn');
    if (logoutBtn) {
        logoutBtn.addEventListener('click', () => {
            sessionStorage.removeItem('user');
            window.location.href = 'index.html';
        });
    }
    const boton = document.getElementById('btn-desplegable');
    const sidebar = document.querySelector('.sidebar');
    const enlaces = document.getElementById('menu-enlaces');
    const mainContent = document.getElementsByClassName('main-content');

    if (boton && sidebar) {
        boton.addEventListener('click', () => {
            sidebar.classList.toggle('abierto');
            
            if (sidebar.classList.contains('abierto')) {
                boton.innerHTML = '✕';
                enlaces.style.display = 'block';
                mainContent[0].style.marginLeft = '250px';
            } else {
                boton.innerHTML = '☰';
                enlaces.style.display = 'none';
                mainContent[0].style.marginLeft = '0px';
            }
        });
    }
});



// Función para cargar componentes dinámicos si fuera necesario
function navigateTo(url) {
    window.location.href = url;
}
