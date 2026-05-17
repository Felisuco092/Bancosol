<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Bancosol - Tiendas - Crear</title>
    <link rel="stylesheet" href="../../../css/styles.css">
    <link rel="stylesheet" href="../../../css/formulario.css">
    <script src="../../../js/aside.js" defer></script>
    <script src="../../../js/crear_tienda.js" type="module"></script>
</head>
<body>

    <main class="main-content">
        <header class="header">
            <h1>Crear Tienda</h1>
        </header>

        <div class="formulario">
            <form id="form-crear-tienda">
                <div class="form-group">
                    <label for="cadena">Cadena</label>
                    <select name="cadena[]" id="cadena" required>
                        <option value="">-- Cargando cadenas --</option>
                    </select>
                </div>
                <div class="form-group">
                    <label for="descripcion">Especificaci&oacute;n:</label>
                    <input type="text" name="descripcion" id="descripcion" required/>
                </div>
                <div class="form-group">
                    <label for="Localidad">Especifique la localidad:</label>
                    <input type="text" name="Localidad" id="Localidad" required/>
                </div>
                <div class="form-group">
                    <label for="domicilio">Especifique el domicilio:</label>
                    <input type="text" name="domicilio" id="domicilio" required/>
                </div>
                <div class="form-group">
                    <label for="CPostal">C&oacute;digo Postal</label>
                    <input type="text" name="CPostal" id="CPostal" required/>
                </div>
                <div class="form-group">
                    <label for="ZGeo">Especifique la zona Geogr&aacute;fica</label>
                    <input type="text" name="ZGeo" id="ZGeo" required/>
                </div>
                <div class="form-actions">
                    <button type="submit" class="btn btn-primary">Crear Tienda</button>
                    <button type="button" class="btn btn-secondary" onclick="history.back()">Cancelar</button>
                </div>
            </form>
        </div>
    </main>
</body>
</html>