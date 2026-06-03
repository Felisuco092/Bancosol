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

| Rol     | id_rol | GET | POST | PUT | DELETE |
|---------|--------|-----|------|-----|--------|
| Admin   | 1      | Todos los recursos | Todos los recursos | Todos los recursos | Todos los recursos |
| Capitán | 2      | tiendas, turnos, voluntarios, notificaciones | notificaciones | - | notificaciones |
| Coordinador | 3 | tiendas, turnos, voluntarios, notificaciones | turnos, voluntarios, notificaciones | turnos, voluntarios | turnos, notificaciones |
| Responsable Entidad | 4 | turnos, voluntarios, notificaciones | notificaciones | - | notificaciones |
| Responsable Tienda | 5 | tiendas, turnos, notificaciones | notificaciones | - | notificaciones |

Los permisos se definen en `server.js` en el objeto `PERMISOS`.

## Usuarios de prueba

| Usuario  | Contraseña | Rol         |
|----------|-----------|-------------|
| jperez   | hash123   | Admin       |
| mlopez   | hash456   | Coordinador |
| aruiz    | hash789   | Capitán     |
| cgomez   | hash101   | Capitán     |
| psanchez | hash202   | Coordinador |
| lmartinez | hash123   | Responsable Entidad |
| rfernandez | hash123  | Responsable Tienda |
