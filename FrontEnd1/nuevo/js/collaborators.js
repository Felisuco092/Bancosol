import { fetch_data } from "./utils/fetch.js";

const filterTipo = document.getElementById('filter-tipo');
const filterLocalidad = document.getElementById('filter-localidad');
const tbody = document.getElementById('colaboradores-tbody');


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
    const rows = document.querySelectorAll('#colaboradores-tbody tr');
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
    console.log(tipoValue)
    const localidadValue = filterLocalidad.value;
    const rows = document.querySelectorAll('#colaboradores-tbody tr');

    rows.forEach(row => {
        // Usamos data-es-persona para el filtrado por tipo
        const rowTipo = row.getAttribute('data-es-persona');
        const rowLocalidad = row.getAttribute('data-localidad');

        const showByTipo = tipoValue === 'all' || rowTipo === tipoValue;
        const showByLocalidad = localidadValue === 'all' || rowLocalidad === localidadValue;

        row.style.display = (showByTipo && showByLocalidad) ? '' : 'none';
    });
}

//Fetch de los datos

function modelo_Fila(colaborador) {
    return `<tr data-es-persona="${colaborador.persona_fisica}" data-localidad="${colaborador.localidad}">
                        <td>${colaborador.nombre_entidad}</td>
                        <td class="tipo-cell"></td>
                        <td>${colaborador.localidad}</td>
                        <td>${colaborador.codigo_postal}</td>
                        <td>${colaborador.n_voluntarios}</td>
                        <td>C${colaborador.observaciones}</td>
                        <td><button class="btn btn-primary btn-sm">Editar</button> <button class="btn btn-danger btn-sm">Borrar</button></td>
                    </tr>`
}

function solicitud_colaboradores(data) {
    data.forEach(element => {
        tbody.insertAdjacentHTML('beforeend', modelo_Fila(element))
    })

    if (filterTipo) filterTipo.addEventListener('change', filterRows);
    if (filterLocalidad) filterLocalidad.addEventListener('change', filterRows);

    // Inicializar estilos al cargar
    updateTableStyles();
}

const colaboradores = fetch_data('voluntarios', "No se ha podido obtener los voluntarios")
    .then(solicitud_colaboradores)




