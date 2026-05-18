<%@ page import="uma.grupo13.bancosol.entity.CampanaEntity" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="es">
<%
    CampanaEntity campana = (CampanaEntity) request.getAttribute("campana");
    if(campana == null) campana = new CampanaEntity();
%>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title><%=campana.getNombre() != null ? "Editar " : "Crear "%>campaña</title>
    <link rel="stylesheet" href="../../../css/styles.css">
    <link rel="stylesheet" href="../../../css/formulario.css">
    <script src="../../../js/crear_campana.js" type="module"></script>
</head>

<body>

    <main class="main-content">
        <header class="header">
            <h1><%= (campana.getId()!=null?"Editar ":"Crear ") %> campaña</h1>
        </header>

        <div class="formulario">
            <form id="form-crear-campana">
                <input type="hidden" name="idCampana" value="<%=campana.getId() == null?"" : campana.getId()%>"/>

                <div class="form-group">
                    <label for="nombre">Especifique el nombre de la campaña:</label>
                    <input type="text" name="nombre" id="nombre" required/>
                </div>
                <div class="form-group">
                    <label for="anyo">Especifique el año del transcurso de la campaña:</label>
                    <input type="number" name="anyo" id="anyo" required/>
                </div>
                <div class="form-group">
                    <label for="fecha-inicio">Especifique el día de comienzo de la campaña:</label>
                    <input type="date" name="fecha-inicio" id="fecha-inicio" required/>
                </div>
                <div class="form-group">
                    <label for="fecha-fin">Especifique el día final de la campaña: </label>
                    <input type="date" name="fecha-fin" id="fecha-fin" required/>
                </div>

                <div class="form-actions">
                    <button type="submit" class="btn btn-primary">
                        <%=campana.getId() == null ? "Crear ":"Editar "%>campaña
                    </button>
                    <button type="button" class="btn btn-secondary" onclick="history.back()">Cancelar</button>
                </div>
            </form>
        </div>
    </main>
</body>
</html>