function fetch_data(ruta, error) {
    return fetch('http://localhost:3001/' + ruta)
        .then((response) => response.json())
        .then(data => {
            return data
        })
        .catch(err => {
            console.error("Ha ocurrido un error en el fetch de " + ruta + ":", err);
            throw err;
        })

}

export {fetch_data}