<%@ page import="uma.grupo13.bancosol.entity.TiendaEntity" %>
<%@ page import="uma.grupo13.bancosol.entity.CadenaEntity" %>
<%@ page import="uma.grupo13.bancosol.entity.CampanaEntity" %>
<%@ page import="java.util.List" %>
<%@ page import="uma.grupo13.bancosol.entity.UsuarioEntity" %>
<%@ page import="uma.grupo13.bancosol.dto.TiendaDTO" %>
<%@ page import="uma.grupo13.bancosol.dto.CadenaDTO" %>
<%@ page import="uma.grupo13.bancosol.dto.CampanaDTO" %>
<%@ page import="uma.grupo13.bancosol.dto.UsuarioDTO" %>
<%@ page import="uma.grupo13.bancosol.dto.ParticipaDTO" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<%
    TiendaDTO tienda = (TiendaDTO) request.getAttribute("tienda");
    List<CadenaDTO> cadenas = (List<CadenaDTO>) request.getAttribute("cadenas");
    List<CampanaDTO> campanas = (List<CampanaDTO>) request.getAttribute("campanas");
    List<UsuarioDTO> capitanes= (List<UsuarioDTO>) request.getAttribute("capitanes");
    List<ParticipaDTO> participacionesTienda = (List<ParticipaDTO>) request.getAttribute("participacionesTienda");
    List<UsuarioDTO> coordinadores = (List<UsuarioDTO>) request.getAttribute("coordinadores");
%>
<html lang="es">
<%
    if(tienda == null){
        tienda = new TiendaDTO();
    }
%>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Bancosol - Tiendas - Crear</title>
    <link rel="stylesheet" href="../../../css/styles.css">
    <link rel="stylesheet" href="../../../css/formulario.css">
</head>
<body>

    <main class="main-content">
        <header class="header">
            <%
                if(tienda.getId()==null){
            %>
            <h1>Crear Tienda</h1>
            <%
            }else{ %>
            <h1>Editar Tienda</h1>
            <%
                }
            %>
        </header>

        <div class="formulario">
            <form id="form-crear-tienda" action="/tiendas/guardar" method="post">
                <%
                    if(tienda.getId()!=null){
                %>
                <input type="hidden" name="id" value="<%=tienda.getId()%>">
                <%
                    }
                %>
                <div class="form-group">
                    <label for="descripcion">Nombre de la tienda: </label>
                    <input type="text" name="descripcion" id="descripcion" value="<%=tienda.getDescripcion() != null ? tienda.getDescripcion() : ""%>" required/>
                </div>
                <div class="form-group">
                    <label for="localidad">Localidad:</label>
                    <input type="text" name="localidad" id="localidad" value="<%=tienda.getLocalidad() != null ? tienda.getLocalidad() : ""%>" required/>
                </div>
                <div class="form-group">
                    <label for="domicilio">Domicilio:</label>
                    <input type="text" name="domicilio" id="domicilio" value="<%=tienda.getDomicilio() != null ? tienda.getDomicilio() : ""%>" required/>
                </div>
                <div class="form-group">
                    <label for="cPostal">Código Postal:</label>
                    <input type="number" name="cPostal" id="cPostal" value="<%=tienda.getCPostal() != null ? tienda.getCPostal() : ""%>" min="0" required/>
                </div>
                <div class="form-group">
                    <label for="zonaGeografica">Zona Geográfica: </label>
                    <input type="text" name="zonaGeografica" id="zonaGeografica" value="<%=tienda.getZonaGeografica() != null ? tienda.getZonaGeografica() : ""%>" required/>
                </div>
                <div class="form-group">
                    <label for="cadena">Cadena: </label>
                    <select name="cadena" id="cadena" required>
                        <option value="">-- Seleccione una cadena --</option>
                        <%
                            for(CadenaDTO cad : cadenas) {
                        %>
                        <option value="<%=cad.getId()%>" <%= (tienda.getCadena() != null && tienda.getCadena().getId().equals(cad.getId())) ? "selected" : "" %>><%=cad.getNombre()%></option>
                        <%
                            }
                        %>
                    </select>
                </div>
                <div class="form-group">
                    <label for="capitan">Capitán: </label>
                    <select name="capitan" id="capitan">
                        <option value="">-- Seleccione un capitán --</option>
                        <%
                            for(UsuarioDTO capi : capitanes) {
                        %>
                        <option value="<%=capi.getId()%>" <%= (tienda.getCapitan() != null && tienda.getCapitan().getId().equals(capi.getId())) ? "selected" : "" %>><%=capi.getNombre()%></option>
                        <%
                            }
                        %>
                    </select>
                </div>
                <div class="form-group">
                    <label for="responsableTienda">Responsable de tienda: </label>
                    <select name="responsableTienda" id="responsableTienda">
                        <option value="">-- Seleccione un responsable --</option>
                        <%
                            List<UsuarioDTO> responsablesTienda = (List<UsuarioDTO>) request.getAttribute("responsablesTienda");
                            if (responsablesTienda != null) {
                                for(UsuarioDTO resp : responsablesTienda) {
                        %>
                        <option value="<%=resp.getId()%>" <%= (tienda.getResponsableTienda() != null && tienda.getResponsableTienda().getId().equals(resp.getId())) ? "selected" : "" %>><%=resp.getNombre()%></option>
                        <%
                                }
                            }
                        %>
                    </select>
                </div>
                <div class="form-group">
                    <label>Campañas en la que participa: </label>
                    <table>
                        <thead>
                            <tr>
                                <th>Campaña</th>
                                <th>Participa</th>
                                <th>Coordinador</th>
                            </tr>
                        </thead>
                        <tbody>
                            <%
                                for (CampanaDTO campana : campanas) {
                                    boolean seleccionado = tienda.participaEn(campana.getId());
                                    Integer idCoordAsignado = null;
                                    for (ParticipaDTO p : participacionesTienda) {
                                        if (p.getIdCampana().equals(campana.getId()) && p.getCoordinador() != null) {
                                            idCoordAsignado = p.getCoordinador().getId();
                                            break;
                                        }
                                    }
                            %>
                            <tr>
                                <td><%=campana.getNombre()%></td>
                                <td>
                                    <input type="checkbox" name="campanasParticipa" value="<%=campana.getId()%>" id="campana-<%=campana.getId()%>" <%= seleccionado ? "checked" : "" %> class="check-participa" style="width: auto;">
                                </td>
                                <td>
                                    <select name="coordinador_<%=campana.getId()%>" id="coord-<%=campana.getId()%>" <%= seleccionado ? "" : "disabled" %>>
                                        <option value="">-- Sin coordinador --</option>
                                        <%
                                            for (UsuarioDTO coord : coordinadores) {
                                        %>
                                        <option value="<%=coord.getId()%>" <%= (idCoordAsignado != null && idCoordAsignado.equals(coord.getId())) ? "selected" : "" %>><%=coord.getNombre()%></option>
                                        <%
                                            }
                                        %>
                                    </select>
                                </td>
                            </tr>
                            <%
                                }
                            %>
                        </tbody>
                    </table>
                </div>
                <script>
                    document.querySelectorAll('input[name="campanasParticipa"]').forEach(function(cb) {
                        cb.addEventListener('change', function() {
                            var select = document.getElementById('coord-' + this.value);
                            if (select) {
                                select.disabled = !this.checked;
                            }
                        });
                    });
                </script>
                <div class="form-actions">
                    <button type="submit" class="btn btn-primary">Guardar</button>
                    <button type="button" class="btn btn-secondary" onclick="history.back()">Cancelar</button>
                </div>
            </form>
        </div>
    </main>
</body>
</html>