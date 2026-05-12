function editarUsuario(id) {
    fetch('/usuarios/editar', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/x-www-form-urlencoded',
        },
        body: new URLSearchParams({ id: id })
    })
    .catch(error => console.error("Error de conexión:", error));
}

function borrarUsuario(id) {
    if (confirm("¿Estás seguro de que deseas borrar este usuario?")) {
        fetch('/usuarios/borrar', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/x-www-form-urlencoded',
            },
            body: new URLSearchParams({ id: id })
        })
        .catch(error => console.error("Error de conexión:", error));
    }
}

function crearUsuario() {
    fetch('/usuarios/crear', {
        method: 'POST',
    })
    .catch(error => console.error("Error de conexión:", error));
}

