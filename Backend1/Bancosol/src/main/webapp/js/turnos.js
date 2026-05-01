document.getElementById('btn-buscar').addEventListener('click', function() {
    const campana = document.getElementById('select-campana').value;
    const tienda = document.getElementById('select-tienda').value;

    if (campana && tienda) {
        document.getElementById('cuadrante-container').style.display = 'block';
    } else {
        alert('Por favor, seleccione una campaña y una tienda.');
    }
});
