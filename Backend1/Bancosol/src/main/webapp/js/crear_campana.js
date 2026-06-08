import { fetch_data } from "./utils/fetch.js";

const form = document.getElementById('form-crear-campana');

async function handleSubmit(e) {
    e.preventDefault();

    const formData = new FormData(form);

    // Mapeo de datos del formulario al objeto JSON que se hace mucho mejor con el FormData

    const newCampana = {
        nombre: formData.get('nombre'),
        ano: Number(formData.get('anyo')),
        diaComienzo: formData.get('fecha-inicio'),
        diaFinal: formData.get('fecha-fin')
    };

    console.log('Enviando nueva campaña:', newCampana);

    try {
        const response = await fetch('/campanas/guardar', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(newCampana)
        });

        if (response.ok) {
            alert('Campaña creada con éxito');
            window.location.href = '/campanas/';
        } else {
            alert('Error al crear la campaña');
        }
    } catch (error) {
        console.error('Error:', error);
        alert('No se pudo conectar con el servidor');
    }
}

form.addEventListener('submit', handleSubmit);