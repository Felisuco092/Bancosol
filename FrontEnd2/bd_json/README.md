# JSON Server - Configuración

## Requisitos previos

Asegúrate de tener **Node.js** instalado en tu sistema.

## Instalación de dependencias

1. Abre una terminal
2. Navega a la carpeta `Frontend1/nuevo/bd_json`:
   ```bash
   cd Frontend1/nuevo/bd_json
   ```
3. Instala las dependencias:
   ```bash
   npm install
   ```

Este comando instala json-server y sus dependencias desde el `package.json`.

## Inicializar la DB

Con las dependencias instaladas, para iniciar json-server con el archivo `db.json`, ejecuta:

```bash
npx json-server db.json --port 3001
```

Esto levantará un servidor REST fake en `http://localhost:3001`.

## Estructura del archivo db.json

El archivo `db.json` contiene las siguientes colecciones de datos:

- **roles**: id, nombre (Administrador, Capitán, Coordinador, Responsable entidad)
- **usuarios**: id, nombre, apellidos, usuario, contrasena, email, telefono, area_asignada, id_rol
- **cadenas**: id, nombre, codigo
- **tiendas**: id, descripcion, localidad, domicilio, c_postal, zona_geografica, id_cadena, id_capitan
- **campanas**: id, nombre, ano, dia_comienzo, dia_final
- **voluntarios**: id, nombre_entidad, persona_fisica, domicilio, localidad, codigo_postal, n_voluntarios, observaciones
- **turnos**: id, dia, hora_inicio, hora_fin, id_campana, id_voluntario, id_tienda
- **notificaciones**: id, id_usuario_destino, fecha_creacion, mensaje
- **participa**: id, id_campana, id_tienda, id_coordinador

Ejemplo de estructura:

JSON Server generará automáticamente los endpoints:
- `GET /usuarios`
- `GET /usuarios/1`
- `POST /usuarios`
- `PUT /usuarios/1`
- `DELETE /usuarios/1`
- etc.