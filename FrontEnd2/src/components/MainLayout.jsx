import { useEffect } from 'react'
import { Outlet, useNavigate } from 'react-router-dom'
import Sidebar from './Sidebar'

export default function MainLayout() {
  const navigate = useNavigate()

  useEffect(() => {
    const user = sessionStorage.getItem('user')
    if (!user) {
      navigate('/')
    }
  }, [navigate])

  return (
    <>
      <Sidebar />
      <main className="main-content">
        <Outlet />
      </main>
    </>
  )
}
