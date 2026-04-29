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
            // Usamos dataset para mayor claridad y seguridad
            const rowCadena = fila.dataset.cadena || '';
            const rowLocalidad = fila.dataset.localidad || '';

            // Lógica de filtrado: si es 'todas' o coincide con el valor seleccionado
            const matchCadena = (cadena === 'todas' || rowCadena === cadena);
            const matchLocalidad = (localidad === 'todas' || rowLocalidad === localidad);

            if (matchCadena && matchLocalidad) {
                fila.style.display = '';
                
                // Actualizar estado visual según la campaña seleccionada
                const checkbox = fila.querySelector('.check-participa');
                const badge = fila.querySelector('.status-badge');
                
                if (checkbox) {
                    const isChecked = !!(participationState[campana] && participationState[campana][index]);
                    checkbox.checked = isChecked;
                    if (badge) {
                        updateBadge(badge, isChecked);
                    }
                }
            } else {
                fila.style.display = 'none';
            }
        });
    }

    function updateBadge(badge, isActive) {
        if (!badge) return;
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

        if (checkbox) {
            checkbox.addEventListener('change', () => {
                const campana = selectCampanas.value;
                // Asegurar que el objeto de la campaña existe
                if (!participationState[campana]) {
                    participationState[campana] = {};
                }
                // Guardar el estado en memoria para la campaña actual
                participationState[campana][index] = checkbox.checked;
                if (badge) {
                    updateBadge(badge, checkbox.checked);
                }
            });
        }
    });

    // Eventos de cambio en filtros
    selectCampanas.addEventListener('change', applyFilters);
    selectCadenas.addEventListener('change', applyFilters);
    selectLocalidad.addEventListener('change', applyFilters);

    // Inicializar
    applyFilters();
});
