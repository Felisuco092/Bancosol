<%@ page import="java.util.List" %>
<%@ page import="uma.grupo13.bancosol.entity.*" %>
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
        int totalVoluntarios = (int) request.getAttribute("totalVoluntarios");
        CampanaEntity campana = (CampanaEntity) request.getAttribute("campana");
        List<CadenaEntity> cadenas= (List<CadenaEntity>) request.getAttribute("cadenas");
        UsuarioEntity usuario = (UsuarioEntity) request.getAttribute("user");
    %>
</head>
<body>
    <% request.setAttribute("paginaActual", "dashboard"); %>
    <jsp:include page="aside.jsp"/>

    <main class="main-content">
        <header class="header">
            <h1>Cuadro de Mando</h1>
            <span>Bienvenid@, <%=usuario.getNombre()%> <%=usuario.getApellidos()%></span>
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
                    <%=totalVoluntarios%>
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
                        <%
                            for(CadenaEntity c : cadenas){ %>
                                <tr>
                                    <td><%=c.getNombre()%></td>
                                    <td><strong><%=c.getTiendas().size()%></strong></td>
                                </tr>
                        <%
                            }
                        %>
                    </tbody>
                </table>
            </div>

            <div class="card dashboard-card">
                <h3>Días restantes</h3>
                <%
                    if (campana==null) { %>
                <p class="dashboard-number blue">Ninguna campaña activa.</p>
                <%
                    }else{ %>
                <p class="dashboard-number blue"><%=campana.getTiempoRestante()%></p>
                <p class="dashboard-label">Días restantes de la campaña activa</p>
                <%
                    }
                %>
            </div>
        </div>
    </main>
</body>
</html>
