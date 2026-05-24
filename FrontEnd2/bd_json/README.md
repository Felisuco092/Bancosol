# JSON Server con Autenticación JWT y Control de Roles

Servidor REST fake con autenticación mediante JWT y control de acceso basado en roles.

## Requisitos previos

Asegúrate de tener **Node.js** instalado en tu sistema.

## Instalación de dependencias

```bash
cd bd_json
pnpm install
```

## Iniciar el servidor

```bash
pnpm start
```

Esto levantará el servidor en `http://localhost:3001`.

## Endpoints

### POST /login (público)
Inicia sesión con usuario y contraseña.

```json
{
  "usuario": "jperez",
  "password": "hash123"
}
```

Respuesta:
```json
{
  "accessToken": "eyJhbGciOiJIUzI1NiIs...",
  "user": { "id": "1", "nombre": "Juan", ... }
}
```

### Rutas protegidas (requieren token)
Todas las demás rutas (`/usuarios`, `/tiendas`, `/cadenas`, etc.) requieren el header:

```
Authorization: Bearer <accessToken>
```

## Control de Roles

| Rol     | id_rol | Permisos |
|---------|--------|----------|
| Admin   | 1      | CRUD completo en todas las tablas |
| Capitán | 2      | Lectura de todo, gestión de turnos |
| Coordinador | 3 | Lectura de todo, gestión de voluntarios/colaboradores |

Los permisos se definen en `server.js` en el objeto `PERMISOS`.

## Usuarios de prueba

| Usuario  | Contraseña | Rol         |
|----------|-----------|-------------|
| jperez   | hash123   | Admin       |
| mlopez   | hash456   | Coordinador |
| aruiz    | hash789   | Capitán     |
| cgomez   | hash101   | Capitán     |
| psanchez | hash202   | Coordinador |
