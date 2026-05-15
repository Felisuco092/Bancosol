const form = document.querySelector('form');

async function handleSubmit(e) {
    e.preventDefault();
    
    const formData = new FormData(form);
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

form.addEventListener('submit', handleSubmit);
