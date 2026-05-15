import { fetch_data } from "../utils/fetch.js";

const selectCampana = document.getElementById('campana');
const selectTienda = document.getElementById('tienda');
const selectVoluntario = document.getElementById('voluntario');
const form = document.querySelector('form');

function populateCampanas(campanas) {
    selectCampana.innerHTML = '<option value="">-- Seleccione una campaña --</option>';
    campanas.forEach(c => {
        const option = document.createElement('option');
        option.value = c.id;
        option.textContent = c.nombre;
        selectCampana.appendChild(option);
    });
}

function populateTiendas(tiendas) {
    selectTienda.innerHTML = '<option value="">-- Seleccione una tienda --</option>';
    tiendas.forEach(t => {
        const option = document.createElement('option');
        option.value = t.id;
        option.textContent = t.descripcion;
        selectTienda.appendChild(option);
    });
}

function populateVoluntarios(voluntarios) {
    selectVoluntario.innerHTML = '<option value="">-- Seleccione un voluntario --</option>';
    voluntarios.forEach(v => {
        const option = document.createElement('option');
        option.value = v.id;
        option.textContent = `Voluntario #${v.id} - ${v.domicilio}`;
        selectVoluntario.appendChild(option);
    });
}

async function handleSubmit(e) {
    e.preventDefault();
    
    const formData = new FormData(form);
    const newTurno = {
        dia: formData.get('dia'),
        hora_inicio: formData.get('hora_inicio'),
        hora_fin: formData.get('hora_fin'),
        id_campana: Number(formData.get('campana')),
        id_tienda: Number(formData.get('tienda')),
        id_voluntario: Number(formData.get('voluntario'))
    };

    function validateDate(hora_inicio, hora_fin) {
        const horaInicio = new Date(hora_inicio);
        const horaFin = new Date(hora_fin);
        return horaInicio < horaFin;
    }

    if (!validateDate(newTurno.hora_inicio, newTurno.hora_fin)) {
        alert('La hora de inicio debe ser anterior a la hora de fin');
        return;
    }

    console.log('Enviando nuevo turno:', newTurno);

    try {
        const response = await fetch('http://localhost:3001/turnos', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(newTurno)
        });

        if (response.ok) {
            alert('Turno creado con éxito');
            window.location.href = '../turnos.html';
        } else {
            alert('Error al crear el turno');
        }
    } catch (error) {
        console.error('Error:', error);
        alert('No se pudo conectar con el servidor (JSON Server)');
    }
}

Promise.all([
    fetch_data('campanas'),
    fetch_data('tiendas'),
    fetch_data('voluntario_base')
])
    .then(([campanas, tiendas, voluntarios]) => {
        populateCampanas(campanas);
        populateTiendas(tiendas);
        populateVoluntarios(voluntarios);
    })
    .catch(err => console.error(err));

form.addEventListener('submit', handleSubmit);
