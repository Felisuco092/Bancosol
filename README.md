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
- 0º Pensar todas las páginas, tanto las basicas como las tablas(actualizacion con fetch), edit y crear.
- 1º HTML/css y js de todas las páginas.
  - Listas index, campañas, bandeja y editarCrear
- 2º Controlador con get para cada página principal
- 3º Post necesarios
  - /login (post) 
- 4º Control de roles
- 5º Extraer en clase a parte la sidebar en una clase a parte y añadir en cada clase .jsp dentro de Frontend1
- 6º Crear js para emular petición y que devuelve un json. mock de base de datos con un timeout
- 6º Service
- 7º DTO 

![Paso 0](https://img.shields.io/badge/Paso_0-Completo-green)
![Paso 1](https://img.shields.io/badge/Paso_1-En_proceso_(4/9_html)-yellow)
![Paso 2](https://img.shields.io/badge/Paso_2-Pendiente-red)
![Paso 3](https://img.shields.io/badge/Paso_3-Pendiente-red)
![Paso 4](https://img.shields.io/badge/Paso_4-Pendiente-red)
![Paso 5](https://img.shields.io/badge/Paso_5-Completo-green)
![Paso 6](https://img.shields.io/badge/Paso_6-Pendiente-red)
![Paso 7](https://img.shields.io/badge/Paso_7-Pendiente-red)

![LC](https://tenor.com/es/view/agnes-tachyon-low-cortisol-gif-6147390476112029717.gif)

![que](https://tenor.com/es/view/double-speech-bubble-speechbubble-speech-bubble-umamusume-low-cortisol-gif-5557824492912855663.gif)

![SD](https://tenor.com/es/view/satono-diamond-uma-musume-satono-satono-diamond-worm-gif-10400485730432458344.gif)

![Leon](https://tenor.com/es/view/leon-kennedy-gif-14428209944621691527.gif)

![MbappeIsListening](https://tenor.com/es/view/kylian-mbappe-mbappe-i-would-rather-kylian-mbappe-i-would-rather-gif-3637069057647683703.gif)
