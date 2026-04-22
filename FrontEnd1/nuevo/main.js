// FrontEnd1/nuevo/main.js

document.addEventListener('DOMContentLoaded', () => {
    console.log('Bancosol App Initialized');
    

    const boton = document.getElementById('btn-desplegable');
    const menu = document.getElementById('menu-enlaces');

    boton.addEventListener('click', () => {

        menu.classList.toggle('abierto');
        

        if (menu.classList.contains('abierto')) {
            boton.innerHTML = '▲ Ocultar Menú';
        } else {
            boton.innerHTML = '▼ Mostrar Menú';
        }
    });
});



// Función para cargar componentes dinámicos si fuera necesario
function navigateTo(url) {
    window.location.href = url;
}
