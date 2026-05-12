import { fetch_data } from "./utils/fetch.js";

const tbody = document.getElementById('chain-table-body');

let globalData = {
    cadenas: []
};

function modelo_Fila(cadena) {
    return `
        <tr>
            <td><strong>${cadena.nombre}</strong></td>
            <td>${cadena.codigo || 'N/A'}</td>
            <td>
                <button class="btn btn-primary btn-sm">Editar</button>
                <button class="btn btn-danger btn-sm">Eliminar</button>
            </td>
        </tr>
    `;
}

function renderTable() {
    tbody.innerHTML = '';

    globalData.cadenas.forEach(cadena => {
        const filaHTML = modelo_Fila(cadena);
        tbody.insertAdjacentHTML('beforeend', filaHTML);
    });

    if (globalData.cadenas.length === 0) {
        tbody.innerHTML = '<tr><td colspan="3" class="text-center">No se encontraron cadenas</td></tr>';
    }
}

function initialize(data) {
    globalData.cadenas = data;
    renderTable();
}

// Carga inicial de datos
fetch_data('cadenas')
    .then(initialize)
    .catch(err => {
        console.error("Error al cargar datos:", err);
        tbody.innerHTML = '<tr><td colspan="3" class="text-center text-danger">Error al cargar los datos. Por favor, intente de nuevo.</td></tr>';
    });
