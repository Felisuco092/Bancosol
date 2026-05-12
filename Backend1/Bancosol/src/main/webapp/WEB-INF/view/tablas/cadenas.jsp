<%@ page import="uma.grupo13.bancosol.entity.CadenaEntity" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    List<CadenaEntity> cadenasList = (List<CadenaEntity>) request.getAttribute("cadenas");
%>
<table id="tabla-cadenas" class="tabla-cadenas">
    <thead>
        <tr>
            <th>Nombre de Cadena</th>
            <th>Código</th>
            <th>Acciones</th>
        </tr>
    </thead>
    <tbody id="chain-table-body">
    <%for(CadenaEntity cadenaAct: cadenasList){%>
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