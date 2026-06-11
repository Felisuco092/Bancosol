<%--
Página JSP que muestra el formulario para crear y editar colaborador

Autores:
- Félix Jiménez Almanza: 90%
- Jorge Torres Sánchez: 10%

--%>
<%@ page import="uma.grupo13.bancosol.entity.VoluntarioBaseEntity" %>
<%@ page import="uma.grupo13.bancosol.entity.VoluntarioFisicoEntity" %>
<%@ page import="uma.grupo13.bancosol.entity.VoluntarioEntidadEntity" %>
<%@ page import="uma.grupo13.bancosol.dto.VoluntarioDTO" %>
<%@ page import="uma.grupo13.bancosol.dto.UsuarioDTO" %>
<%@ page import="java.util.List" %>
<%@ page import="uma.grupo13.bancosol.services.utils.Permiso" %>
<%@ page import="java.util.Map" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    VoluntarioDTO voluntario = (VoluntarioDTO) request.getAttribute("voluntario");
    if(voluntario == null) {
        voluntario = new VoluntarioDTO();
    }
    boolean esFisico = "FISICO".equals(voluntario.getTipo());
    boolean esEntidad = "ENTIDAD".equals(voluntario.getTipo());
    boolean esCreacion = voluntario.getId() == null;
    Map<Permiso, Boolean> permisos = (Map<Permiso, Boolean>) session.getAttribute("permisos");
%>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Bancosol - Colaboradores - <%= esCreacion ? "Crear" : "Editar" %></title>
    <link rel="stylesheet" href="../../../css/styles.css">
    <link rel="stylesheet" href="../../../css/formulario.css">
    <link rel="icon" type="image/png" href="/assets/LOGO_BANCOSOL_ICONO.png">
