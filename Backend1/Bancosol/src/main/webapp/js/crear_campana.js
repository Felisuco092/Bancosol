import { fetch_data } from "./utils/fetch.js";

const selectCampana = document.getElementById('campana');
const form = document.getElementById('form-crear-campana');

async function handleSubmit(e) {
    e.preventDefault();

    const formData = new FormData(form);
    const newCampana = {
        id_campana: newCampana.id,
        nombre_campana: formData.get('nombre'),
        anyo: Number(formData.get('anyo')),

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