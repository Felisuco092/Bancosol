import { fetch_data } from "./utils/fetch.js";

document.getElementById('login-form').addEventListener('submit', (e) => {
    e.preventDefault();

    const usuario = document.getElementById('username').value;
    // Simulación simple para navegación
    fetch_data('usuarios?usuario=' + usuario)
    .then(data => {
        if (data.length > 0) {
            sessionStorage.setItem('user', JSON.stringify(
                {
                    usuario: data[0].usuario,
                    id_rol: data[0].id_rol,
                    nombre: data[0].nombre
                }
            ));
            window.location.href = 'dashboard.html';
        } else {
            alert('Usuario o contraseña incorrectos');
        }
    })
    .catch(err => {
        console.error('Error en el login:', err);
        alert('Error en el login');
    });
});

if(sessionStorage.getItem('user')) {
    window.location.href = 'dashboard.html';
}
