<%@ page import="uma.grupo13.bancosol.entity.CadenaEntity" %><%--
  Created by IntelliJ IDEA.
  User: felix
  Date: 11/05/2026
  Time: 12:19
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    CadenaEntity cadena = (CadenaEntity) request.getAttribute("cadena");
    if(cadena == null) {cadena = new CadenaEntity();}
%>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title><%=cadena.getNombre() != null ? "Editar " : "Crear "%>cadena</title>
    <link rel="stylesheet" href="../../../css/styles.css">
    <link rel="stylesheet" href="../../../css/formulario.css">

</head>
<body>
<main class="main-content">
    <header class="header">
        <h1><%= (cadena.getId()!=null?"Editar":"Crear") %> cadena</h1>
    </header>

    <div class="formulario">
        <form id="form-crear-tienda" action="/cadenas/guardar" method="post">
            <input type="hidden" value="<%=cadena.getId() != null ? cadena.getId() : ""%>" name="id"/>
            <div class="form-group">
                <label for="nombre">Nombre</label>
                <input type="text" name="nombre" id="nombre" required
                       value="<%=cadena.getNombre() != null ? cadena.getNombre() : ""%>" >
            </div>
            <div class="form-group">
                <label for="codigo">Código</label>
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
