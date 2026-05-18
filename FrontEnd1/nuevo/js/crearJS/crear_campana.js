import { fetch_data } from "../utils/fetch.js";

const form = document.querySelector('form');
let existingCampanas = [];

function hasDateOverlap(newStart, newEnd, year) {
    return existingCampanas.some(c => {
        const existStart = new Date(c.dia_comienzo);
        const existEnd = new Date(c.dia_final);
        return c.ano === year && newStart <= existEnd && newEnd >= existStart;
    });
}

async function handleSubmit(e) {
    e.preventDefault();
    
    const formData = new FormData(form);
    const newStart = new Date(formData.get('dia_comienzo'));
    const newEnd = new Date(formData.get('dia_final'));
    const year = Number(formData.get('ano'));

    if (hasDateOverlap(newStart, newEnd, year)) {
        alert('Ya existe una campaña en ese rango de fechas');
        return;
    }

    const newCampana = {
        nombre: formData.get('nombre'),
        ano: Number(formData.get('ano')),
        dia_comienzo: formData.get('dia_comienzo'),
        dia_final: formData.get('dia_final')
    };

    console.log('Enviando nueva campaña:', newCampana);

    try {
        const response = await fetch('http://localhost:3001/campanas', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(newCampana)
        });

        if (response.ok) {
            alert('Campaña creada con éxito');
            window.location.href = '../campanas.html';
        } else {
            alert('Error al crear la campaña');
        }
    } catch (error) {
        console.error('Error:', error);
        alert('No se pudo conectar con el servidor (JSON Server)');
    }
}

fetch_data('campanas')
    .then(data => { existingCampanas = data; })
    .catch(err => console.error('Error al cargar campañas existentes:', err));

form.addEventListener('submit', handleSubmit);
