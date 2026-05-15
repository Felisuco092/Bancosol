import { fetch_data } from "./utils/fetch.js";

const filterTipo = document.getElementById('filter-tipo');
const filterLocalidad = document.getElementById('filter-localidad');
const tbody = document.getElementById('colaboradores-tbody');


// Función que comprueba el booleano y devuelve el estilo (badge)
function formatTipo(esPersona, esPendiente) {
    if(esPendiente === "true") {
        return '<span class="badge badge-confirmar">Por confirmar</span>';
    } else if (esPersona === "true") {
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
        const esPendiente = row.getAttribute('pendiente');
        const cell = row.querySelector('.tipo-cell');
        if (cell) {
            cell.innerHTML = formatTipo(esPersona, esPendiente);
        }
    });
}

function filterRows() {
    const tipoValue = filterTipo.value;
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

function modelo_Fila(colaborador, pendiente = false) {
    return `<tr pendiente="${pendiente}"
                data-es-persona="${colaborador.persona_fisica}" data-localidad="${colaborador.localidad}">
                        <td>${colaborador.nombre}</td>
                        <td class="tipo-cell"></td>
                        <td>${colaborador.zona_geografica}</td>
                        <td>${colaborador.codigo_postal}</td>
                        <td>${colaborador.n_voluntarios}</td>
                        <td>${colaborador.observaciones}</td>
                        <td><button class="btn btn-primary btn-sm">Editar</button> <button class="btn btn-danger btn-sm">Borrar</button></td>
                    </tr>`
}



function solicitud_colaboradores(data, dataFisico, dataEntidad) {
    const fisicoIds = new Set(dataFisico.map(v => v.id_voluntario));
    const entidadIds = new Set(dataEntidad.map(v => v.id_voluntario));

    function json_change(json, arrayJsonFisico, arrayJsonEntidad) {
        //Esto es para hacer una especie de left join con arrayJsonFisico o arrayJsonEntidad el voluntario
        const type = getVoluntarioType(json.id);
        if(type === 'fisico'){
            const json_fisico = arrayJsonFisico.find((element) => {
                return element.id === json.id;
            })
            return {...json, 
                nombre: json_fisico.nombre + " " + json_fisico.apellidos,
                persona_fisica: true,
                n_voluntarios:1
            }
        } else {
            const json_entidad = arrayJsonEntidad.find((element) => {
                return element.id === json.id;
            })
            
            return {...json,
                nombre: json_entidad.nombre_asociacion,
                persona_fisica: false,
                n_voluntarios: json_entidad.n_voluntarios
            }
        }
    }

    function getVoluntarioType(id) {
        const StringId = String(id)
        if (fisicoIds.has(StringId)) return 'fisico';
        if (entidadIds.has(StringId)) return 'entidad';
        return 'desconocido';
    }

    data.forEach(element => {
        if(element.aprobado) {
            element = json_change(element, dataFisico, dataEntidad)
            tbody.insertAdjacentHTML('beforeend', modelo_Fila(element))
        } else {
            element = json_change(element, dataFisico, dataEntidad)
            tbody.insertAdjacentHTML('beforeend', modelo_Fila(element, true))
        }
        
    })

    if (filterTipo) filterTipo.addEventListener('change', filterRows);
    if (filterLocalidad) filterLocalidad.addEventListener('change', filterRows);

    // Inicializar estilos al cargar
    updateTableStyles();
}


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


