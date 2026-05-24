import { StrictMode } from 'react'
import { createRoot } from 'react-dom/client'
import { BrowserRouter } from 'react-router-dom'
import './index.css'
import './css/styles.css'
import './css/login.css'
import './css/formulario.css'
import App from './App.jsx'
import { ProveedorAuten } from './auth/ProveedorAuten.jsx'

createRoot(document.getElementById('root')).render(
  <StrictMode>
    <ProveedorAuten>
      <BrowserRouter>
        <App />
      </BrowserRouter>
    </ProveedorAuten>
  </StrictMode>,
)
