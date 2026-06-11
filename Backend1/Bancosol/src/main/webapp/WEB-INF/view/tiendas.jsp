<%--
Página JSP que muestra la pagina de gestion de tiendas

Autores:
- Jorge Torres Sánchez: 100%

--%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="uma.grupo13.bancosol.services.utils.Permiso" %>
<%@ page import="java.util.Map" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Bancosol - Tiendas</title>
    <link rel="stylesheet" href="../../css/styles.css">
    <link rel="icon" type="image/png" href="/assets/LOGO_BANCOSOL_ICONO.png">
    <script src="../../js/aside.js" defer></script>
</head>
<body>
    <%
        request.setAttribute("paginaActual", "tiendas");
        Map<Permiso, Boolean> permisos = (Map<Permiso, Boolean>) session.getAttribute("permisos");
    %>
    <jsp:include page="aside.jsp"/>

    <main class="main-content">
        <header class="header">
            <h1>Gestión de Tiendas</h1>
            <% if (Boolean.TRUE.equals(permisos.get(Permiso.EDITAR_TIENDA))) { %>
                <a href="/tiendas/crear"><button class="btn btn-primary">+ Crear Tienda</button></a>
            <% } %>
        </header>

        <div class="card">
            <div class="filtros-grid">
                <jsp:include page="filtros/tiendas.jsp"/>
            </div>
            <div id="tabla-container">
                <jsp:include page="tablas/tiendas.jsp"/>
            </div>

        </div>
    </main>
</body>
</html>

<script>
    const filterCampana = document.getElementById('select-filtro-campanas');
    const filterLocalidad = document.getElementById('filtro-por-localidad');
    const filterCadena = document.getElementById('filtro-por-cadenas');
    const tablaContainer = document.getElementById('tabla-container');

    function filtrar() {
        const params = new URLSearchParams();
        params.set('idCampana', filterCampana.value);
        params.set('localidad', filterLocalidad.value);
        params.set('idCadena', filterCadena.value);


        fetch('/tiendas/filtrar', {
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

    filterCampana.addEventListener('change', filtrar);
    filterLocalidad.addEventListener('change', filtrar);
    filterCadena.addEventListener('change', filtrar)

</script>
