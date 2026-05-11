# Bancosol
Trabajo en grupo de la asignatura de tecnología de clientes y servidor para aplicaciones web.

---
## BackEnd 1
Backend completo con jsp para mostrar los datos de la bd.

---
## BackEnd 2
Backend sin jsp, que que se implementa junto a FrontEnd 2.

---
## FrontEnd 1
Front montado por html, css y js sin datos de la BD.

Instrucciones de como montar el serve-json en [Guia Json-server](./FrontEnd1/nuevo/bd_json/README.md)

---
## FrontEnd 2
Front completo en react para mostrar los datos de la bd.

---
## Levantar base de datos docker
Debes tener instalado docker y docker compose para ello, tienes que dirigirte a la carpeta donde esta el docker-compose.yml con schema.sql de la carpeta db, abrir el terminal en ese directorio y ejecutar:
```
docker compose up -d
```
Una vez levantada la base de datos podras conectarte a ella, con el usuario, db y contraseña que esta dentro del docker-compose.yml.
Importante seleccionar la base de datos de PostgreSQL y clicar en apply.

---

## Páginas a Implementar

### 1. Página de Login (Acceso)
**Descripción:** Pantalla para introducir usuario y contraseña. Dependiendo de quién entre (Administrador, Coordinador, etc.), el menú lateral le mostrará unas páginas u otras.

---

### 2. Cuadro de Mando (Dashboard / Inicio)
**Descripción:** Pantalla principal (Home) al entrar, especialmente para el Administrador. Muestra las gráficas de barras e índices de cobertura por cadenas y localidades.

---

### 3. Gestión de Campañas
**Descripción:** Permite crear una nueva campaña (ej. "Gran Recogida"), seleccionar el año y marcar con checkboxes qué cadenas de supermercados participan a nivel general.

---

### 4. Gestión de Tiendas
**Descripción:** Listado interactivo con buscador/filtros (por cadena, localidad o coordinador). Al hacer clic en una tienda, se abre su detalle a la derecha para modificar la dirección, marcar "excepciones" de participación y asignarle un coordinador.

---

### 5. Gestión de Colaboradores (Voluntarios)
**Descripción:** Muestra el listado de los grupos de voluntarios o entidades (ej. "Ayuntamiento de Almáchar"). Permite dar de alta nuevos colaboradores y añadirles hasta 3 contactos diferentes (teléfono y email).

---

### 6. Gestión de Usuarios (Coordinadores / Capitanes)
**Descripción:** Pantalla de administración pura para dar de alta a las personas que van a usar la aplicación. Permite crear los usuarios, asignarles una zona geográfica y proteger sus contraseñas.

---

### 7. Asignación de Turnos (La Operativa)
**Descripción:** Pantalla compleja que usarán a diario los coordinadores durante la campaña. Permite seleccionar una tienda y ver un "cuadrante" con los turnos (Viernes Mañana, Viernes Tarde, etc.). Permite encajar a los colaboradores en franjas horarias específicas y nombrar al capitán de esa tienda.

---

### 8. Buzón de Incidencias (Feedback)(ignorar por ahora)
**Descripción:** Pantalla de administración para leer y gestionar incidencias/feedback reportadas durante la campaña. Muestra una tabla tipo bandeja de entrada con: Fecha, Tienda, Usuario que reporta y texto de la incidencia.

**Integración en la aplicación:**
- Los Capitanes/Responsables pueden registrar incidencias rápidamente mediante un Modal dentro de las páginas de Tienda o Turnos (botón rojo "Registrar Incidencia").
- Los Administradores/Coordinadores acceden a esta página para revisar todo el feedback y problemas reportados durante el fin de semana.

**Nota:** Requiere una nueva entidad en la base de datos: `IncidenciaEntity` (relacionada con `TiendaEntity` y `UsuarioEntity`).

### 9. Crear/Editar objetos que se añaden a la base de datos
**Descripción:** Crear modelos de página en la vista (con su javaScript correspondiente) que permitan la creación, la edición de nuevos objetos que se puedan añadir a la base de datos: ya sea una campaña nueva, una tienda, un usuario. Tareas Asignadas:
**Germán: Crear/Editar campaña y tienda**
**Félix: Crear/Editar cadena y voluntario**
**Jorge: Crear/Editar usuario**

---

### Bandeja de entrada
**Descripción:** Bandeja para que los usuarios vean sus notificaciones que se envian automaticamente.


# To Do
- 0º Pensar todas las páginas, tanto las basicas como las tablas(actualizacion con fetch), edit y crear.
- 1º HTML/css y js de todas las páginas.
  - Listas index, campañas, bandeja, turnos y editarCrear
- 2º Controlador con gets y post
  - Para cada una de los controladores (gets ya creados y algunos posts(botones+login+logout))
    - Pulir Gets (atributos) y hacer los posts(login y logout PUNTO 3.2) Tener en cuenta 3.1
    - Seperar tablas a jsp aparte y modificar lo q haga falta de los jsp de las 8 páginas + aside.
    - Pasar los js a controler(solo los q haga falta)
    - Paginas de editar/crear(necesita datos)(las q necesiten)
  - HECHO(contando todos los jsp relacionados): Aside; ControllerBase, UsuariosController, CadenaController.
  - HACIENDO: BandejaController CampanaController
  - PENDIENTE: 
- 3º Control de sesion(necesita datos)
  - Si sesion, redirect a dashboard y los demas a index sino sesión(todos los gets y posts)
  - /login (medio hecho) /logout hecho
- 4º Control de roles
- 5º Extraer en clase a parte la sidebar en una clase a parte y añadir en cada clase .jsp dentro de Frontend1
- 6º Crear js para emular petición y que devuelve un json. mock de base de datos con un timeout
- 7º Service
- 8º Crear clase cadena en frontend y backend que visualice todas las cadenas existentes y las que participen en las campañas ajustandose al modelo de otras clases existentes
- 9º DTO y Service
- 10º Recarga de tablas(script de fetch)(necesita datos)
- 11º Limpieza de capitanes si no se confirman en x tiempo
- 12º React
- 13º Confirmación al borrar

![Paso 0](https://img.shields.io/badge/Paso_0-Completo-green)
![Paso 1](https://img.shields.io/badge/Paso_1-Completo-green)
![Paso 2](https://img.shields.io/badge/Paso_2-En_proceso-yellow)
![Paso 3](https://img.shields.io/badge/Paso_3-En_proceso-yellow)
![Paso 4](https://img.shields.io/badge/Paso_4-Pendiente-red)
![Paso 5](https://img.shields.io/badge/Paso_5-Completo-green)
![Paso 6](https://img.shields.io/badge/Paso_6-Completo-green)
![Paso 7](https://img.shields.io/badge/Paso_7-Pendiente-red)
![Paso 8](https://img.shields.io/badge/Paso_8-En_proceso-yellow)
![Paso 9](https://img.shields.io/badge/Paso_9-Pendiente-red)
![Paso 10](https://img.shields.io/badge/Paso_10-A_empezar-orange)
![Paso 11](https://img.shields.io/badge/Paso_11-Opcional-purple)
![Paso 12](https://img.shields.io/badge/Paso_12-Pendiente-red)
![Paso 13](https://img.shields.io/badge/Paso_13-Opcional-purple)



