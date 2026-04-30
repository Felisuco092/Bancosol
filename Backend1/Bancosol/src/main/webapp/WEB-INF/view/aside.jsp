<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String paginaActual = (String) request.getAttribute("paginaActual");
    if (paginaActual == null) paginaActual = "";
%>
<button id="btn-desplegable" class="btn btn-primary btn-collapsible">
    ☰
</button>
<aside class="sidebar">
    <img src="${pageContext.request.contextPath}/assets/LOGO_BANCOSOL.png" alt="Logo BANCOSOL" class="logo_login">
    <nav id="menu-enlaces">
        <ul>
            <li><a href="${pageContext.request.contextPath}/dashboard" class="<%= "dashboard".equals(paginaActual) ? "active" : "" %>">Cuadro de Mando</a></li>
            <li><a href="${pageContext.request.contextPath}/campanas/" class="<%= "campanas".equals(paginaActual) ? "active" : "" %>">Gestión de Campañas</a></li>
            <li><a href="${pageContext.request.contextPath}/tiendas/" class="<%= "tiendas".equals(paginaActual) ? "active" : "" %>">Gestión de Tiendas</a></li>
            <li><a href="${pageContext.request.contextPath}/colaboradores/" class="<%= "colaboradores".equals(paginaActual) ? "active" : "" %>">Colaboradores</a></li>
            <li><a href="${pageContext.request.contextPath}/usuarios/" class="<%= "usuarios".equals(paginaActual) ? "active" : "" %>">Usuarios</a></li>
            <li><a href="${pageContext.request.contextPath}/turnos/" class="<%= "turnos".equals(paginaActual) ? "active" : "" %>">Asignación de Turnos</a></li>
            <li><a href="${pageContext.request.contextPath}/bandeja/" class="<%= "bandeja".equals(paginaActual) ? "active" : "" %>">Bandeja de Entrada</a></li>
            <li>
                <form action="${pageContext.request.contextPath}/logout" method="POST" style="margin: 0; padding: 0;">
                    <button type="submit" id="logout-btn" class="btn-logout">Cerrar Sesión</button>
                </form>
            </li>
        </ul>
    </nav>
</aside>
