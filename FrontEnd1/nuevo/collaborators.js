document.addEventListener('DOMContentLoaded', function() {
    const filterTipo = document.getElementById('filter-tipo');
    const filterLocalidad = document.getElementById('filter-localidad');
    const tbody = document.getElementById('colaboradores-tbody');
    const rows = document.querySelectorAll('#colaboradores-tbody tr');

    // Función que comprueba el booleano y devuelve el estilo (badge)
    function formatTipo(esPersona) {
        if (esPersona === "true") {
            return '<span class="badge badge-persona">Persona Física</span>';
        } else {
            return '<span class="badge badge-entidad">Entidad / Grupo</span>';
        }
    }

    // Aplicar el formato inicial a todas las celdas de tipo
    function updateTableStyles() {
        rows.forEach(row => {
            const esPersona = row.getAttribute('data-es-persona');
            const cell = row.querySelector('.tipo-cell');
            if (cell) {
                cell.innerHTML = formatTipo(esPersona);
            }
        });
    }

    function filterRows() {
        const tipoValue = filterTipo.value;
        const localidadValue = filterLocalidad.value;

        rows.forEach(row => {
            // Usamos data-es-persona para el filtrado por tipo
            const rowTipo = row.getAttribute('data-es-persona');
            const rowLocalidad = row.getAttribute('data-localidad');

            const showByTipo = tipoValue === 'all' || rowTipo === tipoValue;
            const showByLocalidad = localidadValue === 'all' || rowLocalidad === localidadValue;

            row.style.display = (showByTipo && showByLocalidad) ? '' : 'none';
        });
    }

    if (filterTipo) filterTipo.addEventListener('change', filterRows);
    if (filterLocalidad) filterLocalidad.addEventListener('change', filterRows);

    // Inicializar estilos al cargar
    updateTableStyles();
});