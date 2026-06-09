<%--
Página JSP que muestra la tabla de las cadenas en la bd

Autores:
- German Pelaez Gallardo: 20%
- Félix Jiménez Almanza: 80%

--%>
<%@ page import="uma.grupo13.bancosol.entity.CadenaEntity" %>
<%@ page import="java.util.List" %>
<%@ page import="uma.grupo13.bancosol.dto.CadenaDTO" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    List<CadenaDTO> cadenasList = (List<CadenaDTO>) request.getAttribute("cadenas");
%>
<table id="tabla-cadenas" class="tabla-cadenas">
    <thead>
        <tr>
            <th><b>Nombre de Cadena</b></th>
            <th><b>Código</b></th>
            <th><b>Acciones</b></th>
        </tr>
    </thead>
    <tbody id="chain-table-body">
    <%for(CadenaDTO cadenaAct: cadenasList){%>
        <tr>
            <td><%=cadenaAct.getNombre()%></td>
            <td><%=cadenaAct.getCodigo()%></td>
            <td>
                <a href="/cadenas/editar?id=<%=cadenaAct.getId()%>">
                    <button class="btn btn-primary btn-sm">Editar</button>
                </a>
                <form action="/cadenas/borrar" method="post"
                    onsubmit="return confirm('¿Eliminar esta cadena? Se borrarán todas sus tiendas.')">
                    <input type="hidden" name="id" value="<%=cadenaAct.getId()%>" />
                    <button class="btn btn-danger btn-sm">Eliminar</button>
                </form>
            </td>
        </tr>
    <%}%>
    </tbody>
</table>