<%--
Página JSP que muestra la gestion de colaboradores

Autores:
- Félix Jiménez Almanza: 100%

--%>
<%@ page import="java.util.List" %>
<%@ page import="uma.grupo13.bancosol.services.utils.Permiso" %>
<%@ page import="java.util.Map" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Bancosol - Colaboradores</title>
    <link rel="stylesheet" href="../../css/styles.css">
    <script src="../../js/aside.js" defer></script>
</head>
<body>
    <%
        request.setAttribute("paginaActual", "colaboradores");
        Map<Permiso, Boolean> permisos = (Map<Permiso, Boolean>) session.getAttribute("permisos");
        List<String> localidades = (List<String>) request.getAttribute("localidades");
    %>
    <jsp:include page="aside.jsp"/>

    <main class="main-content">
        <header class="header">
            <h1>Gestión de Colaboradores</h1>
            <% if (Boolean.TRUE.equals(permisos.get(Permiso.EDITAR_COLABORADORES))) { %>
                <a href="/colaboradores/crear"><button class="btn btn-primary">+ Nuevo Colaborador</button></a>
            <% } %>
        </header>

        <div class="card">
            <div class="filtros-grid">
                <div>
                    <label for="filter-tipo">Tipo:</label>
                    <select id="filter-tipo">
                        <option value="all">Todos</option>
                        <option value="false">Entidad / Grupo</option>
                        <option value="true">Persona Física</option>
                        <option value="confirmar">Por confirmar</option>
                    </select>
                </div>
                <div>
                    <label for="filter-localidad">Localidad:</label>
                    <select id="filter-localidad">
                        <option value="all">Todas</option>
                        <% for (String loc : localidades) { %>
                            <option value="<%=loc%>"><%=loc%></option>
                        <% } %>
                    </select>
                </div>
            </div>

            <div id="tabla-container">
                <jsp:include page="tablas/colaboradores.jsp" />
            </div>
        </div>
    </main>

    <script>
        const filterTipo = document.getElementById('filter-tipo');
        const filterLocalidad = document.getElementById('filter-localidad');
        const tablaContainer = document.getElementById('tabla-container');

        function filtrar() {
            const params = new URLSearchParams();
            params.set('tipo', filterTipo.value);
            params.set('localidad', filterLocalidad.value);

            fetch('/colaboradores/filtrar', {
                method: 'POST',
                body: params,
                headers: { 'Content-Type': 'application/x-www-form-urlencoded' }
            })
                .then(r => r.text())
                .then(html => {
                    tablaContainer.innerHTML = html;
                })
                .catch(e => console.error(e));
        }

        filterTipo.addEventListener('change', filtrar);
        filterLocalidad.addEventListener('change', filtrar);
    </script>
</body>
</html>
