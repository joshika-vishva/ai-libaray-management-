import { Link } from 'react-router-dom'
import { useAuth } from '../context/AuthContext'

export default function Layout({ children }) {
  const { logout } = useAuth()

  return (
    <div>
      <nav className="bg-slate-900 text-white px-6 py-4 flex gap-4 items-center">
        <Link to="/" className="font-semibold">AI Library</Link>
        <Link to="/admin">Admin</Link>
        <Link to="/user">User</Link>
        <Link to="/search">Search</Link>
        <button onClick={logout} className="ml-auto bg-red-500 px-3 py-1 rounded">Logout</button>
      </nav>
      <main className="p-6">{children}</main>
    </div>
  )
}
