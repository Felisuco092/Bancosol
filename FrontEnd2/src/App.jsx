import { Route, Routes } from 'react-router-dom'
import MainLayout from './components/MainLayout'
import LoginLayout from './components/LoginLayout'
import LoginPage from './pages/LoginPage'
import DashboardPage from './pages/DashboardPage'
import CampanasPage from './pages/CampanasPage'
import CadenasPage from './pages/CadenasPage'
import TiendasPage from './pages/TiendasPage'
import CrearTiendaPage from './pages/crear_editar_pages/CrearTiendaPage'
import CrearCadenaPage from './pages/crear_editar_pages/CrearCadenaPage'
import CrearCampanaPage from './pages/crear_editar_pages/CrearCampanaPage'
import CrearColaboradorPage from './pages/crear_editar_pages/CrearColaboradorPage'
import CrearTurnoPage from './pages/crear_editar_pages/CrearTurnoPage'
import CrearUsuarioPage from './pages/crear_editar_pages/CrearUsuarioPage'
import VerMensajePage from './pages/crear_editar_pages/VerMensajePage'
import ColaboradoresPage from './pages/ColaboradoresPage'
import UsuariosPage from './pages/UsuariosPage'
import TurnosPage from './pages/TurnosPage'
import BandejaPage from './pages/BandejaPage'
import SideBarMainLayout from './components/SideBarMainLayout'
import { ProtectedRoute } from './components/RutaProtegida'

function App() {
  return (
    <Routes>
      <Route element={<LoginLayout />}>
        <Route path="/" element={<LoginPage />} />
      </Route>

      {/* Rutas accesibles por TODOS los usuarios autenticados */}
      <Route element={<ProtectedRoute allowedRoles={[1, 2, 3, 4, 5]} />}>
        <Route element={<SideBarMainLayout />}>
          <Route path="/dashboard" element={<DashboardPage />} />
          <Route path="/turnos" element={<TurnosPage />} />
          <Route path="/bandeja" element={<BandejaPage />} />
        </Route>
        <Route element={<MainLayout />}>
          <Route path="/bandeja/ver/:id" element={<VerMensajePage />} />
        </Route>
      </Route>

      {/* Rutas para Administrador (Rol 1) */}
      <Route element={<ProtectedRoute allowedRoles={[1]} />}>
        <Route path="/campanas">
          <Route element={<SideBarMainLayout />}>
            <Route index element={<CampanasPage />} />
          </Route>
          <Route element={<MainLayout />}>
            <Route path="crear" element={<CrearCampanaPage />} />
            <Route path="editar/:id" element={<CrearCampanaPage />} />
          </Route>
        </Route>

        <Route path="/cadenas">
          <Route element={<SideBarMainLayout />}>
            <Route index element={<CadenasPage />} />
          </Route>
          <Route element={<MainLayout />}>
            <Route path="crear" element={<CrearCadenaPage />} />
            <Route path="editar/:id" element={<CrearCadenaPage />} />
          </Route>
        </Route>

        <Route path="/usuarios">
          <Route element={<SideBarMainLayout />}>
            <Route index element={<UsuariosPage />} />
          </Route>
          <Route element={<MainLayout />}>
            <Route path="crear" element={<CrearUsuarioPage />} />
            <Route path="editar/:id" element={<CrearUsuarioPage />} />
          </Route>
        </Route>

        {/* El Admin también puede crear/editar tiendas */}
        <Route path="/tiendas">
          <Route element={<MainLayout />}>
            <Route path="crear" element={<CrearTiendaPage />} />
            <Route path="editar/:id" element={<CrearTiendaPage />} />
          </Route>
        </Route>
      </Route>

      {/* Gestión de Tiendas (Listado): Admin, Capitán, Coordinador, Resp. Tienda */}
      <Route element={<ProtectedRoute allowedRoles={[1, 2, 3, 5]} />}>
        <Route path="/tiendas">
          <Route element={<SideBarMainLayout />}>
            <Route index element={<TiendasPage />} />
          </Route>
        </Route>
      </Route>

      {/* Colaboradores (Listado): Admin, Capitán, Coordinador, Resp. Entidad */}
      <Route element={<ProtectedRoute allowedRoles={[1, 2, 3, 4]} />}>
        <Route path="/colaboradores">
          <Route element={<SideBarMainLayout />}>
            <Route index element={<ColaboradoresPage />} />
          </Route>
        </Route>
      </Route>

      {/* Crear/Editar Colaboradores y Turnos: Admin y Coordinador */}
      <Route element={<ProtectedRoute allowedRoles={[1, 3]} />}>
        <Route path="/colaboradores">
          <Route element={<MainLayout />}>
            <Route path="crear" element={<CrearColaboradorPage />} />
            <Route path="editar/:id" element={<CrearColaboradorPage />} />
          </Route>
        </Route>
        <Route path="/turnos">
          <Route element={<MainLayout />}>
            <Route path="crear" element={<CrearTurnoPage />} />
          </Route>
        </Route>
      </Route>
    </Routes>
  )
}

export default App
