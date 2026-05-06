import { Link, Route, Routes } from 'react-router-dom'
import './App.css'

function App() {
  return (
    <main style={{ padding: '2rem', display: 'grid', gap: '1rem' }}>
      <nav style={{ display: 'flex', gap: '1rem' }}>
        <Link to="/">Inicio</Link>
        <Link to="/about">Acerca de</Link>
      </nav>
      <Routes>
        <Route path="/" element={<HomePage />} />
        <Route path="/about" element={<AboutPage />} />
      </Routes>
    </main>
  )
}

function HomePage() {
  return (
    <section>
      <h1>Inicio</h1>
      <p>React Router está configurado correctamente.</p>
    </section>
  )
}

function AboutPage() {
  return (
    <section>
      <h1>Acerca de</h1>
      <p>Esta es una ruta de ejemplo para ampliar tu navegación.</p>
    </section>
  )
}
export default App
