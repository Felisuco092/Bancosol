const form = document.querySelector('form');

async function handleSubmit(e) {
    e.preventDefault();
    
    const formData = new FormData(form);
    const newCadena = {
        nombre: formData.get('nombre'),
        codigo: formData.get('codigo')
    };

    console.log('Enviando nueva cadena:', newCadena);

    try {
        const response = await fetch('http://localhost:3001/cadenas', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(newCadena)
        });

        if (response.ok) {
            alert('Cadena creada con éxito');
            window.location.href = '../cadena.html';
        } else {
            alert('Error al crear la cadena');
        }
    } catch (error) {
        console.error('Error:', error);
        alert('No se pudo conectar con el servidor (JSON Server)');
    }
}

form.addEventListener('submit', handleSubmit);
