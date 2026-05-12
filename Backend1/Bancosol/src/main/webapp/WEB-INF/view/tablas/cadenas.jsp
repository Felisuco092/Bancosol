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
                <button class="btn btn-primary btn-sm">Editar</button>
                <button class="btn btn-danger btn-sm">Eliminar</button>
            </td>
        </tr>
    <%}%>
    </tbody>
</table>