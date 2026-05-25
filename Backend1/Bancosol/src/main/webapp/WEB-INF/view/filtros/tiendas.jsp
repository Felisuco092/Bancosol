<%@ page import="java.util.List" %>
<%@ page import="java.util.Set" %>
<%@ page import="uma.grupo13.bancosol.dto.CadenaDTO" %>
<%@ page import="uma.grupo13.bancosol.dto.CampanaDTO" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%
    List<CadenaDTO> cadenas = (List<CadenaDTO>) request.getAttribute("cadenas");
    List<CampanaDTO> campanas = (List<CampanaDTO>) request.getAttribute("campanas");
    Set<String> localidades = (Set<String>) request.getAttribute("localidades");
    Integer idCampanaActual = (Integer) request.getAttribute("idCampanaActual");
%>

<div>
    <label for="select-filtro-campanas">Campaña:</label>
    <select id="select-filtro-campanas" name="campanas">
        <%
            for (CampanaDTO cam : campanas) {
                String selected = "";
                if (cam.getId() == idCampanaActual) selected = "selected";
        %>
        <option value=<%=cam.getId()%> <%=selected%>><%=cam.getNombre()%>
        </option>
        <%
            }
        %>
    </select>
</div>
<div>
    <label for="filtro-por-cadenas">Cadena:</label>
    <select id="filtro-por-cadenas" name="cadenas">
        <option value="0" selected>Ver todas</option>
        <%
            for (CadenaDTO cad : cadenas) {
        %>
        <option value=<%=cad.getId()%>><%=cad.getNombre()%>
        </option>
        <%
            }
        %>
    </select>
</div>
<div>
    <label for="filtro-por-localidad">Localidad:</label>
    <select id="filtro-por-localidad" name="local">
        <option value="" selected>Todas</option>
        <%
            for (String loc : localidades) {
        %>
        <option value=<%=loc%>><%=loc%>
        </option>
        <%
            }
        %>
    </select>
</div>


