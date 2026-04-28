document.addEventListener('DOMContentLoaded', () => {
    
    const botonesFiltro = document.querySelectorAll('.filter-btn');
    const filasTiendas = document.querySelectorAll('#tabla-tiendas-body tr');

    filasTiendas.forEach(fila => {
        const checkbox = fila.querySelector('input[type="checkbox"]');
        const etiqueta = fila.querySelector('.status-badge');

        // Cambiar la etiqueta al hacer clic en el checkbox
        if (checkbox && etiqueta) {
            // Escuchamos el evento 'change' (cuando se marca o desmarca)
            checkbox.addEventListener('change', () => {
                if (checkbox.checked) {
                    // Si se ha marcado -> Verde y "Activa"
                    etiqueta.textContent = 'Activa';
                    etiqueta.classList.remove('status-inactiva');
                    etiqueta.classList.add('status-activa');
                } else {
                    // Si se ha desmarcado -> Rojo y "Sin activar"
                    etiqueta.textContent = 'Sin activar';
                    etiqueta.classList.remove('status-activa');
                    etiqueta.classList.add('status-inactiva');
                }
            });
        }
    });

    // Los botones de filtro: "Todas", "Activas", "Sin activar" (eliminado previamente)
    /*botonesFiltro.forEach(boton => {
        boton.addEventListener('click', () => {
            // Cambiar color del botón activo
            botonesFiltro.forEach(b => b.classList.remove('active'));
            boton.classList.add('active');

            const filtroSeleccionado = boton.getAttribute('data-filtro');

            filasTiendas.forEach(fila => {
                const checkbox = fila.querySelector('input[type="checkbox"]');
                if (!checkbox) return; 

                const estaActiva = checkbox.checked;

                if (filtroSeleccionado === 'todas') {
                    fila.style.display = ''; 
                } else if (filtroSeleccionado === 'activas' && estaActiva) {
                    fila.style.display = ''; 
                } else if (filtroSeleccionado === 'sin-activar' && !estaActiva) {
                    fila.style.display = ''; 
                } else {
                    fila.style.display = 'none'; 
                }
            });
        });
    });*/

    const selectCampanas = document.getElementById('select-filtro-campanas');
    if (selectCampanas) {
        selectCampanas.addEventListener('change', () => {
            const campanaSeleccionada = selectCampanas.value;
            console.log('Filtrando por campaña:', campanaSeleccionada);
      
             //Actualmente las filas no tienen datos de campaña asignados.
                   // Si tuvieran un data-campana, se filtraría así:
                  /*
            filasTiendas.forEach(fila => {
                const campanaFila = fila.getAttribute('data-campana');
                if (campanaSeleccionada === 'todas' || campanaFila === campanaSeleccionada) {
                    fila.style.display = '';
                } else {
                    fila.style.display = 'none';
                }
            });*/
      
            if (campanaSeleccionada === 'todas') {
                filasTiendas.forEach(fila => fila.style.display = '');
            }
        });
    }
});