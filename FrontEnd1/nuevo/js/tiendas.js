import { fetch_data } from "./utils/fetch.js";

const selectCampanas = document.getElementById('select-filtro-campanas');
const selectCadenas = document.getElementById('filtro-por-cadenas');
const selectLocalidad = document.getElementById('filtro-por-localidad');
const tbody = document.getElementById('tabla-tiendas-body');
const tableElement = document.getElementById('tabla-tiendas');
const placeholder = document.getElementById('tiendas-placeholder');
const tablaTiendas = document.getElementById('tabla-tiendas');

let globalData = {
    tiendas: [],
    cadenas: [],
    campanas: [],
    participa: []
};

function getCadenaName(id) {
    const cadena = globalData.cadenas.find(c => Number(c.id) === Number(id));
    return cadena ? cadena.nombre : 'Desconocida';
}

function getParticipaState(campanaId, tiendaId) {
    return globalData.participa.some(p => 
        Number(p.id_campana) === Number(campanaId) && 
        Number(p.id_tienda) === Number(tiendaId)
    );
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

function modelo_Fila(tienda, participates) {
    const cadenaName = getCadenaName(tienda.id_cadena);
    const statusClass = participates ? 'status-activa' : 'status-inactiva';
    const statusText = participates ? 'Activa' : 'Sin activar';

    return `
        <tr class="clickable" data-cadena="${tienda.id_cadena}" data-localidad="${tienda.localidad.toLowerCase()}">
            <td>${tienda.descripcion}</td>
            <td><input type="checkbox" class="check-participa" ${participates ? 'checked' : ''} data-tienda-id="${tienda.id}"></td>
            <td>${tienda.localidad.toUpperCase()}</td>
            <td>${tienda.domicilio}</td>
            <td>${tienda.c_postal}</td>
            <td>${tienda.zona_geografica}</td>
            <td><span class="status-badge ${statusClass}">${statusText}</span></td>
            <td>
                <button class="btn btn-primary btn-sm">Editar</button>
                <button class="btn btn-danger btn-sm">Borrar</button>
            </td>
        </tr>
    `;
}

function populateDropdowns() {
    // Campañas
    globalData.campanas.forEach(campana => {
        const option = document.createElement('option');
        option.value = campana.id;
        option.textContent = campana.nombre;
        selectCampanas.appendChild(option);
    });

    // Cadenas
    globalData.cadenas.forEach(cadena => {
        const option = document.createElement('option');
        option.value = cadena.id;
        option.textContent = cadena.nombre;
        selectCadenas.appendChild(option);
    });

    // Localidades (únicas de tiendas)
    const localidades = [...new Set(globalData.tiendas.map(t => t.localidad))];
    localidades.forEach(loc => {
        const option = document.createElement('option');
        option.value = loc.toLowerCase();
        option.textContent = loc;
        selectLocalidad.appendChild(option);
    });
}

function renderTable() {
    const selectedCampana = selectCampanas.value;
    const selectedCadena = selectCadenas.value;
    const selectedLocalidad = selectLocalidad.value;

    if (!selectedCampana) {
        tableElement.style.display = 'none';
        placeholder.style.display = 'block';
        tbody.innerHTML = '';
        return;
    }

    placeholder.style.display = 'none';
    tableElement.style.display = 'table';

    tbody.innerHTML = '';

    globalData.tiendas.forEach(tienda => {
        const participates = getParticipaState(selectedCampana, tienda.id);
        
        const matchCadena = (selectedCadena === 'todas' || Number(tienda.id_cadena) === Number(selectedCadena));
        const matchLocalidad = (selectedLocalidad === 'todas' || tienda.localidad.toLowerCase() === selectedLocalidad);

        if (matchCadena && matchLocalidad) {
            const filaHTML = modelo_Fila(tienda, participates);
            tbody.insertAdjacentHTML('beforeend', filaHTML);
        }
    });

    document.querySelectorAll('.check-participa').forEach(checkbox => {
        checkbox.addEventListener('change', (e) => {
            const badge = e.target.closest('tr').querySelector('.status-badge');
            updateBadge(badge, e.target.checked);
        });
    });
}

function initialize(data) {
    globalData.tiendas = data[0];
    globalData.cadenas = data[1];
    globalData.campanas = data[2];
    globalData.participa = data[3];

    populateDropdowns();
    
    // Event listeners
    selectCampanas.addEventListener('change', renderTable);
    selectCadenas.addEventListener('change', renderTable);
    selectLocalidad.addEventListener('change', renderTable);

    renderTable();
}

Promise.all([
    fetch_data('tiendas'),
    fetch_data('cadenas'),
    fetch_data('campanas'),
    fetch_data('participa')
]).then(initialize).catch(err => console.error(err));
