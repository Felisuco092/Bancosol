<%@ page import="uma.grupo13.bancosol.entity.CampanaEntity" %>
<%@ page import="java.util.List" %><%--
  Created by IntelliJ IDEA.
  User: felix
  Date: 20/04/2026
  Time: 11:29
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Bancosol - Campañas</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/styles.css">
</head>
<body>
<%
    List<CampanaEntity> campanas = (List<CampanaEntity>) request.getAttribute("campanas");
%>
<jsp:include page="aside.jsp"/>

<main class="main-content">
    <header class="header">
        <h1>Gestión de Campañas</h1>
        <button class="btn btn-primary">+ Nueva Campaña</button>
    </header>

    <div class="card">
        <h3>Nueva Campaña (Referencia PDF pág. 2)</h3>
        <form>
            <div style="margin-bottom: 15px;">
                <label>Nombre de la Campaña:</label>
                <input type="text" placeholder="Ej: Gran Recogida" style="width: 100%; padding: 8px;">
            </div>
            <div style="margin-bottom: 15px;">
                <label>Año:</label>
                <select style="width: 100%; padding: 8px;">
                    <option>2026</option>
                    <option>2025</option>
                </select>
            </div>
            <div style="margin-bottom: 15px;">
                <label>Cadenas de Supermercados que participan:</label>
                <div style="margin-top: 10px;">
                    <input type="checkbox"> Mercadona
                    <input type="checkbox" style="margin-left: 20px;"> Carrefour
                    <input type="checkbox" style="margin-left: 20px;"> Lidl
                    <input type="checkbox" style="margin-left: 20px;"> Dia
                </div>
            </div>
            <button type="submit" class="btn btn-success">Guardar Campaña</button>
        </form>
    </div>
    <div class="card">
        <table>
            <thead>
                <th>ID</th>
                <th>NOMBRE</th>
                <th>AÑO</th>
                <th>DIA_COMIENZO</th>
                <th>DIA_FINAL</th>
            </thead>
            <tbody>
                <%
                    for(CampanaEntity campana : campanas) {
                        System.out.println(campana.getNombre());
                %>
                    <tr>
                        <td><%= campana.getId()%></td>
                        <td><%= campana.getNombre()%></td>
                        <td><%= campana.getAno()%></td>
                        <td><%= campana.getDiaComienzo()%></td>
                        <td><%= campana.getDiaFinal()%></td>
                    </tr>
                <%
                    }
                %>
            </tbody>
        </table>
    </div>
</main>
</body>
</html>
