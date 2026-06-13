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
    get: [ROLES.ADMIN],
    post: [ROLES.ADMIN],
    put: [ROLES.ADMIN],
    delete: [ROLES.ADMIN]
  },
  usuarios: {
    get: [ROLES.ADMIN, ROLES.COORDINADOR, ROLES.CAPITAN, ROLES.RESPONSABLE_ENTIDAD, ROLES.RESPONSABLE_TIENDA],
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
    get: [ROLES.ADMIN, ROLES.COORDINADOR, ROLES.CAPITAN, ROLES.RESPONSABLE_ENTIDAD, ROLES.RESPONSABLE_TIENDA],
    post: [ROLES.ADMIN],
    put: [ROLES.ADMIN],
    delete: [ROLES.ADMIN]
  },
  campanas: {
    get: [ROLES.ADMIN, ROLES.COORDINADOR, ROLES.CAPITAN, ROLES.RESPONSABLE_ENTIDAD, ROLES.RESPONSABLE_TIENDA],
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
    get: [ROLES.ADMIN, ROLES.CAPITAN, ROLES.COORDINADOR, ROLES.RESPONSABLE_ENTIDAD, ROLES.RESPONSABLE_TIENDA],
    post: [ROLES.ADMIN, ROLES.COORDINADOR],
    put: [ROLES.ADMIN, ROLES.COORDINADOR],
    delete: [ROLES.ADMIN, ROLES.COORDINADOR]
  },
  notificaciones: {
    get: [ROLES.ADMIN, ROLES.CAPITAN, ROLES.COORDINADOR, ROLES.RESPONSABLE_ENTIDAD, ROLES.RESPONSABLE_TIENDA],
    post: [ROLES.ADMIN, ROLES.CAPITAN, ROLES.COORDINADOR, ROLES.RESPONSABLE_ENTIDAD, ROLES.RESPONSABLE_TIENDA],
    put: [ROLES.ADMIN],
    delete: [ROLES.ADMIN, ROLES.CAPITAN, ROLES.COORDINADOR, ROLES.RESPONSABLE_ENTIDAD, ROLES.RESPONSABLE_TIENDA]
  },
  participa: {
    get: [ROLES.ADMIN, ROLES.CAPITAN, ROLES.COORDINADOR, ROLES.RESPONSABLE_ENTIDAD, ROLES.RESPONSABLE_TIENDA],
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

server.get('/dashboard/stats', (req, res) => {
  const db = router.db
  res.json({
    tiendas: db.get('tiendas').value(),
    cadenas: db.get('cadenas').value(),
    campanas: db.get('campanas').value(),
    voluntarioEntidad: db.get('voluntario_entidad').value(),
    voluntarioFisico: db.get('voluntario_fisico').value()
  })
})

server.use(router)

router.render = (req, res) => {
  const data = res.locals.data
  const user = req.user

  if (!user || String(user.id_rol) === ROLES.ADMIN || req.method !== 'GET' || !Array.isArray(data)) {
    return res.json(data)
  }

  const path = req.path.replace(/^\/+/, '').split('/')[0]
  const userId = String(user.id)
  const roleId = String(user.id_rol)

  let filtered = data

  if (path === 'usuarios') {
    filtered = data.map(u => {
      const { password, ...userSinPassword } = u
      return userSinPassword
    })
    return res.json(filtered)
  }

  if (path === 'notificaciones') {
    filtered = data.filter(n => String(n.id_usuario_destino) === userId)
  }
  else if (roleId === ROLES.CAPITAN) {
    if (path === 'tiendas') {
      filtered = data.filter(t => String(t.id_capitan) === userId)
    } else if (path === 'turnos') {
      const tiendas = router.db.get('tiendas').filter(t => String(t.id_capitan) === userId).value()
      const ids = new Set(tiendas.map(t => String(t.id)))
      filtered = data.filter(t => ids.has(String(t.id_tienda)))
    }
  }
  else if (roleId === ROLES.COORDINADOR) {
    if (path === 'tiendas') {
      const participaciones = router.db.get('participa').filter(p => String(p.id_coordinador) === userId).value()
      const ids = new Set(participaciones.map(p => String(p.id_tienda)))
      filtered = data.filter(t => ids.has(String(t.id)))
    } else if (path === 'turnos') {
      const participaciones = router.db.get('participa').filter(p => String(p.id_coordinador) === userId).value()
      const ids = new Set(participaciones.map(p => String(p.id_tienda)))
      filtered = data.filter(t => ids.has(String(t.id_tienda)))
    }
  }
  else if (roleId === ROLES.RESPONSABLE_ENTIDAD) {
    if (path === 'tiendas') {
      const entidades = router.db.get('voluntario_entidad').filter(v => String(v.id_responsable_entidad) === userId).value()
      const vbIds = new Set(entidades.map(e => String(e.id_voluntario)))
      const turnos = router.db.get('turnos').filter(t => vbIds.has(String(t.id_voluntario))).value()
      const tiendaIds = new Set(turnos.map(t => String(t.id_tienda)))
      filtered = data.filter(t => tiendaIds.has(String(t.id))).map(t => ({ id: t.id, nombre: t.nombre, id_cadena: t.id_cadena }))
    } else if (path === 'voluntario_entidad') {
      filtered = data.filter(v => String(v.id_responsable_entidad) === userId)
    } else if (path === 'voluntario_base') {
      const entidades = router.db.get('voluntario_entidad').filter(v => String(v.id_responsable_entidad) === userId).value()
      const ids = new Set(entidades.map(e => String(e.id_voluntario)))
      filtered = data.filter(v => ids.has(String(v.id)))
    } else if (path === 'turnos') {
      const entidades = router.db.get('voluntario_entidad').filter(v => String(v.id_responsable_entidad) === userId).value()
      const ids = new Set(entidades.map(e => String(e.id_voluntario)))
      filtered = data.filter(t => ids.has(String(t.id_voluntario)))
    } else if (path === 'participa') {
      const entidades = router.db.get('voluntario_entidad').filter(v => String(v.id_responsable_entidad) === userId).value()
      const vbIds = new Set(entidades.map(e => String(e.id_voluntario)))
      const turnos = router.db.get('turnos').filter(t => vbIds.has(String(t.id_voluntario))).value()
      const tiendaIds = new Set(turnos.map(t => String(t.id_tienda)))
      filtered = data.filter(p => tiendaIds.has(String(p.id_tienda)))
    }
  }
  else if (roleId === ROLES.RESPONSABLE_TIENDA) {
    if (path === 'tiendas') {
      filtered = data.filter(t => String(t.id_responsable_tienda) === userId)
    } else if (path === 'turnos') {
      const tiendas = router.db.get('tiendas').filter(t => String(t.id_responsable_tienda) === userId).value()
      const ids = new Set(tiendas.map(t => String(t.id)))
      filtered = data.filter(t => ids.has(String(t.id_tienda)))
    } else if (path === 'voluntario_fisico') {
      filtered = data.map(v => ({ id_voluntario: v.id_voluntario, nombre: v.nombre, apellidos: v.apellidos }))
    } else if (path === 'voluntario_entidad') {
      filtered = data.map(v => ({ id_voluntario: v.id_voluntario, nombre_asociacion: v.nombre_asociacion, n_voluntarios: v.n_voluntarios }))
    } else if (path === 'voluntario_base') {
      filtered = data.map(v => ({ id: v.id }))
    }
  }

  res.json(filtered)
}

server.listen(PORT, () => {
  console.log(`Servidor iniciado en http://localhost:${PORT}`)
  console.log(`Endpoints:`)
  console.log(`  POST /login         → Autenticación (público)`)
  console.log(`  GET|POST|PUT|DELETE  → Rutas protegidas por JWT y rol`)
  console.log(`  Filtrado por roles activo en GET (tiendas, turnos, voluntarios, notificaciones)`)
})
