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

function fetch_post(url, urlSearchParams, success, error) {
    fetch(url, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/x-www-form-urlencoded',
        },
        body: urlSearchParams
    })
        .then(success)
        .catch(error);
}

export { fetch_data, fetch_post };