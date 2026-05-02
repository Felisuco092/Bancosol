import { fetch_data } from './utils/fetch.js'

function dateToString(date) {
    const fecha = new Date(date); 
    const fechaFormateada = fecha.toLocaleDateString('es-ES');
    return fechaFormateada;
}

function modelo_Fila(json) {
    return `<tr>
                        <td>${dateToString(json.fecha_creacion)}</td>
                        <td>${json.asunto}</td>
                        <td>
                            <button class="btn btn-primary btn-view" onclick="alert('${json.mensaje}')">Ver mensaje</button>
                        </td>
                    </tr>`
}

function renderRows(arrayJsonMensajes) {
    const tbody = document.getElementById('notificaciones_tbody');
    arrayJsonMensajes.forEach(element => {
        tbody.insertAdjacentHTML('beforeend', modelo_Fila(element))
    });
}

fetch_data('notificaciones')
    .then(renderRows)
    .catch(e => {
    console.error(e);
   });