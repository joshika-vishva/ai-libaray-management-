```mermaid
flowchart LR
  UI[React Frontend] --> API[Spring Boot REST API]
  API --> SEC[Security Layer\nJWT + RBAC]
  API --> SRV[Service Layer]
  SRV --> REPO[Repository Layer\nSpring Data JPA]
  REPO --> DB[(PostgreSQL)]
  SRV --> AI[AI Integration Layer]
  AI --> AISVC[FastAPI ML Services]
```
