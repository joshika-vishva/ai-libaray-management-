import { useState } from 'react'
import api from '../services/api'

export default function ChatbotWidget() {
  const [query, setQuery] = useState('')
  const [answer, setAnswer] = useState('')

  const ask = async () => {
    const { data } = await api.post('/ai/chat', { userId: 1, query })
    setAnswer(`${data.intent}: ${data.answer}`)
  }

  return (
    <div className="bg-white rounded shadow p-4">
      <h3 className="font-semibold mb-2">AI Chatbot</h3>
      <input className="border p-2 w-full" value={query} onChange={(e) => setQuery(e.target.value)} placeholder="Ask about availability, due dates, fine" />
      <button onClick={ask} className="mt-2 bg-blue-600 text-white px-3 py-2 rounded">Ask</button>
      {answer && <p className="mt-3 text-sm">{answer}</p>}
    </div>
  )
}
