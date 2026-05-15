import { fetch_data } from "../utils/fetch.js";

const selectCadena = document.getElementById('cadena');
const form = document.querySelector('form');

function populateCadenas(cadenas) {
    selectCadena.innerHTML = '<option value="">-- Seleccione una cadena --</option>';
    cadenas.forEach(cadena => {
        const option = document.createElement('option');
        option.value = cadena.id;
        option.textContent = cadena.nombre;
        selectCadena.appendChild(option);
    });
}

async function handleSubmit(e) {
    e.preventDefault();
    
    const formData = new FormData(form);
    const newTienda = {
        id_cadena: Number(formData.get('cadena[]')),
        descripcion: formData.get('descripcion'),
        localidad: formData.get('Localidad'),
        domicilio: formData.get('domicilio'),
        c_postal: formData.get('CPostal'),
        zona_geografica: formData.get('ZGeo'),
        id_capitan: null
    };

    console.log('Enviando nueva tienda:', newTienda);

    try {
        const response = await fetch('http://localhost:3001/tiendas', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(newTienda)
        });

        if (response.ok) {
            alert('Tienda creada con éxito');
            window.location.href = '../tiendas.html';
        } else {
            alert('Error al crear la tienda');
        }
    } catch (error) {
        console.error('Error:', error);
        alert('No se pudo conectar con el servidor (JSON Server)');
    }
}

function populateCapitanes(capitanes) {
    const selectCapitan = document.getElementById('capitan');
    selectCapitan.innerHTML = '<option value="">-- Seleccione un capitán --</option>';
    capitanes.forEach(capitan => {
        const option = document.createElement('option');
        option.value = capitan.id;
        option.textContent = capitan.nombre;
        selectCapitan.appendChild(option);
    });
}

function populate(cadenas, usuarios, roles) {
    populateCadenas(cadenas);
    const capitanRol = roles.find(rol => rol.nombre === 'Capitán');
    const capitanes = usuarios.filter(user => String(user.id_rol) === String(capitanRol.id));
    populateCapitanes(capitanes);
}

Promise.all([
    fetch_data('cadenas'),
    fetch_data('usuarios'),
    fetch_data('roles')
])
    .then(([cadenas, usuarios, roles]) => populate(cadenas, usuarios, roles))
    .catch(err => console.error(err));

form.addEventListener('submit', handleSubmit);
