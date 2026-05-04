<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Bancosol - Usuarios</title>
    <link rel="stylesheet" href="../../css/styles.css">
    <script src="../js/aside.js" defer></script>
</head>
<body>
    <% request.setAttribute("paginaActual", "usuarios"); %>
    <jsp:include page="aside.jsp"/>

    <main class="main-content">
        <header class="header">
            <h1>Gestión de Usuarios</h1>
            <button class="btn btn-primary">+ Crear Usuario</button>
        </header>

        <div class="card">
            <table>
                <thead>
                    <tr>
                        <th>Nombre</th>
                        <th>Apellidos</th>
                        <th>Email</th>
                        <th>Teléfono</th>
                        <th>Rol</th>
                        <th>Área Asignada (Municipio)</th>
                        <th>Acciones</th>
                    </tr>
                </thead>
                <tbody>
                    <tr>
                        <td>Juan</td>
                        <td>Pérez García</td>
                        <td>juan.perez@email.com</td>
                        <td>600123456</td>
                        <td><span class="badge-rol badge-capitan">Capitán</span></td>
                        <td>Málaga Este</td>
                        <td>
                            <button class="btn btn-primary btn-sm">Editar</button>
                            <button class="btn btn-danger btn-sm">Baja</button>
                        </td>
                    </tr>
                    <tr>
                        <td>María</td>
                        <td>López Sánchez</td>
                        <td>m.lopez@email.com</td>
                        <td>611987654</td>
                        <td><span class="badge-rol badge-coordinador">Coordinador</span></td>
                        <td>Almáchar</td>
                        <td>
                            <button class="btn btn-primary btn-sm">Editar</button>
                            <button class="btn btn-danger btn-sm">Baja</button>
                        </td>
                    </tr>
                    <tr>
                        <td>Antonio</td>
                        <td>Ruiz Fernández</td>
                        <td>a.ruiz@email.com</td>
                        <td>622456789</td>
                        <td><span class="badge-rol badge-capitan">Capitán</span></td>
                        <td>Rincón de la Victoria</td>
                        <td>
                            <button class="btn btn-primary btn-sm">Editar</button>
                            <button class="btn btn-danger btn-sm">Baja</button>
                        </td>
                    </tr>
                </tbody>
            </table>
        </div>
    </main>
</body>
</html>
