# Rajkishor Spring Boot Platform 🚀

A **production-ready backend template** built with **Java 21 & Spring Boot 3**, designed to help developers launch SaaS products, startups, and enterprise applications **faster and safer**.

---

## ✨ Why This Template?

Most developers waste weeks setting up:
- Authentication & security
- Project structure
- Database migrations
- Docker & environment configs

This template solves that.

> **Start building features on Day 1 — not infrastructure.**

---

## 🧱 Tech Stack

- **Java 21 (LTS)**
- **Spring Boot 3**
- Spring Security 6 (JWT + Refresh Tokens)
- PostgreSQL
- Flyway (DB migrations)
- Docker & Docker Compose
- Swagger / OpenAPI
- Maven

---

## 🔐 Features

### Authentication & Security
- JWT authentication
- Refresh tokens (DB-backed)
- Secure logout
- BCrypt password hashing
- Role-Based Access Control (RBAC)

### Architecture
- Clean, feature-based package structure
- Global exception handling
- Standard API response format
- Base entity with auditing
- API versioning (`/api/v1`)

### Database
- PostgreSQL (default)
- Flyway-managed schema
- Production-safe `ddl-auto: validate`

### DevOps
- Dockerfile (multi-stage)
- Docker Compose (App + DB)
- Environment-based configuration
- Ready for CI/CD

---

## 📂 Project Structure

```
com.rajkishor.platform
├── auth
├── user
├── common
├── config
└── PlatformApplication
```

---

## 🚀 Getting Started

### 1️⃣ Prerequisites
- Java 21
- Docker & Docker Compose

---

### 2️⃣ Run with Docker (Recommended)

```bash
docker-compose up --build
```

Backend will be available at:
```
http://localhost:8080
```

---

### 3️⃣ Health Check

```http
GET /api/v1/health
```

Response:
```json
{
  "success": true,
  "message": "Service is running",
  "data": "OK"
}
```

---

## 🔑 Authentication APIs

### Register
```http
POST /api/v1/auth/register
```

### Login
```http
POST /api/v1/auth/login
```

Returns:
- Access Token
- Refresh Token

### Refresh Token
```http
POST /api/v1/auth/refresh
```

### Logout
```http
POST /api/v1/auth/logout
```

---

## 📘 API Documentation

Swagger UI:
```
http://localhost:8080/swagger
```

---

## 🧩 Who Is This For?

- Backend developers
- Freelancers
- Startups launching MVPs
- Companies needing a clean backend foundation
- Students building serious projects

---

## 💎 Roadmap

- OAuth2 (Google/GitHub)
- Admin management APIs
- Multi-tenancy
- Microservices version
- Frontend starter (React)

---

## 📄 License

Commercial / Personal use allowed.  
Redistribution or reselling as-is is not permitted.

---

## 🙌 Author

**Rajkishor Murmu**  
Java Backend Developer
