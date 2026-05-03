<%@ page import="java.util.List" %>
<%@ page import="uma.grupo13.bancosol.entity.TiendaEntity" %>
<%@ page import="uma.grupo13.bancosol.entity.VoluntarioEntity" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Bancosol - Dashboard</title>
    <link rel="stylesheet" href="../../css/styles.css">
    <script src="../../js/aside.js" defer></script>
    <%
        List<TiendaEntity> tiendas = (List<TiendaEntity>) request.getAttribute("tiendas");
        List<VoluntarioEntity> voluntarios = (List<VoluntarioEntity>) request.getAttribute("voluntarios");
    %>
</head>
<body>
    <% request.setAttribute("paginaActual", "dashboard"); %>
    <jsp:include page="aside.jsp"/>

    <main class="main-content">
        <header class="header">
            <h1>Cuadro de Mando</h1>
            <span>Bienvenido, Administrador</span>
        </header>
        <div class="dashboard-grid">
            <div class="card dashboard-card">
                <h3>Total de Tiendas</h3>
                <div class="dashboard-number"><%=tiendas.size()%></div>
                <p class="dashboard-label">Tiendas registradas</p>
            </div>

            <div class="card dashboard-card">
                <h3>Total de Voluntarios Movilizados</h3>
                <p class="dashboard-number blue">
                    <%
                        int total = 0;
                        for (VoluntarioEntity v :voluntarios){
                            total += v.getNVoluntarios();
                        }
                    %>
                    <%=total%>
                </p>
                <p class="dashboard-label">voluntarios registrados</p>
            </div>

            <div class="card">
                <h3>Top Cadenas Participantes</h3>
                <table class="dashboard-table">
                    <thead>
                        <tr>
                            <th>Cadena</th>
                            <th>Tiendas</th>
                        </tr>
                    </thead>
                    <tbody>
                        <tr>
                            <td>Mercadona</td>
                            <td><strong>82</strong></td>
                        </tr>
                        <tr>
                            <td>Carrefour</td>
                            <td><strong>35</strong></td>
                        </tr>
                        <tr>
                            <td>Lidl</td>
                            <td><strong>22</strong></td>
                        </tr>
                        <tr>
                            <td>Aldi</td>
                            <td><strong>13</strong></td>
                        </tr>
                    </tbody>
                </table>
            </div>

            <div class="card dashboard-card">
                <h3>Días restantes</h3>
                <p class="dashboard-number blue">42</p>
                <p class="dashboard-label">Días restantes de la campaña activa</p>
            </div>
        </div>
    </main>
</body>
</html>
