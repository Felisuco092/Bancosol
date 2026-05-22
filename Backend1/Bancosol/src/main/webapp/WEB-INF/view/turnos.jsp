<%@ page import="uma.grupo13.bancosol.entity.TiendaEntity" %>
<%@ page import="uma.grupo13.bancosol.entity.CampanaEntity" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="es">
<!-- La ia generativa nos ha modificado el contenedor del cuadrante más abajo porque solo aparecía medio segundo y depsués se borraba -->
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
    String capitanNombre = (String) request.getAttribute("capitanNombre");
    Integer idCampanaSelect = (Integer) request.getAttribute("idCampanaSel");
    Integer idTiendaSelect = (Integer) request.getAttribute("idTiendaSel");
%>
<body>
    <% request.setAttribute("paginaActual", "turnos"); %>
    <jsp:include page="aside.jsp"/>

    <main class="main-content">
        <header class="header">
            <h1>Asignación de Turnos</h1>
        </header>
        <div class="card filtros-turnos">
            <form id="filter-form" style="display: flex; gap: 20px; align-items: flex-end; width: 100%;">
                <div style="flex: 1;">
                    <label for="select-campana">Campaña:</label>
                    <select id="select-campana" name="idCampana" style="width: 100%;">
                        <option value="">-- Seleccione Campaña --</option>

                        <%
                        for(CampanaEntity campanaAct: campanasList){%>
                            <option value="<%=campanaAct.getId()%>" <%= (idCampanaSelect != null && idCampanaSelect.equals(campanaAct.getId())) ? "selected" : "" %>><%=campanaAct.getNombre()%>-<%=campanaAct.getAno()%></option>
                        <%}%>
                    </select>
                </div>
                <div>
                    <label for="select-tienda">Tienda:</label>
                    <select id="select-tienda" name="idTienda">
                        <option value="">-- Seleccione Tienda --</option>
                        <%
                        for(TiendaEntity tiendaAct: tiendasList){%>
                        <option value="<%=tiendaAct.getId()%>" <%= (idTiendaSelect != null && idTiendaSelect.equals(tiendaAct.getId())) ? "selected" : "" %>><%=tiendaAct.getDescripcion()%></option>
                        <%}%>
                    </select>
                </div>

            </form>
        </div>

        <div id="cuadrante-container" style="<%=(idCampanaSelect != null || idTiendaSelect != null) ? "display: block;" : "display: none;" %>">
            <div class="card">
                <div class="cuadrante-header">
                    <h3>Cuadrante de Turnos</h3>
                    <div class="cuadrante-actions">
                        <span>Capitán: <strong id="capitan-nombre">
                            <% if(capitanNombre != null){ %>
                            <%=capitanNombre%>
                            <%}else{%>
                              <%=""%>
                            <%}%></strong></span>
                        <a href="/turnos/crear">
                            <button class="btn btn-success btn-add-extra">+ Añadir Turno Extra</button>
                        </a>
                    </div>
                </div>

            <div id="tabla-container">
                <jsp:include page="tablas/turnos.jsp"/>
            </div>
        </div>
    </div>
    </main>

    <script>
        const filterTipo = document.getElementById('select-campana');
        const filterTienda = document.getElementById('select-tienda');
        const containerTabla = document.getElementById('tabla-container');
        const capitanNombreLabel = document.getElementById('capitan-nombre');
        const cuadranteContainer = document.getElementById('cuadrante-container');

        function filter(){
            const params = new URLSearchParams();
            params.set('idCampana', filterTipo.value);
            params.set('idTienda', filterTienda.value);

            fetch('/turnos/filtrar', {
                method: 'POST',
                body: params,
                headers: { 'Content-Type': 'application/x-www-form-urlencoded' }
            }).then(r => r.text())
            .then(html =>{
                containerTabla.innerHTML = html;
                const table = containerTabla.querySelector('table');
                if (table) {
                    capitanNombreLabel.innerText = table.dataset.capitan || '';
                }
                
                if (filterTipo.value || filterTienda.value) {
                    cuadranteContainer.style.display = 'block';
                }
            })
            .catch(error => console.error(error));
        }

        filterTipo.addEventListener('change', filter);
        filterTienda.addEventListener('change', filter);
        document.getElementById('btn-buscar').addEventListener('click', filter);
    </script>
</body>
</html>
