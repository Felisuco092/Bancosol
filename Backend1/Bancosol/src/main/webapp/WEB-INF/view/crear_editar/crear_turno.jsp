<%@ page import="uma.grupo13.bancosol.entity.TurnoEntity" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="es">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Página-crear-turno</title>
    <link rel="stylesheet" href="../../../css/styles.css">
    <link rel="stylesheet" href="../../../css/formulario.css">
    <link rel="stylesheet" href="../../../js/turnos.js">
</head>
<body>
    <main class="main-content">
        <h1><b>Crear Turno</b></h1>

        <div class="formulario">
            <form id= "form-crear-turno" action="/turnos/guardar" method="post">
                <div class="form-group">
                    <label for="nombre">Especifica el tipo de turno:</label>
                    <input type="text" name="tipo-turno" id="tipo-turno" required/>
                </div>
                <div class="form-group">
                    <label for="dia">Especifica el día del turno:</label>
                    <input type="date" name="dia" id="dia" required/>
                </div>
                <div class="form-group">
                    <label for="">Especifique la hora de comienzo del turno:</label>
                    <input type="time" name="hora-inicio" id="hora-inicio" required/>
                </div>
                <div class="form-group">
                    <label for="">Especifique la hora final del turno:</label>
                    <input type="time" name="hora-fin" id="hora-fin" required/>
                </div>
                <div class="form-actions">
                    <button type="submit" class="btn btn-primary">Crear y guardar turno</button>
                    <button type="button" class="btn btn-secondary" onclick="history.back()">Cancelar</button>
                </div>
            </form>
        </div>

    </main>
</body>
</html>