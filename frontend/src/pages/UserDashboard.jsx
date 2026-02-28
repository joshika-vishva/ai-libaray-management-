import { useEffect, useState } from 'react'
import api from '../services/api'

export default function UserDashboard() {
  const [recommendations, setRecommendations] = useState([])

  useEffect(() => {
    api.get('/ai/recommend/1').then(({ data }) => setRecommendations(data.recommendations || []))
  }, [])

  return (
    <div className="bg-white rounded shadow p-4">
      <h3 className="font-semibold mb-3">Top 5 AI Recommendations</h3>
      <ol className="list-decimal list-inside text-sm">
        {recommendations.map((r, idx) => <li key={idx}>{r}</li>)}
      </ol>
    </div>
  )
}
