import { fetch_data } from "./utils/fetch.js";

const selectCadena = document.getElementById('cadena');
const form = document.getElementById('form-crear-tienda');

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
            window.location.href = 'tiendas.jsp';
        } else {
            alert('Error al crear la tienda');
        }
    } catch (error) {
        console.error('Error:', error);
        alert('No se pudo conectar con el servidor (JSON Server)');
    }
}

fetch_data('http://localhost:3001/cadenas')
    .then(populateCadenas)
    .catch(err => console.error(err));

form.addEventListener('submit', handleSubmit);