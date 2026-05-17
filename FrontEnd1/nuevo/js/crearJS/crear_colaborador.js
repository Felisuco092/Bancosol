const tipoSelect = document.getElementById('tipo_colaborador');
const camposFisico = document.getElementById('campos-fisico');
const camposEntidad = document.getElementById('campos-entidad');
const form = document.querySelector('form');

const nombreInput = document.getElementById('nombre');
const apellidosInput = document.getElementById('apellidos');
const nombreAsociacionInput = document.getElementById('nombre_asociacion');
const nVoluntariosInput = document.getElementById('n_voluntarios');

function setRequired(input, required) {
    if (required) {
        input.setAttribute('required', '');
    } else {
        input.removeAttribute('required');
    }
}

tipoSelect.addEventListener('change', () => {
    const value = tipoSelect.value;
    camposFisico.style.display = value === 'fisico' ? 'block' : 'none';
    camposEntidad.style.display = value === 'entidad' ? 'block' : 'none';

    setRequired(nombreInput, value === 'fisico');
    setRequired(apellidosInput, value === 'fisico');
    setRequired(nombreAsociacionInput, value === 'entidad');
    setRequired(nVoluntariosInput, value === 'entidad');
});

async function handleSubmit(e) {
    e.preventDefault();

    const formData = new FormData(form);
    const tipo = formData.get('tipo_colaborador');

    if (!tipo) {
        alert('Seleccione un tipo de colaborador');
        return;
    }

    const baseData = {
        domicilio: formData.get('domicilio'),
        zona_geografica: formData.get('zona_geografica'),
        codigo_postal: formData.get('codigo_postal'),
        observaciones: formData.get('observaciones') || '',
        aprobado: false
    };

    try {
        const baseResponse = await fetch('http://localhost:3001/voluntario_base', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(baseData)
        });

        if (!baseResponse.ok) {
            alert('Error al crear el colaborador base');
            return;
        }

        const baseResult = await baseResponse.json();
        const newId = baseResult.id;

        if (tipo === 'fisico') {
            const fisicoData = {
                id_voluntario: newId,
                nombre: formData.get('nombre'),
                apellidos: formData.get('apellidos')
            };
            const resp = await fetch('http://localhost:3001/voluntario_fisico', {
                method: 'POST',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify(fisicoData)
            });
            if (resp.ok) {
                alert('Colaborador creado con éxito');
                window.location.href = '../colaboradores.html';
            } else {
                alert('Error al crear el colaborador (datos físicos)');
            }
        } else if (tipo === 'entidad') {
            const entidadData = {
                id_voluntario: newId,
                nombre_asociacion: formData.get('nombre_asociacion'),
                n_voluntarios: Number(formData.get('n_voluntarios'))
            };
            const resp = await fetch('http://localhost:3001/voluntario_entidad', {
                method: 'POST',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify(entidadData)
            });
            if (resp.ok) {
                alert('Colaborador creado con éxito');
                window.location.href = '../colaboradores.html';
            } else {
                alert('Error al crear el colaborador (datos entidad)');
            }
        }
    } catch (error) {
        console.error('Error:', error);
        alert('No se pudo conectar con el servidor (JSON Server)');
    }
}

form.addEventListener('submit', handleSubmit);
