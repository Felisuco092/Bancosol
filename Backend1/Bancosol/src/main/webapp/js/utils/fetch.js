function fetch_data(url) {
    return fetch(url)
        .then((response) => {
            if (!response.ok) {
                throw new Error(`HTTP error! status: ${response.status}`);
            }
            return response.json();
        })
        .then(data => {
            return data;
        })
        .catch(err => {
            console.error("Error en fetch:", err);
            throw err;
        });
}

export { fetch_data };