# AI Ops Monitor

AI Ops Monitor is an intelligent Kubernetes incident analysis platform built with Spring Boot, Spring AI, and OpenAPI.

The application simulates Kubernetes incidents, stores them, and generates SRE-oriented troubleshooting recommendations using AI.

---

# Features

* Kubernetes incident simulation
* Incident management API
* AI-powered SRE assistant
* CrashLoopBackOff analysis
* Swagger/OpenAPI documentation
* H2 in-memory database
* Spring AI integration
* Intelligent fallback when AI provider is unavailable

---

# Tech Stack

## Backend

* Java 21
* Spring Boot 3
* Spring Web
* Spring Validation
* Spring Data JPA
* Hibernate

## AI

* Spring AI
* OpenAI API

## Database

* H2 Database
* PostgreSQL ready

## Documentation

* Swagger/OpenAPI
* SpringDoc

---

# Architecture

The project follows a layered architecture:

```text
Controller Layer
    ↓
Application Service Layer
    ↓
Domain Layer
    ↓
Persistence Layer
```

## Main Components

### Controllers

* IncidentController
* SimulationController
* AiChatController

### Services

* IncidentService
* AiIncidentAnalysisService
* AiSreChatService

### Domain

* Incident
* Severity
* Status

---

# API Endpoints

## Incident APIs

### Create Incident

```http
POST /api/incidents
```

Example:

```json
{
  "namespace": "prod",
  "podName": "payment-service",
  "severity": "HIGH",
  "status": "OPEN",
  "message": "CrashLoopBackOff detected after deployment"
}
```

---

### Get All Incidents

```http
GET /api/incidents
```

---

### Analyze Incident With AI

```http
POST /api/incidents/analyze
```

This endpoint generates:

* incident summary
* probable cause
* remediation recommendations
* kubectl troubleshooting commands

---

## Kubernetes Simulation APIs

### Simulate CrashLoopBackOff

```http
POST /api/simulations/crashloop
```

This endpoint generates a fake Kubernetes production incident for testing/demo purposes.

---

## AI SRE Assistant

### Ask Kubernetes Questions

```http
POST /api/chat
```

Example:

```json
{
  "question": "Why is payment-service in CrashLoopBackOff in namespace prod?"
}
```

---

# Swagger Documentation

Swagger UI:

```text
http://localhost:8080/swagger-ui/index.html
```

---

# Running The Project

## Prerequisites

* Java 21
* Maven 3.9+

---

## Clone Repository

```bash
git clone <YOUR_GITHUB_REPOSITORY_URL>
cd ai-ops-monitor
```

---

## Configure OpenAI API Key

Mac/Linux:

```bash
export OPENAI_API_KEY=your_openai_api_key
```

Windows:

```powershell
setx OPENAI_API_KEY "your_openai_api_key"
```

---

## Run Application

### Option 1: Local Maven

```bash
mvn clean spring-boot:run
```

### Option 2: Docker

Build and run with Docker:

```bash
# Build Docker image
docker build -t ai-ops-monitor:latest .

# Run container
docker run -p 8080:8080 \
  -e OPENAI_API_KEY=your_openai_api_key \
  ai-ops-monitor:latest
```

### Option 3: Docker Compose (recommended for development)

This includes PostgreSQL + the app:

```bash
# Set your OpenAI API key
export OPENAI_API_KEY=your_openai_api_key

# Start containers
docker-compose up -d

# View logs
docker-compose logs -f ai-ops-monitor

# Stop containers
docker-compose down
```

Access the app at: `http://localhost:8080`

---

# Running Tests

## Unit Tests

```bash
# Run all tests
mvn test

# Run specific test class
mvn -Dtest=com.rahim.aiops.application.service.IncidentServiceTest test

# Run with coverage
mvn test jacoco:report
```

Test files location: `src/test/java/com/rahim/aiops/`

---

# AI Fallback Strategy

If OpenAI quota is unavailable or the AI provider fails, the application automatically returns a fallback SRE troubleshooting response instead of crashing.

This demonstrates:

* resiliency
* fault tolerance
* production-oriented backend design

---

# Example AI Analysis

Input:

```json
{
  "namespace": "prod",
  "podName": "payment-service",
  "severity": "HIGH",
  "status": "OPEN",
  "message": "CrashLoopBackOff detected after deployment"
}
```

Generated response:

```text
Summary:
The payment-service pod is repeatedly crashing after deployment.

Probable cause:
Potential configuration issue, failing health checks, or invalid environment variables.

Recommended actions:
- kubectl logs payment-service -n prod
- kubectl describe pod payment-service -n prod
- verify secrets and config maps
```

---

# Project Goals

The objective of this project is to demonstrate how Generative AI can improve DevOps and SRE workflows by transforming raw Kubernetes incidents into actionable operational diagnostics.

---

# Future Improvements

* Angular dashboard
* Real Kubernetes API integration
* JWT authentication
* PostgreSQL persistence
* Grafana integration
* Slack/Teams alerting
* Vector database / RAG
* Streaming AI responses
* Multi-model AI support

---

# Author

Rahim Diallo

Full-stack developer specialized in:

* Spring Boot
* Angular
* Kubernetes
