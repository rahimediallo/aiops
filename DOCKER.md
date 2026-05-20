# Docker Guide - AI Ops Monitor

This guide explains how to run AI Ops Monitor using Docker.

## Prerequisites

- Docker 20.10+
- Docker Compose 2.0+ (for docker-compose option)
- OpenAI API Key (optional, falls back to generic SRE guidance if not available)

## Quick Start (Recommended)

### 1. Set your OpenAI API Key

```bash
export OPENAI_API_KEY=sk-...
```

### 2. Run the full stack with Docker Compose

```bash
docker-compose up -d
```

This will start:
- PostgreSQL database on `localhost:5432`
- AI Ops Monitor API on `localhost:8080`

### 3. Verify it's running

```bash
# Check logs
docker-compose logs -f ai-ops-monitor

# Access Swagger UI
open http://localhost:8080/swagger-ui/index.html
```

### 4. Stop services

```bash
docker-compose down
```

---

## Manual Docker Usage

### Build Docker Image

```bash
docker build -t ai-ops-monitor:latest .
```

### Run Container Standalone

```bash
docker run -p 8080:8080 \
  -e OPENAI_API_KEY=sk-... \
  ai-ops-monitor:latest
```

Or without OpenAI key (uses fallback):

```bash
docker run -p 8080:8080 ai-ops-monitor:latest
```

---

## Docker Compose Configuration

The `docker-compose.yml` file defines:

### Services

**ai-ops-monitor**
- Application port: `8080`
- Environment variables loaded from `.env` file
- Depends on PostgreSQL to be healthy before starting
- Mounted on `aiops-network`

**postgres**
- Database port: `5432`
- Database: `aiops`
- User: `aiops`
- Password: `aiops`
- Health check enabled

### Environment Variables

Configure in `.env` or set in the command:

```bash
OPENAI_API_KEY=your_key_here
SPRING_DATASOURCE_URL=jdbc:postgresql://postgres:5432/aiops
SPRING_DATASOURCE_USERNAME=aiops
SPRING_DATASOURCE_PASSWORD=aiops
```

See `.env.example` for all available variables.

---

## Useful Commands

### View all running containers

```bash
docker-compose ps
```

### Stream logs from the app

```bash
docker-compose logs -f ai-ops-monitor
```

### Stream logs from database

```bash
docker-compose logs -f postgres
```

### Execute command in running container

```bash
docker-compose exec ai-ops-monitor curl http://localhost:8080/swagger-ui.html
```

### Rebuild image after code changes

```bash
docker-compose up -d --build
```

### Remove all containers and volumes

```bash
docker-compose down -v
```

---

## Quick Makefile Commands

```bash
make docker-build       # Build Docker image
make docker-run         # Run Docker container
make docker-compose-up  # Start with docker-compose
make docker-compose-down# Stop docker-compose
make help              # Show all available commands
```

---

## Example API Calls

### Create an Incident

```bash
curl -X POST http://localhost:8080/api/incidents \
  -H "Content-Type: application/json" \
  -d '{
    "namespace": "prod",
    "podName": "payment-service",
    "severity": "HIGH",
    "status": "OPEN",
    "message": "CrashLoopBackOff detected after deployment"
  }'
```

### Get All Incidents

```bash
curl http://localhost:8080/api/incidents
```

### Analyze Incident with AI

```bash
curl -X POST http://localhost:8080/api/incidents/analyze \
  -H "Content-Type: application/json" \
  -d '{
    "namespace": "prod",
    "podName": "payment-service",
    "severity": "HIGH",
    "status": "OPEN",
    "message": "CrashLoopBackOff detected after deployment"
  }'
```

### Chat with SRE Assistant

```bash
curl -X POST http://localhost:8080/api/chat \
  -H "Content-Type: application/json" \
  -d '{
    "question": "Why is payment-service in CrashLoopBackOff?"
  }'
```

---

## Troubleshooting

### Container fails to start

Check logs:
```bash
docker-compose logs ai-ops-monitor
```

Common issues:
- OPENAI_API_KEY not set or invalid → uses fallback mode (OK)
- PostgreSQL not ready → wait a few seconds and restart
- Port 8080 already in use → kill the process or use different port in docker-compose.yml

### Database connection issues

Verify PostgreSQL is running:
```bash
docker-compose ps postgres
```

### Build fails

Clean and rebuild:
```bash
docker-compose down -v
docker-compose up -d --build
```

---

## Performance Tips

1. **Use the runtime image** (`eclipse-temurin:21-jre-jammy`) instead of full JDK
2. **Multi-stage builds** reduce final image size
3. **Skip tests during build** in Docker (already done in Dockerfile)
4. **Use PostgreSQL** for production instead of H2 in-memory (configured in docker-compose)

---

## Security Considerations

1. **Never commit `.env`** with real secrets
2. **Use** `.env.example` as a template
3. **Mount volumes** for persistent data (configured in docker-compose.yml)
4. **Use environment variables** instead of hardcoded credentials
5. **Change default PostgreSQL password** in production

---

## Next Steps

- Review the Swagger UI: http://localhost:8080/swagger-ui/index.html
- Run tests: `./mvnw test`
- Deploy to Kubernetes using the Docker image
- Integrate with your CI/CD pipeline
