import { fetch_data } from "./utils/fetch.js";

const filterTipo = document.getElementById('filter-tipo');
const filterLocalidad = document.getElementById('filter-localidad');
const tbody = document.getElementById('colaboradores-tbody');


// Función que comprueba el booleano y devuelve el estilo (badge)
<<<<<<< HEAD
function formatTipo(esPersona, esPendiente) {
    if(esPendiente === "true") {
        return '<span class="badge badge-confirmar">Por confirmar</span>';
    } else if (esPersona === "true") {
=======
function formatTipo(esPersona) {
    if (esPersona === "true") {
>>>>>>> b05fb8c6684e5a13ad881728aab92cc6470b7677
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
<<<<<<< HEAD
        const esPendiente = row.getAttribute('pendiente');
        console.log("Es persona: " + esPersona)
        const cell = row.querySelector('.tipo-cell');
        if (cell) {
            cell.innerHTML = formatTipo(esPersona, esPendiente);
=======
        const cell = row.querySelector('.tipo-cell');
        if (cell) {
            cell.innerHTML = formatTipo(esPersona);
>>>>>>> b05fb8c6684e5a13ad881728aab92cc6470b7677
        }
    });
}

function filterRows() {
    const tipoValue = filterTipo.value;
<<<<<<< HEAD
=======
    console.log(tipoValue)
>>>>>>> b05fb8c6684e5a13ad881728aab92cc6470b7677
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

<<<<<<< HEAD
function modelo_Fila(colaborador, pendiente = false) {
    return `<tr pendiente="${pendiente}"
                data-es-persona="${colaborador.persona_fisica}" data-localidad="${colaborador.localidad}">
=======
function modelo_Fila(colaborador) {
    return `<tr data-es-persona="${colaborador.persona_fisica}" data-localidad="${colaborador.localidad}">
>>>>>>> b05fb8c6684e5a13ad881728aab92cc6470b7677
                        <td>${colaborador.nombre_entidad}</td>
                        <td class="tipo-cell"></td>
                        <td>${colaborador.localidad}</td>
                        <td>${colaborador.codigo_postal}</td>
                        <td>${colaborador.n_voluntarios}</td>
                        <td>C${colaborador.observaciones}</td>
                        <td><button class="btn btn-primary btn-sm">Editar</button> <button class="btn btn-danger btn-sm">Borrar</button></td>
                    </tr>`
}

<<<<<<< HEAD
function solicitud_colaboradores(data, dataFisico, dataEntidad) {
    const fisicoIds = new Set(dataFisico.map(v => v.id_voluntario));
    const entidadIds = new Set(dataEntidad.map(v => v.id_voluntario));

    function getVoluntarioType(id) {
        const NumberId = Number(id)
        if (fisicoIds.has(NumberId)) return 'fisico';
        if (entidadIds.has(NumberId)) return 'entidad';
        return 'desconocido';
    }

    data.forEach(element => {
        if(element.aprobado) {
            element.persona_fisica = getVoluntarioType(element.id) === 'fisico' ? true : false;
            tbody.insertAdjacentHTML('beforeend', modelo_Fila(element))
        } else {
            tbody.insertAdjacentHTML('beforeend', modelo_Fila(element, true))
        }
        
=======
function solicitud_colaboradores(data) {
    data.forEach(element => {
        tbody.insertAdjacentHTML('beforeend', modelo_Fila(element))
>>>>>>> b05fb8c6684e5a13ad881728aab92cc6470b7677
    })

    if (filterTipo) filterTipo.addEventListener('change', filterRows);
    if (filterLocalidad) filterLocalidad.addEventListener('change', filterRows);

    // Inicializar estilos al cargar
    updateTableStyles();
}

<<<<<<< HEAD

Promise.all([
     fetch_data('voluntario_base'),
     fetch_data('voluntario_fisico'),
     fetch_data('voluntario_entidad')
   ])
   .then(([voluntariosBase, voluntariosFisico, voluntariosEntidad]) => {
     solicitud_colaboradores(voluntariosBase, voluntariosFisico, voluntariosEntidad)
   })
   .catch(e => {
    console.error(e);
   });
=======
const colaboradores = fetch_data('voluntarios', "No se ha podido obtener los voluntarios")
    .then(solicitud_colaboradores)


>>>>>>> b05fb8c6684e5a13ad881728aab92cc6470b7677


