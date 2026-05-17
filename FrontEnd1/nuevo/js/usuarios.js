import { fetch_data, delete_data, update_data } from "./utils/fetch.js";
import { quitarTildes } from "./utils/string_utils.js";

function getRolName(arrayJsonRoles, id) {
    //Dado un id de rol, obtiene que nombre de rol tiene.
    const jsonRol = arrayJsonRoles.find((json) => String(json.id) === String(id))
    return jsonRol.nombre
}

function modelo_Fila(jsonUsuario, rolName) {
    return `<tr id="fila-usuario-${jsonUsuario.id}">
                        <td>${jsonUsuario.nombre}</td>
                        <td>${jsonUsuario.apellidos}</td>
                        <td>${jsonUsuario.email}</td>
                        <td>${jsonUsuario.telefono}</td>
                        <td><span class="badge-rol badge-${quitarTildes(rolName.toLowerCase())}">${rolName}</span></td>
                        <td>${jsonUsuario.area_asignada}</td>
                        <td>
                            <button class="btn btn-primary btn-sm">Editar</button>
                            <button class="btn btn-danger btn-sm" id="eliminar-usuario-${jsonUsuario.id}">Baja</button>
                        </td>
                    </tr>`
}

function renderUsers([arrayJsonUsers, arrayJsonRoles]) {
    const tbody = document.getElementById('usuarios_tbody');

    arrayJsonUsers.forEach(element => {
        const roleName = getRolName(arrayJsonRoles, element.id_rol);
        tbody.insertAdjacentHTML('beforeend', modelo_Fila(element, roleName));
        const btnEliminar = document.getElementById(`eliminar-usuario-${element.id}`);
        btnEliminar.addEventListener('click', async () => {
            if (confirm('¿Estás seguro de dar de baja a este usuario? Se desasignará de las tiendas donde sea capitán.')) {
                try {
                    const tiendas = await fetch_data(`tiendas?id_capitan=${element.id}`);
                    for (const tienda of tiendas) {
                        await update_data(`tiendas/${tienda.id}`, { id_capitan: '' });
                    }
                    const notificaciones = await fetch_data(`notificaciones?id_usuario_destino=${element.id}`);
                    notificaciones.forEach(n => delete_data(`notificaciones/${n.id}`));
                    await delete_data(`usuarios/${element.id}`);
                    document.getElementById(`fila-usuario-${element.id}`).remove();
                } catch (err) {
                    console.error("Error al eliminar usuario:", err);
                }
            }
        });
    });
}

Promise.all([
    fetch_data('usuarios'),
    fetch_data('roles')
]).then(renderUsers)
    .catch(e => {
    console.error(e);
   });