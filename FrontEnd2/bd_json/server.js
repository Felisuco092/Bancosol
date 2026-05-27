//Servidor hecho con IA para simular un backend con autenticación JWT y control de roles.
const jsonServer = require('json-server')
const jwt = require('jsonwebtoken')
const bcrypt = require('bcryptjs')

const JWT_SECRET = process.env.JWT_SECRET || 'bancosol_secret_key_2026'
const JWT_EXPIRES = '24h'
const PORT = process.env.PORT || 3001

const server = jsonServer.create()
const router = jsonServer.router('db.json')
const middlewares = jsonServer.defaults({ noCors: false })

server.use(middlewares)
server.use(jsonServer.bodyParser)

const ROLES = {
  ADMIN: "1",
  CAPITAN: "2",
  COORDINADOR: "3",
  RESPONSABLE_ENTIDAD: "4",
  RESPONSABLE_TIENDA: "5"
}

const PERMISOS = {
  roles: {
    get: [ROLES.ADMIN, ROLES.CAPITAN, ROLES.COORDINADOR],
    post: [ROLES.ADMIN],
    put: [ROLES.ADMIN],
    delete: [ROLES.ADMIN]
  },
  usuarios: {
    get: [ROLES.ADMIN, ROLES.CAPITAN, ROLES.COORDINADOR, ROLES.RESPONSABLE_ENTIDAD, ROLES.RESPONSABLE_TIENDA],
    post: [ROLES.ADMIN],
    put: [ROLES.ADMIN],
    delete: [ROLES.ADMIN]
  },
  tiendas: {
    get: [ROLES.ADMIN, ROLES.CAPITAN, ROLES.COORDINADOR, ROLES.RESPONSABLE_ENTIDAD, ROLES.RESPONSABLE_TIENDA],
    post: [ROLES.ADMIN],
    put: [ROLES.ADMIN],
    delete: [ROLES.ADMIN]
  },
  cadenas: {
    get: [ROLES.ADMIN, ROLES.CAPITAN, ROLES.COORDINADOR, ROLES.RESPONSABLE_ENTIDAD, ROLES.RESPONSABLE_TIENDA],
    post: [ROLES.ADMIN],
    put: [ROLES.ADMIN],
    delete: [ROLES.ADMIN]
  },
  campanas: {
    get: [ROLES.ADMIN, ROLES.CAPITAN, ROLES.COORDINADOR, ROLES.RESPONSABLE_ENTIDAD, ROLES.RESPONSABLE_TIENDA],
    post: [ROLES.ADMIN],
    put: [ROLES.ADMIN],
    delete: [ROLES.ADMIN]
  },
  voluntario_base: {
    get: [ROLES.ADMIN, ROLES.CAPITAN, ROLES.COORDINADOR, ROLES.RESPONSABLE_ENTIDAD, ROLES.RESPONSABLE_TIENDA],
    post: [ROLES.ADMIN, ROLES.COORDINADOR],
    put: [ROLES.ADMIN, ROLES.COORDINADOR],
    delete: [ROLES.ADMIN]
  },
  voluntario_entidad: {
    get: [ROLES.ADMIN, ROLES.CAPITAN, ROLES.COORDINADOR, ROLES.RESPONSABLE_ENTIDAD, ROLES.RESPONSABLE_TIENDA],
    post: [ROLES.ADMIN, ROLES.COORDINADOR],
    put: [ROLES.ADMIN, ROLES.COORDINADOR],
    delete: [ROLES.ADMIN]
  },
  voluntario_fisico: {
    get: [ROLES.ADMIN, ROLES.CAPITAN, ROLES.COORDINADOR, ROLES.RESPONSABLE_ENTIDAD, ROLES.RESPONSABLE_TIENDA],
    post: [ROLES.ADMIN, ROLES.COORDINADOR],
    put: [ROLES.ADMIN, ROLES.COORDINADOR],
    delete: [ROLES.ADMIN]
  },
  turnos: {
    get: [ROLES.ADMIN, ROLES.CAPITAN, ROLES.COORDINADOR],
    post: [ROLES.ADMIN, ROLES.CAPITAN],
    put: [ROLES.ADMIN, ROLES.CAPITAN],
    delete: [ROLES.ADMIN]
  },
  notificaciones: {
    get: [ROLES.ADMIN, ROLES.CAPITAN, ROLES.COORDINADOR],
    post: [ROLES.ADMIN],
    put: [ROLES.ADMIN],
    delete: [ROLES.ADMIN]
  },
  participa: {
    get: [ROLES.ADMIN, ROLES.CAPITAN, ROLES.COORDINADOR],
    post: [ROLES.ADMIN],
    put: [ROLES.ADMIN],
    delete: [ROLES.ADMIN]
  }
}

