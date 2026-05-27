<%@ page import="uma.grupo13.bancosol.entity.TurnoEntity" %>
<%@ page import="java.util.List" %>
<%@ page import="uma.grupo13.bancosol.dto.VoluntarioDTO" %>
<%@ page import="uma.grupo13.bancosol.dto.CampanaDTO" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Incidencia</title>
    <link rel="stylesheet" href="../../../css/styles.css">
    <link rel="stylesheet" href="../../../css/formulario.css">
</head>
<%
    List<VoluntarioDTO> voluntarioDTOS = (List<VoluntarioDTO>) request.getAttribute("voluntarios");
    Integer idTurno = (Integer) request.getAttribute("idTurno");
    CampanaDTO campana = (CampanaDTO) request.getAttribute("campana");
%>
<body>
<main class="main-content">
   <header class = "header">
       <h1>Reportar incidencia</h1>

       <div class="formulario">
            <form id="crear-incidencia" action="/turnos/reportar-incidencia" method="post">
                <div class="form-group">
                    <label for="">Especifique </label>
                    <input type="" name="" id="" required value=""/>
                </div>

                <div class="form-group">
                    <label for="">Especifique </label>
                    <input type="" name="" id="" required value=""/>
                </div>

                <div class="form-group">
                    <label for="">Especifique </label>
                    <input type="" name="" id="" required value=""/>
                </div>

                <div class="form-group">
                    <label for="">Especifique </label>
                    <input type="" name="" id="" required value=""/>
                </div>
            </form>

       </div>
   </header>
</main>
</body>
</html>