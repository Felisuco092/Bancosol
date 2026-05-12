<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String paginaActual = (String) request.getAttribute("paginaActual");
    if (paginaActual == null) paginaActual = "";
%>
<button id="btn-desplegable" class="btn btn-primary btn-collapsible">
    ☰
</button>
<aside class="sidebar">
    <img src="../../assets/LOGO_BANCOSOL.png" alt="Logo BANCOSOL" class="logo_login">
    <nav id="menu-enlaces">
        <ul>
            <li><a href="/dashboard" class="<%= "dashboard".equals(paginaActual) ? "active" : "" %>">Cuadro de Mando</a></li>
            <li><a href="/campanas/" class="<%= "campanas".equals(paginaActual) ? "active" : "" %>">Gestión de Campañas</a></li>
            <li><a href="/cadenas/" class="<%= "cadenas".equals(paginaActual) ? "active" : "" %>">Gestión de Cadenas</a></li>
            <li><a href="/tiendas/" class="<%= "tiendas".equals(paginaActual) ? "active" : "" %>">Gestión de Tiendas</a></li>
            <li><a href="/colaboradores/" class="<%= "colaboradores".equals(paginaActual) ? "active" : "" %>">Colaboradores</a></li>
            <li><a href="/usuarios/" class="<%= "usuarios".equals(paginaActual) ? "active" : "" %>">Usuarios</a></li>
            <li><a href="/turnos/" class="<%= "turnos".equals(paginaActual) ? "active" : "" %>">Asignación de Turnos</a></li>
            <li><a href="/bandeja/" class="<%= "bandeja".equals(paginaActual) ? "active" : "" %>">Bandeja de Entrada</a></li>
            <li>
                <form action="/logout" method="POST" style="margin: 0; padding: 0;">
                    <button type="submit" id="logout-btn" class="btn-logout">Cerrar Sesión</button>
                </form>
            </li>
        </ul>
    </nav>
</aside>
