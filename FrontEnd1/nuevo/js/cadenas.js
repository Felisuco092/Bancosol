import { fetch_data, delete_data } from "./utils/fetch.js";

const tbody = document.getElementById('chain-table-body');

let globalData = {
    cadenas: [],
    tiendas: []
};

function modelo_Fila(cadena) {
    return `
        <tr id="fila-cadena-${cadena.id}">
            <td><strong>${cadena.nombre}</strong></td>
            <td>${cadena.codigo || 'N/A'}</td>
            <td>
                <button class="btn btn-primary btn-sm">Editar</button>
                <button class="btn btn-danger btn-sm" id="eliminar-cadena-${cadena.id}">Eliminar</button>
            </td>
        </tr>
    `;
}

function renderTable() {
    tbody.innerHTML = '';

    globalData.cadenas.forEach(cadena => {
        const filaHTML = modelo_Fila(cadena);
        tbody.insertAdjacentHTML('beforeend', filaHTML);
        const botonEliminar = document.getElementById(`eliminar-cadena-${cadena.id}`);
        botonEliminar.addEventListener('click', async () => {
            if(confirm('¿Estás seguro de eliminar esta cadena? Se eliminarán todas las tiendas asociadas.')) {

                //Fetch de las tiendas de la cadena
                try{
                    const tiendas_query = await fetch_data(`tiendas?id_cadena=${cadena.id}`);
                    tiendas_query.forEach(async (tienda) => {
                        //Eliminar participaciones de la tienda
                        const participaciones_query = await fetch_data(`participa?id_tienda=${tienda.id}`);
                        participaciones_query.forEach(participacion => {
                            delete_data(`participaciones/${participacion.id}`);
                        });
                        //Eliminar turnos de la tienda
                        const turnos_query = await fetch_data(`turnos?id_tienda=${tienda.id}`);
                        turnos_query.forEach(turno => {
                            delete_data(`turnos/${turno.id}`);
                        });
                        //Eliminar la tienda
                        delete_data(`tiendas/${tienda.id}`);
                    });
                    //Eliminar la cadena
                    delete_data(`cadenas/${cadena.id}`);
                    document.getElementById(`fila-cadena-${cadena.id}`).remove();
                } catch (err) {
                    console.error("Error al cargar tiendas:", err);
                } 

            }
        });
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
