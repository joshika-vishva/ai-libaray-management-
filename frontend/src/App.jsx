import React from 'react'
import { Link, Route, Routes } from 'react-router-dom'
import AdminDashboard from './pages/AdminDashboard'
import UserDashboard from './pages/UserDashboard'
import BookSearchPage from './pages/BookSearchPage'
import IssueReturnPanel from './pages/IssueReturnPanel'
import AnalyticsDashboard from './pages/AnalyticsDashboard'
import ChatbotWidget from './components/ChatbotWidget'

export default function App() {
  return (
    <div style={{ fontFamily: 'Arial', margin: 20 }}>
      <h1>AI Library Management</h1>
      <nav style={{ display: 'flex', gap: 12 }}>
        <Link to="/admin">Admin</Link>
        <Link to="/user">User</Link>
        <Link to="/search">Book Search</Link>
        <Link to="/issue-return">Issue/Return</Link>
        <Link to="/analytics">Analytics</Link>
      </nav>
      <Routes>
        <Route path="/admin" element={<AdminDashboard />} />
        <Route path="/user" element={<UserDashboard />} />
        <Route path="/search" element={<BookSearchPage />} />
        <Route path="/issue-return" element={<IssueReturnPanel />} />
        <Route path="/analytics" element={<AnalyticsDashboard />} />
      </Routes>
      <ChatbotWidget />
    </div>
  )
}
