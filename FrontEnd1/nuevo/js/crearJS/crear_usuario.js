import { fetch_data } from "../utils/fetch.js";

const selectRol = document.getElementById('rol');
const form = document.querySelector('form');

function populateRoles(roles) {
    selectRol.innerHTML = '<option value="">-- Seleccione un rol --</option>';
    roles.forEach(rol => {
        const option = document.createElement('option');
        option.value = rol.id;
        option.textContent = rol.nombre;
        selectRol.appendChild(option);
    });
}

async function handleSubmit(e) {
    e.preventDefault();
    
    const formData = new FormData(form);
    const newUsuario = {
        nombre: formData.get('nombre'),
        apellidos: formData.get('apellidos'),
        usuario: formData.get('usuario'),
        contrasena: formData.get('contrasena'),
        email: formData.get('email'),
        telefono: formData.get('telefono'),
        area_asignada: formData.get('area_asignada'),
        id_rol: Number(formData.get('rol'))
    };

    console.log('Enviando nuevo usuario:', newUsuario);

    try {
        const response = await fetch('http://localhost:3001/usuarios', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(newUsuario)
        });

        if (response.ok) {
            alert('Usuario creado con éxito');
            window.location.href = '../usuarios.html';
        } else {
            alert('Error al crear el usuario');
        }
    } catch (error) {
        console.error('Error:', error);
        alert('No se pudo conectar con el servidor (JSON Server)');
    }
}

fetch_data('roles')
    .then(populateRoles)
    .catch(err => console.error(err));

form.addEventListener('submit', handleSubmit);
