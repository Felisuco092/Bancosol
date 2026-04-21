<%--
  Created by IntelliJ IDEA.
  User: felix
  Date: 21/04/2026
  Time: 13:20
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%
    String paginaActual = (String) request.getAttribute("paginaActual");
%>
<aside class="sidebar">
    <h2>Bancosol</h2>
    <nav>
        <ul>
            <li><a href="/dashboard" class="<%= "dashboard".equals(paginaActual) ? "active" : "" %>">Cuadro de Mando</a></li>
            <li><a href="/campanas" class="<%= "campanas".equals(paginaActual) ? "active" : "" %>">Gestión de Campañas</a></li>
            <li><a href="/tiendas" class="<%= "tiendas".equals(paginaActual) ? "active" : "" %>">Gestión de Tiendas</a></li>
            <li><a href="/colaboradores" class="<%= "colaboradores".equals(paginaActual) ? "active" : "" %>">Colaboradores</a></li>
            <li><a href="/usuarios" class="<%= "usuarios".equals(paginaActual) ? "active" : "" %>">Usuarios</a></li>
            <li><a href="/turnos" class="<%= "turnos".equals(paginaActual) ? "active" : "" %>">Asignación de Turnos</a></li>
            <li><a href="/bandeja" class="<%= "bandeja".equals(paginaActual) ? "active" : "" %>">Bandeja de Entrada</a></li>
            <li><a href="/" id="logout-btn" style="color: #ff6b6b;">Cerrar Sesión</a></li>
        </ul>
    </nav>
</aside>
