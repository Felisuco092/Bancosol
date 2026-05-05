import { fetch_data } from "./utils/fetch.js";

const selectCampanas = document.getElementById('select-filtro-campanas');
const tbody = document.getElementById('chain-table-body');
const tableElement = document.getElementById('tabla-cadenas');
const placeholder = document.getElementById('cadenas-placeholder');
const filterButtons = document.querySelectorAll('.filter-btn');

let currentFilter = 'all';

let globalData = {
    tiendas: [],
    cadenas: [],
    campanas: [],
    participa: []
};

/**
 * Determina si una cadena participa en una campaña específica.
 * Una cadena participa si al menos una de sus tiendas está en la tabla 'participa' para esa campaña.
 */
function checkChainParticipation(cadenaId, campanaId) {
    if (!campanaId) return false;
    
    // Obtener todas las tiendas de esta cadena
    const tiendasDeCadena = globalData.tiendas.filter(t => Number(t.id_cadena) === Number(cadenaId));
    const idsTiendasDeCadena = tiendasDeCadena.map(t => t.id);

    // Comprobar si alguna de esas tiendas participa en la campaña
    return globalData.participa.some(p => 
        Number(p.id_campana) === Number(campanaId) && 
        idsTiendasDeCadena.includes(p.id_tienda)
    );
}

function modelo_Fila(cadena, isParticipating) {
    const statusClass = isParticipating ? 'status-activa' : 'status-inactiva';
    const statusText = isParticipating ? 'Participando' : 'Sin participación';

    return `
        <tr>
            <td><strong>${cadena.nombre}</strong></td>
            <td><span class="status-badge ${statusClass}">${statusText}</span></td>
            <td>
                <button class="btn btn-primary btn-sm">Editar</button>
                <button class="btn btn-danger btn-sm">Eliminar</button>
            </td>
        </tr>
    `;
}

function populateDropdowns() {
    globalData.campanas.forEach(campana => {
        const option = document.createElement('option');
        option.value = campana.id;
        option.textContent = campana.nombre;
        selectCampanas.appendChild(option);
    });
}

function renderTable() {
    const selectedCampana = selectCampanas.value;

    if (!selectedCampana) {
        tableElement.style.display = 'none';
        placeholder.style.display = 'block';
        tbody.innerHTML = '';
        return;
    }

    placeholder.style.display = 'none';
    tableElement.style.display = 'table';
    tbody.innerHTML = '';

    globalData.cadenas.forEach(cadena => {
        const isParticipating = checkChainParticipation(cadena.id, selectedCampana);
        
        // Aplicar filtro de botones (Todas / Activas)
        if (currentFilter === 'activa' && !isParticipating) {
            return;
        }

        const filaHTML = modelo_Fila(cadena, isParticipating);
        tbody.insertAdjacentHTML('beforeend', filaHTML);
    });

    if (tbody.innerHTML === '') {
        tbody.innerHTML = '<tr><td colspan="3" class="text-center">No se encontraron cadenas con los filtros seleccionados</td></tr>';
    }
}

function initialize(data) {
    globalData.tiendas = data[0];
    globalData.cadenas = data[1];
    globalData.campanas = data[2];
    globalData.participa = data[3];

    populateDropdowns();
    
    // Event listeners
    selectCampanas.addEventListener('change', renderTable);
    
    filterButtons.forEach(btn => {
        btn.addEventListener('click', () => {
            filterButtons.forEach(b => b.classList.remove('active'));
            btn.classList.add('active');
            currentFilter = btn.dataset.filter;
            renderTable();
        });
    });

    renderTable();
}

// Carga inicial de datos
Promise.all([
    fetch_data('tiendas'),
    fetch_data('cadenas'),
    fetch_data('campanas'),
    fetch_data('participa')
]).then(initialize).catch(err => {
    console.error("Error al cargar datos:", err);
    placeholder.textContent = "Error al cargar los datos. Por favor, intente de nuevo.";
});
