import React, { useState } from 'react'
import api from '../services/api'

export default function BookSearchPage() {
  const [q, setQ] = useState('')
  const [books, setBooks] = useState([])

  const search = async () => {
    const res = await api.get('/books/search', { params: { q } })
    setBooks(res.data.content || [])
  }

  return (
    <section>
      <h2>Book Search</h2>
      <input value={q} onChange={(e) => setQ(e.target.value)} placeholder="Search by title, author, category" />
      <button onClick={search}>Search</button>
      <ul>{books.map((b) => <li key={b.id}>{b.title} - {b.availableCopies} in stock</li>)}</ul>
    </section>
  )
}
