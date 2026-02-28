import { useState } from 'react'
import api from '../services/api'

export default function BookSearchPage() {
  const [query, setQuery] = useState('')
  const [books, setBooks] = useState([])
  const [semantic, setSemantic] = useState([])

  const search = async () => {
    const { data } = await api.get('/books/search', { params: { query } })
    setBooks(data.content || [])
    const sem = await api.get('/ai/semantic-search', { params: { query } })
    setSemantic(sem.data || [])
  }

  return (
    <div className="space-y-4">
      <div className="bg-white rounded shadow p-4">
        <input className="border p-2 w-full" value={query} onChange={(e) => setQuery(e.target.value)} placeholder="Search books" />
        <button onClick={search} className="mt-2 bg-blue-600 text-white px-3 py-2 rounded">Search</button>
      </div>
      <div className="grid md:grid-cols-2 gap-4">
        <div className="bg-white rounded shadow p-4">
          <h4 className="font-semibold">Keyword Search</h4>
          {books.map((b) => <p key={b.id}>{b.title} - {b.author}</p>)}
        </div>
        <div className="bg-white rounded shadow p-4">
          <h4 className="font-semibold">Semantic Results</h4>
          {semantic.map((s, i) => <p key={i}>{s}</p>)}
        </div>
      </div>
    </div>
  )
}