</head>
<body>
    <main class="main-content">
        <header class="header">
            <h1><%= esCreacion ? "Crear" : "Editar" %> Colaborador</h1>
        </header>
        <div class="formulario">
            <form id="form-crear-colaborador" action="/colaboradores/guardar" method="post">
                <input type="hidden" value="<%=voluntario.getId() != null ? voluntario.getId() : ""%>" name="id"/>



                <div id="campos-base">
                    <div class="form-group">
                        <label for="domicilio">Domicilio<span class="required">*</span></label>
                        <input type="text" name="domicilio" id="domicilio" required
                               value="<%= voluntario.getDomicilio() != null ? voluntario.getDomicilio() : "" %>" />
                    </div>
                    <div class="form-group">
                        <label for="zona_geografica">Zona Geográfica<span class="required">*</span></label>
                        <input type="text" name="zona_geografica" id="zona_geografica" required
                               value="<%= voluntario.getZonaGeografica() != null ? voluntario.getZonaGeografica() : "" %>" />
                    </div>
                    <div class="form-group">
                        <label for="codigo_postal">Código Postal<span class="required">*</span></label>
                        <input type="number" name="codigo_postal" id="codigo_postal" required
                               value="<%= voluntario.getCodigoPostal() != null ? voluntario.getCodigoPostal() : "" %>" min="0"/>
                    </div>

                    <%
                        if(Boolean.TRUE.equals(permisos.get(Permiso.CONFIRMAR_COLABORADORES))){ %>
                    <div class="form-group" style="display: <%=voluntario.getAprobado() == false ? "block" : "none"%>">
                        <label for="confirmar">Confirmar colaborador</label>
                        <input type="checkbox" id="confirmar" name="confirmar" value="true"
                                <%=voluntario.getAprobado() == true ? "checked" : ""%>/>
                    </div>
                    <%
                        }
                    %>
                </div>


                <div class="form-group" style="display: <%=voluntario.getId() != null ? "none" : "block" %>">
                    <label for="tipo_colaborador">Tipo de Colaborador<span class="required">*</span></label>
                    <select name="tipo_colaborador" id="tipo_colaborador" required >
                        <option value="">-- Seleccione un tipo --</option>
                        <option value="fisico" <%= esFisico ? "selected" : "" %>>Persona Física</option>
                        <option value="entidad" <%= esEntidad ? "selected" : "" %>>Entidad / Grupo</option>
                    </select>
                </div>

                <% if(esFisico) {%>
                <div id="campos-fisico">
                    <div class="form-group">
                        <label for="nombre">Nombre<span class="required">*</span></label>
                        <input type="text" name="nombre" id="nombre" required
                               value="<%= voluntario.getNombre() != null ? voluntario.getNombre() : "" %>" />
                    </div>
                    <div class="form-group">
                        <label for="apellidos">Apellidos<span class="required">*</span></label>
                        <input type="text" name="apellidos" id="apellidos" required
                               value="<%= voluntario.getApellidos() != null ? voluntario.getApellidos() : "" %>" />
                    </div>
                </div>
                <% } else { %>
                <div id="campos-fisico" style="display: none;">
                    <div class="form-group">
                        <label for="nombre">Nombre<span class="required">*</span></label>
                        <input type="text" name="nombre" id="nombre" />
                    </div>
                    <div class="form-group">
                        <label for="apellidos">Apellidos<span class="required">*</span></label>
                        <input type="text" name="apellidos" id="apellidos" />
                    </div>
                </div>
                <% } %>

                <% if(esEntidad) { %>
                <div id="campos-entidad">
                    <div class="form-group">
                        <label for="nombre_asociacion">Nombre de Asociación<span class="required">*</span></label>
                        <input type="text" name="nombre_asociacion" id="nombre_asociacion" required
                               value="<%= voluntario.getNombreAsociacion() != null ? voluntario.getNombreAsociacion() : "" %>" />
                    </div>
                    <div class="form-group">
                        <label for="n_voluntarios">Número de Voluntarios<span class="required">*</span></label>
                        <input type="number" name="n_voluntarios" id="n_voluntarios" required
                               value="<%= voluntario.getNVoluntarios() != null ? voluntario.getNVoluntarios() : "" %>" min="0"/>
                    </div>
                    <div class="form-group">
                        <label for="responsableEntidad">Responsable de entidad: </label>
                        <select name="responsableEntidad" id="responsableEntidad">
                            <option value="">-- Seleccione un responsable --</option>
                            <%
                                List<UsuarioDTO> responsablesEntidad = (List<UsuarioDTO>) request.getAttribute("responsablesEntidad");
                                if (responsablesEntidad != null) {
                                    for(UsuarioDTO resp : responsablesEntidad) {
                            %>
                            <option value="<%=resp.getId()%>" <%= (voluntario.getResponsableEntidad() != null && voluntario.getResponsableEntidad().getId().equals(resp.getId())) ? "selected" : "" %>><%=resp.getNombre()%></option>
                            <%
                                    }
                                }
                            %>
                        </select>
                    </div>
                </div>
                <% } else { %>
                <div id="campos-entidad" style="display: none;">
                    <div class="form-group">
                        <label for="nombre_asociacion">Nombre de Asociación<span class="required">*</span></label>
                        <input type="text" name="nombre_asociacion" id="nombre_asociacion" />
                    </div>
                    <div class="form-group">
                        <label for="n_voluntarios">Número de Voluntarios<span class="required">*</span></label>
                        <input type="number" name="n_voluntarios" id="n_voluntarios" />
                    </div>
                    <div class="form-group">
                        <label for="responsableEntidad">Responsable de entidad: </label>
                        <select name="responsableEntidad" id="responsableEntidad">
                            <option value="">-- Seleccione un responsable --</option>
                        </select>
                    </div>
                </div>
                <% } %>

                <div class="form-actions">
                    <button type="submit" class="btn btn-primary"><%= esCreacion ? "Crear" : "Editar" %> Colaborador</button>
                    <button type="button" class="btn btn-secondary" onclick="history.back()">Cancelar</button>
                </div>
            </form>
        </div>
    </main>

    <script>
        document.addEventListener('DOMContentLoaded', function() {
            const tipoSelect = document.getElementById('tipo_colaborador');
            const camposFisico = document.getElementById('campos-fisico');
            const camposEntidad = document.getElementById('campos-entidad');
            const nombreInput = document.getElementById('nombre');
            const apellidosInput = document.getElementById('apellidos');
            const nombreAsociacionInput = document.getElementById('nombre_asociacion');
            const nVoluntariosInput = document.getElementById('n_voluntarios');

            function toggleCampos() {
                let valueTipo = tipoSelect.value;
                if (valueTipo === 'fisico') {
                    camposFisico.style.display = 'block';
                    camposEntidad.style.display = 'none';
                    nombreInput.required = true;
                    apellidosInput.required = true;
                    nombreAsociacionInput.required = false;
                    nVoluntariosInput.required = false;
                } else if (valueTipo === 'entidad') {
                    camposFisico.style.display = 'none';
                    camposEntidad.style.display = 'block';
                    nombreInput.required = false;
                    apellidosInput.required = false;
                    nombreAsociacionInput.required = true;
                    nVoluntariosInput.required = true;
                } else {
                    camposFisico.style.display = 'none';
                    camposEntidad.style.display = 'none';
                    nombreInput.required = false;
                    apellidosInput.required = false;
                    nombreAsociacionInput.required = false;
                    nVoluntariosInput.required = false;
                }
            }
            toggleCampos();
            tipoSelect.addEventListener('change', toggleCampos);
        });
    </script>
</body>
</html>
