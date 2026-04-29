document.addEventListener('DOMContentLoaded', () => {
    const selectCampanas = document.getElementById('select-filtro-campanas');
    const selectCadenas = document.getElementById('filtro-por-cadenas');
    const selectLocalidad = document.getElementById('filtro-por-localidad');
    const filasTiendas = document.querySelectorAll('#tabla-tiendas-body tr');

    // Simulación de estado de participación por campaña
    // Esto guardará qué tiendas están marcadas en cada campaña
    const participationState = {
        'campana-gran-recogida': {},
        'campana-navidad': {},
        'operacion-kilo': {}
    };

    function applyFilters() {
        const campana = selectCampanas.value;
        const cadena = selectCadenas.value;
        const localidad = selectLocalidad.value;

        filasTiendas.forEach((fila, index) => {
            const matchCadena = (cadena === 'todas' || fila.getAttribute('data-cadena') === cadena);
            const matchLocalidad = (localidad === 'todas' || fila.getAttribute('data-localidad') === localidad);

            if (matchCadena && matchLocalidad) {
                fila.style.display = '';
                // Cargar el estado del checkbox para esta campaña
                const checkbox = fila.querySelector('.check-participa');
                const badge = fila.querySelector('.status-badge');
                
                checkbox.checked = participationState[campana][index] || false;
                updateBadge(badge, checkbox.checked);
            } else {
                fila.style.display = 'none';
            }
        });
    }

    function updateBadge(badge, isActive) {
        if (isActive) {
            badge.textContent = 'Activa';
            badge.classList.remove('status-inactiva');
            badge.classList.add('status-activa');
        } else {
            badge.textContent = 'Sin activar';
            badge.classList.remove('status-activa');
            badge.classList.add('status-inactiva');
        }
    }

    // Escuchar cambios en los checkboxes de participación
    filasTiendas.forEach((fila, index) => {
        const checkbox = fila.querySelector('.check-participa');
        const badge = fila.querySelector('.status-badge');

        checkbox.addEventListener('change', () => {
            const campana = selectCampanas.value;
            // Guardar el estado en memoria para la campaña actual
            participationState[campana][index] = checkbox.checked;
            updateBadge(badge, checkbox.checked);
        });
    });

    // Eventos de cambio en filtros
    selectCampanas.addEventListener('change', applyFilters);
    selectCadenas.addEventListener('change', applyFilters);
    selectLocalidad.addEventListener('change', applyFilters);

    // Inicializar
    applyFilters();
});
