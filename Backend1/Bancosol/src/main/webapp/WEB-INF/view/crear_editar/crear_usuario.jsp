<%@ page import="uma.grupo13.bancosol.entity.UsuarioEntity" %>
<%@ page import="uma.grupo13.bancosol.entity.RolEntity" %>
<%@ page import="java.util.List" %>
<%@ page import="uma.grupo13.bancosol.dto.UsuarioDTO" %>
<%@ page import="uma.grupo13.bancosol.dto.RolDTO" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Bancosol - Usuarios - Crear</title>
    <link rel="stylesheet" href="../../../css/styles.css">
    <link rel="stylesheet" href="../../../css/formulario.css">
<%
UsuarioDTO usuario= (UsuarioDTO) request.getAttribute("usuario");
List<RolDTO> roles= (List<RolDTO>) request.getAttribute("roles");
%>
</head>
<body>

    <main class="main-content">
        <header class="header">
            <%
                if(usuario.getId()==null){
                    %>
                    <h1>Crear Usuario</h1>
            <%
                }else{ %>
                    <h1>Editar Usuario</h1>
            <%
                }
            %>

        </header>

        <div class="formulario">
            <form id="form-crear-usuario" action="/usuarios/guardar" method="post">
                <%
                    if(usuario.getId()!=null){
                %>
                <input type="hidden" name="id" value="<%=usuario.getId()%>" size="100" maxlength="100">
                <%
                    }
                %>


                <div class="form-group">
                    <label for="nombre">Nombre</label>
                    <input type="text" name="nombre" id="nombre" value="<%=usuario.getNombre() == null ? "" : usuario.getNombre()%>" required/>
                </div>
                <div class="form-group">
                    <label for="apellidos">Apellidos</label>
                    <input type="text" name="apellidos" id="apellidos" value="<%=usuario.getApellidos() == null ? "" : usuario.getApellidos()%>" required/>
                </div>
                <div class="form-group">
                    <label for="user">Usuario</label>
                    <input type="text" name="user" id="user" value="<%=usuario.getUsuario() == null ? "" : usuario.getUsuario()%>" required/>
                </div>
                <div class="form-group">
                    <label for="password">Contraseña</label>
                    <input type="password" name="password" id="password" <%= (usuario.getId() == null) ? "required" : "" %>/>
                </div>
                <div class="form-group">
                    <label for="email">Email</label>
                    <input type="text" name="email" id="email" value="<%=usuario.getEmail() == null ? "" : usuario.getEmail()%>" required/>
                </div>
                <div class="form-group">
                    <label for="telefono">Teléfono</label>
                    <input type="tel" name="telefono" id="telefono" value="<%=usuario.getTelefono() == null ? "" : usuario.getTelefono()%>"/>
                </div>
                <div class="form-group">
                    <label for="area">Área Asignada</label>
                    <input type="text" name="area" id="area" value="<%=usuario.getAreaAsignada() == null ? "" : usuario.getAreaAsignada()%>"/>
                </div>
                <div class="form-group">
                    <label for="rol">Rol</label>
                    <select name="rol" id="rol">
                        <option value="">-- Seleccione una cadena --</option>
                        <%
                            for(RolDTO rol : roles) {
                        %>
                        <option value="<%=rol.getId()%>"><%=rol.getNombre()%></option>
                        <%
                            }
                        %>
                    </select>
                </div>

                <div class="form-actions">
                    <input class="btn btn-secondary" type="submit" value="Guardar">
                    <button type="button" class="btn btn-secondary" onclick="history.back()">Cancelar</button>
                </div>
            </form>
        </div>
    </main>
</body>
</html>