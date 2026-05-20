# Getting Started with AI Ops Monitor

**TL;DR** — Run the app in 5 minutes:

```bash
# Option 1: Fastest (requires Docker)
chmod +x quick-start.sh
./quick-start.sh

# Option 2: With Docker Compose
export OPENAI_API_KEY=sk-...  # optional
docker-compose up -d

# Option 3: Local Maven
export OPENAI_API_KEY=sk-...  # optional
./mvnw spring-boot:run
```

Then visit: `http://localhost:8080/swagger-ui/index.html`

---

## What is AI Ops Monitor?

An intelligent Kubernetes incident analysis platform that:
- Detects Kubernetes pod issues (e.g., CrashLoopBackOff)
- Generates SRE troubleshooting recommendations using OpenAI
- Works offline with smart fallback guidance
- Stores incidents in PostgreSQL

---

## Prerequisites

### Minimum
- Docker 20.10+ (OR)
- Java 21 + Maven 3.9+

### Optional
- OpenAI API key (for AI features; app works without it)

---

## Quick Start Guide

### 1️⃣ Clone & Navigate
```bash
git clone <REPO_URL>
cd ai-ops-monitor
```

### 2️⃣ Set OpenAI Key (Optional)
```bash
export OPENAI_API_KEY=sk-your-key-here
```
(If not set, the app uses fallback SRE guidance)

### 3️⃣ Run with Docker Compose (Recommended)
```bash
docker-compose up -d
```

### 4️⃣ Access the App
- **API & Documentation**: http://localhost:8080/swagger-ui/index.html
- **Database**: PostgreSQL on localhost:5432 (user: `aiops`, password: `aiops`)

### 5️⃣ Try an Example
```bash
curl -X POST http://localhost:8080/api/incidents \
  -H "Content-Type: application/json" \
  -d '{
    "namespace": "prod",
    "podName": "payment-service",
    "severity": "HIGH",
    "status": "OPEN",
    "message": "CrashLoopBackOff after deployment"
  }'
```

---

## Alternative: Run Locally (No Docker)

```bash
# Prerequisites: Java 21, Maven 3.9+
export OPENAI_API_KEY=sk-your-key-here

# Run
./mvnw spring-boot:run

# App starts on http://localhost:8080
```

---

## Common Commands

```bash
# See all available commands
make help

# Run tests
./mvnw test
# or
make test

# Stop Docker services
docker-compose down

# View logs
docker-compose logs -f ai-ops-monitor

# Demo API endpoints
chmod +x demo-api.sh
./demo-api.sh
```

---

## Main API Endpoints

| Endpoint | Method | Purpose |
|----------|--------|---------|
| `/api/incidents` | POST | Create incident |
| `/api/incidents` | GET | List incidents |
| `/api/incidents/analyze` | POST | Analyze with AI |
| `/api/chat` | POST | Ask SRE assistant |
| `/api/simulations/crashloop` | POST | Simulate K8s event |
| `/swagger-ui/index.html` | GET | API documentation |

---

## Troubleshooting

### App won't start?
```bash
# Check Docker is running
docker ps

# Check logs
docker-compose logs ai-ops-monitor

# Clean and rebuild
docker-compose down -v
docker-compose up -d --build
```

### Can't connect to database?
```bash
# Ensure PostgreSQL is healthy
docker-compose ps postgres

# Logs
docker-compose logs postgres
```

### No AI responses?
- OPENAI_API_KEY not set → uses fallback mode (still works!)
- Invalid key → check your key format
- API rate limit → wait and retry

---

## Documentation Files

- **README.md** - Full project overview
- **DOCKER.md** - Docker & deployment guide
- **CLIENT_DEMO_CHECKLIST.md** - Demo presentation guide
- **Makefile** - Quick commands
- **CHANGELOG.md** - Version history

---

## Need Help?

1. Check the **Swagger UI**: http://localhost:8080/swagger-ui/index.html
2. Run the demo script: `./demo-api.sh`
3. Read **DOCKER.md** for advanced options
4. Check logs: `docker-compose logs -f`

---

## Next Steps

- [ ] Explore the Swagger UI endpoints
- [ ] Try creating and analyzing incidents
- [ ] Review the architecture in README.md
- [ ] Check out the unit tests in `src/test/`
- [ ] Deploy to your environment

---

**Happy monitoring! 🚀**
