const API_URL = 'http://localhost:3001';

function fetch_data(ruta, error) {
    return fetch(API_URL + '/' + ruta)
        .then((response) => response.json())
        .then(data => {
            return data
        })
        .catch(err => {
            console.error("Ha ocurrido un error en el fetch de " + ruta + ":", err);
            throw err;
        })

}

function delete_data(ruta,error) {
    return fetch(API_URL + '/' + ruta, {
        method: 'DELETE'
    })
    .then((response) => response.json())
    .then(data => {
        return data
    })
    .catch(err => {
        console.error("Ha ocurrido un error en el delete de " + ruta + ":", err);
        throw err;
    })
}

async function update_data(ruta, data) {
    try {
        const response = await fetch(API_URL + '/' + ruta, {
            method: 'PATCH',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(data)
        });
        if (!response.ok) {
            throw new Error(`Error al actualizar ${ruta}: ${response.status}`);
        }
        return await response.json();
    } catch (err) {
        console.error("Ha ocurrido un error en el update de " + ruta + ":", err);
        throw err;
    }
}

export {fetch_data, delete_data, update_data}