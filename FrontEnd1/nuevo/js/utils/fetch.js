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

export {fetch_data, delete_data}