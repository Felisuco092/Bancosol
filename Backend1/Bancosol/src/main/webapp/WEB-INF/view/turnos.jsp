<%@ page import="uma.grupo13.bancosol.entity.TiendaEntity" %>
<%@ page import="uma.grupo13.bancosol.entity.CampanaEntity" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Bancosol - Asignación de Turnos</title>
    <link rel="stylesheet" href="../../css/styles.css">
    <script src="../../js/aside.js" defer></script>
    <script src="../../js/turnos.js" defer></script>
</head>
<%
    List<TiendaEntity> tiendasList = (List<TiendaEntity>) request.getAttribute("tiendas");
    List<CampanaEntity> campanasList = (List<CampanaEntity>) request.getAttribute("campanas");
%>
<body>
    <% request.setAttribute("paginaActual", "turnos"); %>
    <jsp:include page="aside.jsp"/>

    <main class="main-content">
        <header class="header">
            <h1>Asignación de Turnos</h1>
        </header>
        <div class="card filtros-turnos">
            <!--<form action = "/turnos/filtrar" method = "POST">-->
            <div>
                <label for="select-campana">Campaña:</label>
                <select id="select-campana">
                    <option value="">-- Seleccione Campaña --</option>

                    <%
                        int i = 1;
                        for(CampanaEntity campanaAct: campanasList){%>
                    <option value="<%=i%>"><%=campanaAct.getNombre()%>-<%=campanaAct.getAno()%></option>
                    <%}%>
                </select>
            </div>
            <div>
                <label for="select-tienda">Tienda:</label>
                <select id="select-tienda">
                    <option value="">-- Seleccione Tienda --</option>
                    <%for(TiendaEntity tiendaAct: tiendasList){%>
                    <option value="<%=tiendaAct.getId()%>"><%=tiendaAct.getDescripcion()%></option>
                    <%}%>
                </select>
            </div>

            <button id="btn-buscar" class="btn btn-primary">Ver Cuadrante</button>
            <!--</form>-->
        </div>

        <div id="cuadrante-container">
            <div class="card">
                <div class="cuadrante-header">
                    <h3>Cuadrante de Turnos</h3>
                    <div class="cuadrante-actions">
                        <span>Capitán: <strong id="capitan-nombre"></strong></span>
                        <a href="/turnos/crear">
                            <button class="btn btn-success btn-add-extra">+ Añadir Turno Extra</button>
                        </a>
                    </div>
                </div>

            <div id="tabla-container">
                <jsp:include page="tablas/turnos.jsp"/>
            </div>
        </div>

    </main>
</body>
</html>
