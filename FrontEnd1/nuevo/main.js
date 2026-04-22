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
    //¿Lógica para el despliegue del menú sidebar?
    /*const boton = document.getElementById('btn-desplegable');
    const menu = document.getElementById('menu-enlaces');

    boton.addEventListener('click', () => {

        menu.classList.toggle('abierto');
        

        if (menu.classList.contains('abierto')) {
            boton.innerHTML = '▲ Ocultar Menú';
        } else {
            boton.innerHTML = '▼ Mostrar Menú';
        }
    });
    */
});



// Función para cargar componentes dinámicos si fuera necesario
function navigateTo(url) {
    window.location.href = url;
}
