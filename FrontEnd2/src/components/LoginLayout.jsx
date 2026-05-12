import { Outlet } from 'react-router-dom'

export default function LoginLayout() {
  return (
    <div className="login-page">
      <Outlet />
    </div>
  )
}
