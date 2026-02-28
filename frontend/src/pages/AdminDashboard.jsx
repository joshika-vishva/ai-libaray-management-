import { useEffect, useState } from 'react'
import api from '../services/api'

export default function AdminDashboard() {
  const [books, setBooks] = useState([])
  const [risk, setRisk] = useState(null)

  useEffect(() => {
    api.get('/books').then(({ data }) => setBooks(data.content || []))
    api.get('/ai/risk/1').then(({ data }) => setRisk(data.risk_score))
  }, [])

  return (
    <div className="grid md:grid-cols-2 gap-4">
      <div className="bg-white rounded shadow p-4">
        <h3 className="font-semibold mb-2">Inventory Snapshot</h3>
        <ul className="text-sm">
          {books.map((b) => <li key={b.id}>{b.title} - {b.availableCopies}/{b.totalCopies}</li>)}
        </ul>
      </div>
      <div className="bg-white rounded shadow p-4">
        <h3 className="font-semibold mb-2">Late Return Risk</h3>
        <p className="text-2xl text-orange-600">{risk ?? 'N/A'}</p>
      </div>
    </div>
  )
}
