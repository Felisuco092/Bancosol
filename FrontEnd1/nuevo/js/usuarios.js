import { fetch_data } from "./utils/fetch.js";
import { quitarTildes } from "./utils/string_utils.js";

function getRolName(arrayJsonRoles, id) {
    //Dado un id de rol, obtiene que nombre de rol tiene.
    const jsonRol = arrayJsonRoles.find((json) => Number(json.id) === id)
    return jsonRol.nombre
}

function modelo_Fila(jsonUsuario, rolName) {
    return `<tr>
                        <td>${jsonUsuario.nombre}</td>
                        <td>${jsonUsuario.apellidos}</td>
                        <td>${jsonUsuario.email}</td>
                        <td>${jsonUsuario.telefono}</td>
                        <td><span class="badge-rol badge-${quitarTildes(rolName.toLowerCase())}">${rolName}</span></td>
                        <td>${jsonUsuario.area_asignada}</td>
                        <td>
                            <button class="btn btn-primary btn-sm">Editar</button>
                            <button class="btn btn-danger btn-sm">Baja</button>
                        </td>
                    </tr>`
}

function renderUsers([arrayJsonUsers, arrayJsonRoles]) {
    const tbody = document.getElementById('usuarios_tbody');

    arrayJsonUsers.forEach(element => {
        const roleName = getRolName(arrayJsonRoles, element.id_rol);
        tbody.insertAdjacentHTML('beforeend', modelo_Fila(element, roleName))
    });
}

Promise.all([
    fetch_data('usuarios'),
    fetch_data('roles')
]).then(renderUsers)
    .catch(e => {
    console.error(e);
   });