function getRecurso(path) {
  const parts = path.split('/')
  return parts[0] || null
}

function getMethod(req) {
  const method = req.method.toUpperCase()
  if (method === 'GET') return 'get'
  if (method === 'POST') return 'post'
  if (method === 'PUT' || method === 'PATCH') return 'put'
  if (method === 'DELETE') return 'delete'
  return null
}

server.post('/login', (req, res) => {
  const { usuario, password } = req.body
  if (!usuario || !password) {
    return res.status(400).json({ error: 'Usuario y contraseña son obligatorios' })
  }

  const user = router.db.get('usuarios').find({ usuario }).value()
  if (!user) {
    return res.status(401).json({ error: 'Usuario o contraseña incorrectos' })
  }

  const passwordValida = bcrypt.compareSync(password, user.password)
  if (!passwordValida) {
    return res.status(401).json({ error: 'Usuario o contraseña incorrectos' })
  }

  const token = jwt.sign(
    {
      id: user.id,
      usuario: user.usuario,
      nombre: user.nombre,
      apellidos: user.apellidos,
      email: user.email,
      id_rol: user.id_rol
    },
    JWT_SECRET,
    { expiresIn: JWT_EXPIRES }
  )

  const { password: _, ...userSinPassword } = user
  res.json({ accessToken: token, user: userSinPassword })
})

server.use((req, res, next) => {
  const publicas = ['/login']
  if (publicas.includes(req.path)) {
    return next()
  }

  const authHeader = req.headers.authorization
  if (!authHeader || !authHeader.startsWith('Bearer ')) {
    return res.status(401).json({ error: 'Token de acceso requerido' })
  }

  try {
    const decoded = jwt.verify(authHeader.split(' ')[1], JWT_SECRET)
    req.user = decoded
    const path = req.path.startsWith('/') ? req.path.slice(1) : req.path
    const recurso = getRecurso(path)
    const method = getMethod(req)

    if (method === 'post' && recurso === 'usuarios') {
      const { password } = req.body
      if (password) {
        req.body.password = bcrypt.hashSync(password, 12)
      }
    }

    if (String(req.user.id_rol) === String(ROLES.ADMIN)) {
      return next()
    }

    

    if (!recurso || !method) {
      return next()
    }

    const permiso = PERMISOS[recurso]
    if (!permiso) {
      return next()
    }

    const rolesPermitidos = permiso[method]
    if (!rolesPermitidos || !rolesPermitidos.includes(String(req.user.id_rol))) {
      return res.status(403).json({ error: 'No tienes permisos para esta operación' })
    }
    

    next()
  } catch (err) {
    return res.status(403).json({ error: 'Token inválido o expirado' })
  }
})

server.use(router)

server.listen(PORT, () => {
  console.log(`Servidor iniciado en http://localhost:${PORT}`)
  console.log(`Endpoints:`)
  console.log(`  POST /login         → Autenticación (público)`)
  console.log(`  GET|POST|PUT|DELETE  → Rutas protegidas por JWT y rol`)
})
