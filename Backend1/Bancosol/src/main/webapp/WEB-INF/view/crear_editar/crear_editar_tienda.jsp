<%@ page import="uma.grupo13.bancosol.entity.TiendaEntity" %>
<%@ page import="uma.grupo13.bancosol.entity.CadenaEntity" %>
<%@ page import="uma.grupo13.bancosol.entity.CampanaEntity" %>
<%@ page import="java.util.List" %>
<%@ page import="uma.grupo13.bancosol.entity.UsuarioEntity" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<%
    TiendaEntity tienda = (TiendaEntity) request.getAttribute("tienda");
    List<CadenaEntity> cadenas = (List<CadenaEntity>) request.getAttribute("cadenas");
    List<CampanaEntity> campanas = (List<CampanaEntity>) request.getAttribute("campanas");
    List<UsuarioEntity> capitanes= (List<UsuarioEntity>) request.getAttribute("capitanes");
%>
<html lang="es">
<%
    if(tienda == null){
        tienda = new TiendaEntity();
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
            <h1><%= (tienda.getId()!=null?"Editar":"Crear") %> tienda</h1>
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
                    <input type="number" name="cPostal" id="cPostal" value="<%=tienda.getCPostal() != null ? tienda.getCPostal() : ""%>" required/>
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
                            for(CadenaEntity cad : cadenas) {
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
                            for(UsuarioEntity capi : capitanes) {
                        %>
                        <option value="<%=capi.getId()%>" <%= (tienda.getCapitan() != null && tienda.getCapitan().getId().equals(capi.getId())) ? "selected" : "" %>><%=capi.getNombre()%></option>
                        <%
                            }
                        %>
                    </select>
                </div>
                <div class="form-group">
                    <label>Campañas en la que participa: </label>
                    <div class="checkbox-group" style="max-height: 200px; overflow-y: auto; border: 1px solid var(--input-border); padding: 10px; border-radius: 4px;">
                        <%
                            for(CampanaEntity campana : campanas) {
                                boolean seleccionado = tienda.participaEn(campana.getId());
                        %>
                        <div class="checkbox-item" style="display: flex; align-items: center; margin-bottom: 0.5rem;">
                            <input type="checkbox" name="campanasParticipa" value="<%=campana.getId()%>" id="campana-<%=campana.getId()%>" <%= seleccionado ? "checked" : "" %> style="width: auto; margin-right: 10px;">
                            <label for="campana-<%=campana.getId()%>" style="margin-bottom: 0; display: inline; font-weight: normal;"><%=campana.getNombre()%></label>
                        </div>
                        <%
                            }
                        %>
                    </div>
                </div>
                <div class="form-actions">
                    <button type="submit" class="btn btn-primary">Guardar</button>
                    <button type="button" class="btn btn-secondary" onclick="history.back()">Cancelar</button>
                </div>
            </form>
        </div>
    </main>
</body>
</html>