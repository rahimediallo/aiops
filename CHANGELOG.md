# Changelog

All notable changes to AI Ops Monitor will be documented in this file.

## [0.0.1] - 2026-05-20

### Added

#### Docker Support
- Multi-stage Dockerfile with optimized builds
- Docker Compose configuration (app + PostgreSQL)
- .dockerignore file for build optimization
- DOCKER.md comprehensive guide

#### Unit Tests
- IncidentServiceTest (getAllIncidents, createIncident)
- AiSreChatServiceTest (AI response, fallback handling)
- KubernetesEventServiceTest (event conversion)
- Using JUnit 5 + Mockito framework

#### Documentation & Configuration
- Updated README.md with Docker options
- DOCKER_SETUP_SUMMARY.md for setup documentation
- CLIENT_DEMO_CHECKLIST.md for presentation guidance
- .env.example for environment configuration template
- Makefile for convenient commands
- quick-start.sh for automated setup
- demo-api.sh for API endpoint demonstrations

#### Development Tools
- .gitignore updated for .env, Docker files
- Added Makefile with build, test, docker targets

### Improved
- README.md: Added Docker deployment options
- README.md: Added Testing section
- .gitignore: Added environment variables, Docker, OS files

### Features (Pre-existing)
- Spring Boot 3 REST API
- Spring AI with OpenAI integration
- Kubernetes incident simulation
- Incident analysis and SRE assistant
- H2 and PostgreSQL database support
- OpenAPI/Swagger documentation
- Layered architecture (Domain/Application/Infrastructure/Presentation)
- Exception handling with GlobalExceptionHandler

### Tech Stack
- Java 21
- Spring Boot 3.5.7
- Spring Web, Data JPA, Validation
- Spring AI (OpenAI)
- PostgreSQL / H2 Database
- Maven build system
- Docker & Docker Compose
- JUnit 5, Mockito for testing

### Next Steps
- [ ] GitHub Actions CI/CD workflow
- [ ] JaCoCo test coverage reports
- [ ] Spring Boot Actuator for health/metrics
- [ ] Kubernetes manifests (Deployment, Service, ConfigMap)
- [ ] Angular frontend
- [ ] JWT authentication
- [ ] Rate limiting
- [ ] Vector database / RAG
- [ ] Multi-model AI support

---

## Project Structure

```
ai-ops-monitor/
├── src/
│   ├── main/java/com/rahim/aiops/
│   │   ├── application/      # Services, DTOs
│   │   ├── domain/           # Models, enums
│   │   ├── infrastructure/   # Persistence, adapters
│   │   └── presentation/     # Controllers, exceptions
│   └── test/java/com/rahim/aiops/
│       └── application/service/  # Unit tests
├── Dockerfile
├── docker-compose.yml
├── pom.xml
└── README.md
```

---

## How to Build & Run

### Local Development
```bash
mvn clean spring-boot:run
```

### Docker
```bash
docker build -t ai-ops-monitor:latest .
docker run -p 8080:8080 -e OPENAI_API_KEY=sk-... ai-ops-monitor:latest
```

### Docker Compose (Recommended)
```bash
docker-compose up -d
```

### Tests
```bash
mvn test
```

---

## API Documentation

Access Swagger UI:
```
http://localhost:8080/swagger-ui/index.html
```

Main endpoints:
- `POST /api/incidents` - Create incident
- `GET /api/incidents` - List incidents
- `POST /api/incidents/analyze` - Analyze with AI
- `POST /api/chat` - SRE assistant
- `POST /api/simulations/crashloop` - Simulate K8s event

---

## Contributors

- Rahim Diallo (rahim@example.com)

---

## License

[Specify License Here]
