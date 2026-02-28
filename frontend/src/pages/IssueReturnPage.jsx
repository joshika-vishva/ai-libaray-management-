import { useState } from 'react'
import api from '../services/api'

export default function IssueReturnPage() {
  const [userId, setUserId] = useState(1)
  const [bookId, setBookId] = useState(1)
  const [transactionId, setTransactionId] = useState(1)
  const [message, setMessage] = useState('')

  const issue = async () => {
    const { data } = await api.post('/borrow/issue', { userId: Number(userId), bookId: Number(bookId) })
    setMessage(`Issued successfully with due date: ${data.dueDate}`)
  }

  const returnBook = async () => {
    const { data } = await api.post('/borrow/return', { transactionId: Number(transactionId) })
    setMessage(`Returned. Fine: ${data.fineAmount}`)
  }

  return (
    <div className="bg-white rounded shadow p-4 space-y-2">
      <h3 className="font-semibold">Issue / Return</h3>
      <input className="border p-2 w-full" value={userId} onChange={(e) => setUserId(e.target.value)} placeholder="User ID" />
      <input className="border p-2 w-full" value={bookId} onChange={(e) => setBookId(e.target.value)} placeholder="Book ID" />
      <button className="bg-green-600 text-white px-3 py-2 rounded mr-2" onClick={issue}>Issue</button>
      <input className="border p-2 w-full" value={transactionId} onChange={(e) => setTransactionId(e.target.value)} placeholder="Transaction ID" />
      <button className="bg-purple-600 text-white px-3 py-2 rounded" onClick={returnBook}>Return</button>
      {message && <p>{message}</p>}
    </div>
  )
}
