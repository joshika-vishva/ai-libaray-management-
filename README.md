# AI-Powered Library Management System

Production-ready full-stack project featuring secure library operations and modular AI capabilities.

## Tech Stack
- **Backend:** Java 17, Spring Boot, Spring Security (JWT), Spring Data JPA, PostgreSQL, Swagger/OpenAPI
- **Frontend:** React + Vite (built static), React Router, Axios, Tailwind CSS
- **AI Service:** FastAPI + scikit-learn (recommendation, chatbot, risk prediction, semantic search)
- **Ops:** Docker, Docker Compose, Nginx (frontend runtime)

## Modules Implemented
1. **Book Management**: CRUD, pagination, sorting, stock visibility, keyword/category search
2. **User Management**: register, login, BCrypt password hashing, JWT auth, ADMIN/USER RBAC
3. **Issue/Return**: due date automation, fine logic, overdue tracking, transaction history
4. **Fine & Transactions**: event logs and overdue APIs
5. **AI Modules**:
   - Smart recommendations (`/recommend/{userId}`)
   - Chatbot intent handling (`/chat`)
   - Late return risk score (`/predict-late-return/{userId}`)
   - Semantic search (`/semantic-search`)
6. **Cross-Cutting**: global exception handling, DTO pattern, logging, Swagger

## Project Structure
- `backend/` Spring Boot API
- `frontend/` React UI + Nginx config
- `ai-service/` FastAPI ML microservice
- `docs/` SQL schema + architecture/ER/flowchart diagrams

## Quick Deploy (Docker)
```bash
docker compose up --build -d
```

### Service URLs
- Frontend: `http://localhost:5173`
- Backend API: `http://localhost:8080`
- Swagger: `http://localhost:8080/swagger-ui.html`
- AI service docs: `http://localhost:8000/docs`

### Default Admin Credentials (seeded on first startup)
- Email: `admin@library.com`
- Password: `Admin@123`

You can override seed and runtime config using environment variables in `docker-compose.yml`.

## Manual Run (optional)
### Backend
```bash
cd backend
mvn spring-boot:run
```

### AI service
```bash
cd ai-service
pip install -r requirements.txt
uvicorn app.main:app --reload
```

### Frontend
```bash
cd frontend
npm install
npm run dev
```

## Production Notes
- Frontend container is multi-stage (build + nginx static runtime).
- Nginx proxies `/api` to backend, so frontend uses relative API base URL by default.
- Backend includes configurable CORS and env-driven datasource/JWT/AI URLs.

## Deliverables Included
- ✅ Source code (backend + frontend + ai-service)
- ✅ Database schema (`docs/sql/schema.sql`)
- ✅ System architecture diagram (`docs/diagrams/architecture.md`)
- ✅ ER diagram (`docs/diagrams/er-diagram.md`)
- ✅ Flowcharts (`docs/diagrams/flowcharts.md`)
- ✅ API docs via Swagger
- ✅ README with setup instructions
