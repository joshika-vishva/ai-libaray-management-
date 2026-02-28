import { useState } from 'react'
import { useNavigate } from 'react-router-dom'
import api from '../services/api'
import { useAuth } from '../context/AuthContext'

export default function LoginPage() {
  const [email, setEmail] = useState('admin@library.com')
  const [password, setPassword] = useState('password')
  const { login } = useAuth()
  const navigate = useNavigate()

  const handleLogin = async () => {
    const { data } = await api.post('/auth/login', { email, password })
    login(data.token)
    navigate('/admin')
  }

  return (
    <div className="max-w-md mx-auto mt-24 bg-white p-6 rounded shadow">
      <h2 className="text-xl font-bold mb-4">Login</h2>
      <input className="border p-2 w-full mb-3" value={email} onChange={(e) => setEmail(e.target.value)} />
      <input className="border p-2 w-full mb-3" type="password" value={password} onChange={(e) => setPassword(e.target.value)} />
      <button className="bg-blue-600 text-white px-4 py-2 rounded" onClick={handleLogin}>Login</button>
    </div>
  )
}
