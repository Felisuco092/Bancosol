document.addEventListener('DOMContentLoaded', () => {
    const botonesFiltro = document.querySelectorAll('.filter-btn');
    const filasTiendas = document.querySelectorAll('#tabla-tiendas-body tr');

    // Al hacer clic en un botón de filtro
    botonesFiltro.forEach(boton => {
        boton.addEventListener('click', () => {
            // Parte visual
            botonesFiltro.forEach(b => b.classList.remove('active'));
            boton.classList.add('active');

            // Parte lógica
            const filtroSeleccionado = boton.getAttribute('data-filtro');

            // Recorremos cada fila una por una
            filasTiendas.forEach(fila => {
                const checkbox = fila.querySelector('input[type="checkbox"]');
                
                if (!checkbox) return; 

                const estaActiva = checkbox.checked;

                if (filtroSeleccionado === 'todas') {
                    fila.style.display = '';
                } 
                else if (filtroSeleccionado === 'activas' && estaActiva === true) {
                    fila.style.display = '';
                } 
                else if (filtroSeleccionado === 'sin-activar' && estaActiva === false) {
                    fila.style.display = ''; 
                } 
                else {
                    fila.style.display = 'none'; 
                }
            });
        });
    });
});