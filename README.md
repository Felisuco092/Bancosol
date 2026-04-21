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

---
## FrontEnd 2
Front completo en react para mostrar los datos de la bd.

---
## Levantar base de datos docker
Debes tener instalado docker y docker compose para ello, tienes que dirigirte a la carpeta donde esta el docker-compose.yml de la carpeta db y ejecutar:
```
docker compose up -d
```
Una vez levantada la base de datos podras conectarte a ella, con el usuario, db y contraseña que esta dentro del docker-compose.yml

---

## Páginas a Implementar

### 1. Página de Login (Acceso)
**Descripción:** Pantalla para introducir usuario y contraseña. Dependiendo de quién entre (Administrador, Coordinador, etc.), el menú lateral le mostrará unas páginas u otras.

---

### 2. Cuadro de Mando (Dashboard / Inicio)
**Descripción:** Pantalla principal (Home) al entrar, especialmente para el Administrador. Muestra las gráficas de barras e índices de cobertura por cadenas y localidades.

**Referencia en PDF:** Página 9

---

### 3. Gestión de Campañas
**Descripción:** Permite crear una nueva campaña (ej. "Gran Recogida"), seleccionar el año y marcar con checkboxes qué cadenas de supermercados participan a nivel general.

**Referencia en PDF:** Página 2

---

### 4. Gestión de Tiendas
**Descripción:** Listado interactivo con buscador/filtros (por cadena, localidad o coordinador). Al hacer clic en una tienda, se abre su detalle a la derecha para modificar la dirección, marcar "excepciones" de participación y asignarle un coordinador.

**Referencia en PDF:** Página 3

---

### 5. Gestión de Colaboradores (Voluntarios)
**Descripción:** Muestra el listado de los grupos de voluntarios o entidades (ej. "Ayuntamiento de Almáchar"). Permite dar de alta nuevos colaboradores y añadirles hasta 3 contactos diferentes (teléfono y email).

**Referencia en PDF:** Página 4

---

### 6. Gestión de Usuarios (Coordinadores / Capitanes)
**Descripción:** Pantalla de administración pura para dar de alta a las personas que van a usar la aplicación. Permite crear los usuarios, asignarles una zona geográfica y proteger sus contraseñas.

**Referencia en PDF:** Página 5

---

### 7. Asignación de Turnos (La Operativa)
**Descripción:** Pantalla compleja que usarán a diario los coordinadores durante la campaña. Permite seleccionar una tienda y ver un "cuadrante" con los turnos (Viernes Mañana, Viernes Tarde, etc.). Permite encajar a los colaboradores en franjas horarias específicas y nombrar al capitán de esa tienda.

**Referencia en PDF:** Página 6

---

### 8. Buzón de Incidencias (Feedback)(ignorar por ahora)
**Descripción:** Pantalla de administración para leer y gestionar incidencias/feedback reportadas durante la campaña. Muestra una tabla tipo bandeja de entrada con: Fecha, Tienda, Usuario que reporta y texto de la incidencia.

**Integración en la aplicación:**
- Los Capitanes/Responsables pueden registrar incidencias rápidamente mediante un Modal dentro de las páginas de Tienda o Turnos (botón rojo "Registrar Incidencia").
- Los Administradores/Coordinadores acceden a esta página para revisar todo el feedback y problemas reportados durante el fin de semana.

**Nota:** Requiere una nueva entidad en la base de datos: `IncidenciaEntity` (relacionada con `TiendaEntity` y `UsuarioEntity`).

---

### Bandeja de entrada
**Descripción:** Bandeja para que los usuarios vean sus notificaciones que se envian automaticamente.


# To Do
- 0º Pensar todas las pg, tanto las basicas como las tablas(actualizacion con fetch), edit y crear...
- 1º HTML/css y js de todas las pg.
- 2º Controlador con get para cada pg principal
- 3º Post necesarios
  - /login (post) 
- 4º Control de roles
- 5º Extraer en clase a parte la sidebar en una clase a parte y poner referencia en cada clase .jsp
