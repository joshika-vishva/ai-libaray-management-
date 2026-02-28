# AI-Powered Library Management System

Production-ready full-stack starter for a modular Library Management System with AI features.

## Tech Stack
- Backend: Java 17, Spring Boot, Spring Security (JWT), Spring Data JPA, PostgreSQL, Maven
- Frontend: React + Vite + Axios + React Router
- AI Layer: modular recommendation, chatbot, predictive risk, semantic search services

## Features
- JWT authentication with RBAC (`ROLE_ADMIN`, `ROLE_USER`)
- Book CRUD (admin), search with pagination and sorting
- Issue/return workflow with due-date and fine calculations
- Overdue tracking and transaction history
- AI endpoints for recommendations, chatbot, semantic search, late-return prediction
- Swagger OpenAPI docs (`/swagger-ui/index.html`)

## Project Structure
- `backend/` Spring Boot app (controller/service/repository/security/ai layers)
- `frontend/` React app with dashboard modules
- `docs/` architecture diagram, ER diagram, and flowcharts

## Setup
1. Start DB and app containers:
   ```bash
   docker compose up --build
   ```
2. Backend: `http://localhost:8080`
3. Frontend: `http://localhost:5173`
4. Swagger: `http://localhost:8080/swagger-ui/index.html`

## Database Schema
See `backend/src/main/resources/schema.sql`.

## API Documentation
- Swagger/OpenAPI enabled via springdoc dependency.
- Key APIs:
  - `POST /api/auth/register`
  - `POST /api/auth/login`
  - `GET /api/books/search`
  - `POST /api/transactions/issue`
  - `POST /api/transactions/return`
  - `GET /api/ai/recommendations/{userId}`
  - `POST /api/ai/chatbot`
  - `GET /api/ai/risk-score`

## Notes for Production
- Replace JWT secret and DB credentials with secure env vars.
- Swap AI stub services with FastAPI/ML microservices or model-backed Java implementations.
- Add observability (Prometheus/Grafana), CI/CD, and integration testing.
