document.addEventListener('DOMContentLoaded', function() {
    const filterTipo = document.getElementById('filter-tipo');
    const filterLocalidad = document.getElementById('filter-localidad');
    const rows = document.querySelectorAll('tbody tr');

    function filterRows() {
        const tipoValue = filterTipo.value;
        const localidadValue = filterLocalidad.value;

        rows.forEach(row => {
            const rowTipo = row.getAttribute('data-tipo');
            const rowLocalidad = row.getAttribute('data-localidad');

            const showByTipo = tipoValue === 'all' || rowTipo === tipoValue;
            const showByLocalidad = localidadValue === 'all' || rowLocalidad === localidadValue;

            row.style.display = (showByTipo && showByLocalidad) ? '' : 'none';
        });
    }

    filterTipo.addEventListener('change', filterRows);
    filterLocalidad.addEventListener('change', filterRows);
});