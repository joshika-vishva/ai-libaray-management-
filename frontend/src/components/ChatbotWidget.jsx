import React, { useState } from 'react'
import api from '../services/api'

export default function ChatbotWidget() {
  const [query, setQuery] = useState('')
  const [response, setResponse] = useState('')

  const ask = async () => {
    const res = await api.post('/ai/chatbot', null, { params: { userId: 1, query } })
    setResponse(res.data.response)
  }

  return (
    <section style={{ marginTop: 24, borderTop: '1px solid #ddd' }}>
      <h3>AI Chatbot Assistant</h3>
      <input value={query} onChange={(e) => setQuery(e.target.value)} placeholder="Ask about due date/fines" />
      <button onClick={ask}>Ask</button>
      {response && <p>{response}</p>}
    </section>
  )
}
