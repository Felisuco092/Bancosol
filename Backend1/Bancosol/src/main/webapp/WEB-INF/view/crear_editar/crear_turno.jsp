<%@ page import="uma.grupo13.bancosol.entity.TurnoEntity" %>
<%@ page import="uma.grupo13.bancosol.entity.CampanaEntity" %>
<%@ page import="uma.grupo13.bancosol.entity.TiendaEntity" %>
<%@ page import="java.util.List" %>
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
<%
    List<CampanaEntity> campanas = (List<CampanaEntity>) request.getAttribute("campanas");
    List<TiendaEntity> tiendas = (List<TiendaEntity>) request.getAttribute("tiendas");
%>
<body>
    <main class="main-content">
        <h1><b>Crear Turno</b></h1>

        <div class="formulario">
            <form id= "form-crear-turno" action="/turnos/guardar" method="post">
                <div class="form-group">
                    <label for="idCampana">Campaña:</label>
                    <select name="idCampana" id="idCampana" required>
                        <option value="">-- Seleccione Campaña --</option>
                        <% for(CampanaEntity c : campanas) { %>
                            <option value="<%=c.getId()%>"><%=c.getNombre()%>-<%=c.getAno()%></option>
                        <% } %>
                    </select>
                </div>
                <div class="form-group">
                    <label for="idTienda">Tienda:</label>
                    <select name="idTienda" id="idTienda" required>
                        <option value="">-- Seleccione Tienda --</option>
                        <% for(TiendaEntity t : tiendas) { %>
                            <option value="<%=t.getId()%>"><%=t.getDescripcion()%></option>
                        <% } %>
                    </select>
                </div>
                <div class="form-group">
                    <label for="tipo-turno">Especifica el tipo de turno:</label>
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