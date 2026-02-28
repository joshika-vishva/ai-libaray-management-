from fastapi import FastAPI
from pydantic import BaseModel
from sklearn.feature_extraction.text import TfidfVectorizer
from sklearn.metrics.pairwise import cosine_similarity
import numpy as np

app = FastAPI(title="AI Library Service")

BOOK_CORPUS = [
    "Clean Code by Robert Martin software craftsmanship",
    "Design Patterns GoF software architecture",
    "Deep Learning with Python neural networks",
    "Sapiens world history evolution",
    "Atomic Habits personal development"
]

vectorizer = TfidfVectorizer()
corpus_vectors = vectorizer.fit_transform(BOOK_CORPUS)

class ChatRequest(BaseModel):
    user_id: int
    query: str

class SearchRequest(BaseModel):
    query: str

@app.get("/recommend/{user_id}")
def recommend(user_id: int):
    # Placeholder collaborative/content hybrid strategy
    return {"recommendations": BOOK_CORPUS[:5]}

@app.post("/chat")
def chat(request: ChatRequest):
    q = request.query.lower()
    if "available" in q or "stock" in q:
        return {"intent": "availability", "answer": "You can check live stock in the search dashboard."}
    if "due" in q:
        return {"intent": "due_date", "answer": "Typical due date is 14 days from issue."}
    if "fine" in q:
        return {"intent": "fine", "answer": "Fine is calculated at 2 units/day after due date."}
    return {"intent": "general", "answer": "Please ask about availability, due date, or fine."}

@app.get("/predict-late-return/{user_id}")
def predict_late_return(user_id: int):
    # Synthetic risk score placeholder
    risk_score = float(np.clip((user_id % 10) / 10 + 0.15, 0, 1))
    return {"risk_score": risk_score}

@app.post("/semantic-search")
def semantic_search(request: SearchRequest):
    query_vec = vectorizer.transform([request.query])
    scores = cosine_similarity(query_vec, corpus_vectors).flatten()
    top_indices = np.argsort(scores)[::-1][:5]
    return [BOOK_CORPUS[i] for i in top_indices.tolist()]
