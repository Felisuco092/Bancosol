import { Route, Routes } from 'react-router-dom'
import MainLayout from './components/MainLayout'
import LoginLayout from './components/LoginLayout'
import LoginPage from './pages/LoginPage'
import DashboardPage from './pages/DashboardPage'
import CampanasPage from './pages/CampanasPage'
import CadenasPage from './pages/CadenasPage'
import TiendasPage from './pages/TiendasPage'
import CrearTiendaPage from './pages/crear_editar_pages/CrearTiendaPage'
import ColaboradoresPage from './pages/ColaboradoresPage'
import UsuariosPage from './pages/UsuariosPage'
import TurnosPage from './pages/TurnosPage'
import BandejaPage from './pages/BandejaPage'

function App() {
  return (
    <Routes>
      <Route element={<LoginLayout />}>
        <Route path="/" element={<LoginPage />} />
      </Route>
      <Route element={<MainLayout />}>
        <Route path="/dashboard" element={<DashboardPage />} />
      </Route>

      <Route path="/tiendas">
        <Route element={<MainLayout />}>
          <Route index element={<TiendasPage />} />
        </Route>
        <Route element={<LoginLayout />}>
          <Route path="crear" element={<CrearTiendaPage />} />
          <Route path="editar/:id" element={<CrearTiendaPage />} />
        </Route>
      </Route>

      <Route path="/campanas">
        <Route element={<MainLayout />}>
          <Route index element={<CampanasPage />} />
        </Route>
        <Route element={<LoginLayout />}>
          <Route path="crear" element={<CrearTiendaPage />} />
          <Route path="editar/:id" element={<CrearTiendaPage />} />
        </Route>
      </Route>

      <Route path="/colaboradores">
        <Route element={<MainLayout />}>
          <Route index element={<ColaboradoresPage />} />
        </Route>
        <Route element={<LoginLayout />}>
          <Route path="crear" element={<CrearTiendaPage />} />
          <Route path="editar/:id" element={<CrearTiendaPage />} />
        </Route>
      </Route>

      <Route path="/cadenas">
        <Route element={<MainLayout />}>
          <Route index element={<CadenasPage />} />
        </Route>
        <Route element={<LoginLayout />}>
          <Route path="crear" element={<CrearTiendaPage />} />
          <Route path="editar/:id" element={<CrearTiendaPage />} />
        </Route>
      </Route>

      <Route path="/usuarios">
        <Route element={<MainLayout />}>
          <Route index element={<UsuariosPage />} />
        </Route>
        <Route element={<LoginLayout />}>
          <Route path="crear" element={<CrearTiendaPage />} />
          <Route path="editar/:id" element={<CrearTiendaPage />} />
        </Route>
      </Route>

      <Route path="/turnos">
        <Route element={<MainLayout />}>
          <Route index element={<TurnosPage />} />
        </Route>
        <Route element={<LoginLayout />}>
          <Route path="crear" element={<CrearTiendaPage />} />
          <Route path="editar/:id" element={<CrearTiendaPage />} />
        </Route>
      </Route>

      <Route path="/bandeja">
        <Route element={<MainLayout />}>
          <Route index element={<BandejaPage />} />
        </Route>
        <Route element={<LoginLayout />}>
          <Route path=":id" element={<CrearTiendaPage />} />
        </Route>
      </Route>
    </Routes>
  )
}

export default App
