import { fetch_data } from "./utils/fetch.js";

const selectCampana = document.getElementById('select-campana');
const selectTienda = document.getElementById('select-tienda');
const btnBuscar = document.getElementById('btn-buscar');
const cuadranteContainer = document.getElementById('cuadrante-container');
const tablaTurnosBody = document.getElementById('tabla-turnos-body');
const capitanNombre = document.getElementById('capitan-nombre');

let globalData = {
    turnos: [],
    campanas: [],
    tiendas: [],
    voluntarioBase: [],
    voluntarioEntidad: [],
    voluntarioFisico: [],
    usuarios: []
};

function getVoluntarioDisplay(idVoluntario) {
    const fisico = globalData.voluntarioFisico.find(v => Number(v.id_voluntario) === Number(idVoluntario));
    if (fisico) {
        return `${fisico.nombre} ${fisico.apellidos}`;
    }

    const entidad = globalData.voluntarioEntidad.find(v => Number(v.id_voluntario) === Number(idVoluntario));
    if (entidad) {
        return `${entidad.nombre_asociacion} (${entidad.n_voluntarios})`;
    }

    return `Voluntario #${idVoluntario}`;
}

function getUsuarioName(id) {
    const user = globalData.usuarios.find(u => Number(u.id) === Number(id));
    return user ? `${user.nombre} ${user.apellidos}` : 'No asignado';
}

function modelo_Fila(turno) {
    const voluntarioDisplay = getVoluntarioDisplay(turno.id_voluntario);
    
    // Format date nicely (assuming YYYY-MM-DD)
    const fecha = new Date(turno.dia);
    const options = { weekday: 'long', day: '2-digit', month: '2-digit' };
    const fechaFormateada = fecha.toLocaleDateString('es-ES', options);

    return `
        <tr>
            <td style="text-transform: capitalize;">${fechaFormateada}</td>
            <td>${turno.hora_inicio}</td>
            <td>${turno.hora_fin}</td>
            <td>
                <div class="voluntarios-cell">
                    <span class="voluntario-tag">${voluntarioDisplay} <button class="btn-remove">×</button></span>
                    <button class="btn btn-sm btn-add">+ Añadir</button>
                </div>
            </td>
            <td>
                <button class="btn btn-danger btn-incidence">Incidencia</button>
            </td>
        </tr>
    `;
}

function populateDropdowns() {
    globalData.campanas.forEach(campana => {
        const option = document.createElement('option');
        option.value = campana.id;
        option.textContent = campana.nombre;
        selectCampana.appendChild(option);
    });

    globalData.tiendas.forEach(tienda => {
        const option = document.createElement('option');
        option.value = tienda.id;
        option.textContent = tienda.descripcion;
        selectTienda.appendChild(option);
    });
}

function handleBuscarClick() {
    const campanaId = selectCampana.value;
    const tiendaId = selectTienda.value;

    if (!campanaId || !tiendaId) {
        alert('Por favor, seleccione una campaña y una tienda.');
        return;
    }

    // Filter turnos
    const filteredTurnos = globalData.turnos.filter(t => 
        Number(t.id_campana) === Number(campanaId) && 
        Number(t.id_tienda) === Number(tiendaId)
    );

    // Show captain
    const tienda = globalData.tiendas.find(t => Number(t.id) === Number(tiendaId));
    if (tienda && tienda.id_capitan) {
        capitanNombre.textContent = getUsuarioName(tienda.id_capitan);
    } else {
        capitanNombre.textContent = 'Sin asignar';
    }

    // Render table
    tablaTurnosBody.innerHTML = '';
    if (filteredTurnos.length === 0) {
        tablaTurnosBody.innerHTML = '<tr><td colspan="5" style="text-align: center;">No hay turnos registrados para esta selección.</td></tr>';
    } else {
        filteredTurnos.forEach(turno => {
            tablaTurnosBody.insertAdjacentHTML('beforeend', modelo_Fila(turno));
        });
    }

    cuadranteContainer.style.display = 'block';
}

function initialize(data) {
    globalData.turnos = data[0];
    globalData.campanas = data[1];
    globalData.tiendas = data[2];
    globalData.voluntarioBase = data[3];
    globalData.voluntarioEntidad = data[4];
    globalData.voluntarioFisico = data[5];
    globalData.usuarios = data[6];

    populateDropdowns();
    btnBuscar.addEventListener('click', handleBuscarClick);
}

Promise.all([
    fetch_data('turnos'),
    fetch_data('campanas'),
    fetch_data('tiendas'),
    fetch_data('voluntario_base'),
    fetch_data('voluntario_entidad'),
    fetch_data('voluntario_fisico'),
    fetch_data('usuarios')
]).then(initialize).catch(err => console.error(err));
