<%--
Página JSP que muestra el formulario para crear y editar cadena

Autores:
- German Pelaez Gallardo: 100%

--%>
<%@ page import="uma.grupo13.bancosol.entity.CadenaEntity" %>
<%@ page import="uma.grupo13.bancosol.dto.CadenaDTO" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    CadenaDTO cadena = (CadenaDTO) request.getAttribute("cadena");
    if(cadena == null) {cadena = new CadenaDTO();}
    String error = (String) request.getAttribute("error");
%>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title><%=cadena.getNombre() != null ? "Editar " : "Crear "%>cadena</title>
    <link rel="stylesheet" href="../../../css/styles.css">
    <link rel="stylesheet" href="../../../css/formulario.css">
    <link rel="icon" type="image/png" href="/assets/LOGO_BANCOSOL_ICONO.png">

</head>
<body>
<main class="main-content">
    <header class="header">
        <h1><%= (cadena.getId()!=null?"Editar":"Crear") %> cadena</h1>
        <% if (error != null) { %>
        <div class="alert alert-error"><%= error %></div>
        <% } %>
    </header>

    <div class="formulario">
        <form id="form-crear-tienda" action="/cadenas/guardar" method="post">
            <input type="hidden" value="<%=cadena.getId() != null ? cadena.getId() : ""%>" name="id"/>
            <div class="form-group">
                <label for="nombre">Nombre<span class="required">*</span></label>
                <input type="text" name="nombre" id="nombre" required
                       value="<%=cadena.getNombre() != null ? cadena.getNombre() : ""%>" >
            </div>
            <div class="form-group">
                <label for="codigo">Código<span class="required">*</span></label>
                <input type="text" name="codigo" id="codigo" required
                       value="<%=cadena.getCodigo() != null ? cadena.getCodigo() : ""%>"/>
            </div>
            <div class="form-actions">
                <button type="submit" class="btn btn-primary">
                    <%=cadena.getNombre() != null ? "Editar " : "Crear "%> Tienda
                </button>
                <button type="button" class="btn btn-secondary" onclick="history.back()">Cancelar</button>
            </div>
        </form>
    </div>
</main>
</body>
</html>
