import { Route, Routes } from 'react-router-dom'
import MainLayout from './components/MainLayout'
import LoginLayout from './components/LoginLayout'
import LoginPage from './pages/LoginPage'
import DashboardPage from './pages/DashboardPage'
import CampanasPage from './pages/CampanasPage'
import CadenasPage from './pages/CadenasPage'
import TiendasPage from './pages/TiendasPage'
import CrearEditarTiendaPage from './pages/crear_editar_pages/CrearEditarTiendaPage'
import CrearEditarCadenaPage from './pages/crear_editar_pages/CrearEditarCadenaPage'
import CrearEditarCampanaPage from './pages/crear_editar_pages/CrearEditarCampanaPage'
import CrearEditarColaboradorPage from './pages/crear_editar_pages/CrearEditarColaboradorPage'
import CrearTurnoPage from './pages/crear_editar_pages/CrearTurnoPage'
import CrearEditarUsuarioPage from './pages/crear_editar_pages/CrearEditarUsuarioPage'
import VerMensajePage from './pages/crear_editar_pages/VerMensajePage'
import ColaboradoresPage from './pages/ColaboradoresPage'
import UsuariosPage from './pages/UsuariosPage'
import TurnosPage from './pages/TurnosPage'
import BandejaPage from './pages/BandejaPage'
import SideBarMainLayout from './components/SideBarMainLayout'
import { ProtectedRoute } from './components/RutaProtegida'
import IncidenciasPage from './pages/IncidenciasPage'
import { Roles } from './utils/constants'

function App() {
  return (
    <Routes>
      <Route element={<LoginLayout />}>
        <Route path="/" element={<LoginPage />} />
      </Route>

      
      <Route element={<ProtectedRoute allowedRoles={[Roles.ADMIN, Roles.CAPITAN, Roles.COORDINADOR, Roles.RESP_ENTIDAD, Roles.RESP_TIENDA]} />}>
        <Route element={<SideBarMainLayout />}>
          <Route path="/dashboard" element={<DashboardPage />} />
          <Route path="/turnos" element={<TurnosPage />} />
          <Route path="/bandeja" element={<BandejaPage />} />
        </Route>
        <Route element={<MainLayout />}>
          <Route path="/bandeja/ver/:id" element={<VerMensajePage />} />
        </Route>
      </Route>

      
      <Route element={<ProtectedRoute allowedRoles={[Roles.ADMIN]} />}>
        <Route path="/campanas">
          <Route element={<SideBarMainLayout />}>
            <Route index element={<CampanasPage />} />
          </Route>
          <Route element={<MainLayout />}>
            <Route path="crear" element={<CrearEditarCampanaPage />} />
            <Route path="editar/:id" element={<CrearEditarCampanaPage />} />
          </Route>
        </Route>

        <Route path="/cadenas">
          <Route element={<SideBarMainLayout />}>
            <Route index element={<CadenasPage />} />
          </Route>
          <Route element={<MainLayout />}>
            <Route path="crear" element={<CrearEditarCadenaPage />} />
            <Route path="editar/:id" element={<CrearEditarCadenaPage />} />
          </Route>
        </Route>

        <Route path="/usuarios">
          <Route element={<SideBarMainLayout />}>
            <Route index element={<UsuariosPage />} />
          </Route>
          <Route element={<MainLayout />}>
            <Route path="crear" element={<CrearEditarUsuarioPage />} />
            <Route path="editar/:id" element={<CrearEditarUsuarioPage />} />
          </Route>
        </Route>

        
        <Route path="/tiendas">
          <Route element={<MainLayout />}>
            <Route path="crear" element={<CrearEditarTiendaPage />} />
            <Route path="editar/:id" element={<CrearEditarTiendaPage />} />
          </Route>
        </Route>
      </Route>

      
      <Route element={<ProtectedRoute allowedRoles={[Roles.ADMIN, Roles.CAPITAN, Roles.COORDINADOR, Roles.RESP_TIENDA]} />}>
        <Route path="/tiendas">
          <Route element={<SideBarMainLayout />}>
            <Route index element={<TiendasPage />} />
          </Route>
        </Route>
      </Route>

      
      <Route element={<ProtectedRoute allowedRoles={[Roles.ADMIN, Roles.CAPITAN, Roles.COORDINADOR, Roles.RESP_ENTIDAD]} />}>
        <Route path="/colaboradores">
          <Route element={<SideBarMainLayout />}>
            <Route index element={<ColaboradoresPage />} />
          </Route>
        </Route>
      </Route>

      
      <Route element={<ProtectedRoute allowedRoles={[Roles.ADMIN, Roles.COORDINADOR]} />}>
        <Route path="/colaboradores">
          <Route element={<MainLayout />}>
            <Route path="crear" element={<CrearEditarColaboradorPage />} />
            <Route path="editar/:id" element={<CrearEditarColaboradorPage />} />
          </Route>
        </Route>
      </Route>

      
      <Route element={<ProtectedRoute allowedRoles={[Roles.ADMIN, Roles.COORDINADOR]} />}>
        <Route path="/turnos">
          <Route element={<MainLayout />}>
            <Route path="crear" element={<CrearTurnoPage />} />
          </Route>
        </Route>
      </Route>

      
      <Route element={<ProtectedRoute allowedRoles={[Roles.ADMIN, Roles.CAPITAN, Roles.COORDINADOR, Roles.RESP_ENTIDAD]} />}>
        <Route path="/turnos">
          <Route element={<MainLayout />}>
            <Route path="incidencia" element={<IncidenciasPage />} />
          </Route>
        </Route>
      </Route>
    </Routes>
  )
}

export default App
