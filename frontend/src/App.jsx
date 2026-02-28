import { BrowserRouter, Navigate, Route, Routes } from 'react-router-dom'
import { AuthProvider, useAuth } from './context/AuthContext'
import Layout from './components/Layout'
import ChatbotWidget from './components/ChatbotWidget'
import LoginPage from './pages/LoginPage'
import AdminDashboard from './pages/AdminDashboard'
import UserDashboard from './pages/UserDashboard'
import BookSearchPage from './pages/BookSearchPage'
import IssueReturnPage from './pages/IssueReturnPage'
import AnalyticsPage from './pages/AnalyticsPage'

function Protected({ children }) {
  const { token } = useAuth()
  if (!token) return <Navigate to="/login" replace />
  return <Layout>{children}<div className="mt-6"><ChatbotWidget /></div></Layout>
}

export default function App() {
  return (
    <AuthProvider>
      <BrowserRouter>
        <Routes>
          <Route path="/login" element={<LoginPage />} />
          <Route path="/admin" element={<Protected><AdminDashboard /></Protected>} />
          <Route path="/user" element={<Protected><UserDashboard /></Protected>} />
          <Route path="/search" element={<Protected><BookSearchPage /></Protected>} />
          <Route path="/issue-return" element={<Protected><IssueReturnPage /></Protected>} />
          <Route path="/analytics" element={<Protected><AnalyticsPage /></Protected>} />
          <Route path="/" element={<Navigate to="/login" replace />} />
        </Routes>
      </BrowserRouter>
    </AuthProvider>
  )
}
