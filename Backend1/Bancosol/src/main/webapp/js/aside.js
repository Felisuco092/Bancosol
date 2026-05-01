document.addEventListener('DOMContentLoaded', () => {
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
