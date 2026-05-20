# Client Presentation Checklist

## Pre-Demo Preparation

### Technical Setup ✅
- [ ] Clone the repository
- [ ] Install Docker and Docker Compose
- [ ] Run `./quick-start.sh` or `docker-compose up -d`
- [ ] Verify app runs at `http://localhost:8080`
- [ ] Check Swagger UI at `http://localhost:8080/swagger-ui/index.html`
- [ ] Run tests locally: `./mvnw test`

### Environment Setup ✅
- [ ] Set `OPENAI_API_KEY` (optional, app works without it with fallback)
- [ ] Verify PostgreSQL is running and healthy
- [ ] Confirm all services started: `docker-compose ps`

---

## Demo Flow (Estimated: 15 minutes)

### 1. Architecture Overview (2 min)
**What to show:**
- Open `README.md` or `DOCKER.md`
- Show the project structure (layered architecture)
- Highlight: Domain → Application → Presentation layers
- Mention: clean separation of concerns, testability

**Key points to mention:**
- "Built with Spring Boot 3, Java 21"
- "Production-ready architecture with hexagonal pattern"
- "Unit tests already included"

---

### 2. Docker & Deployment (2 min)
**What to show:**
```bash
# Show Docker setup is simple
cat Dockerfile
# Run in Docker
docker build -t ai-ops-monitor:latest .
docker-compose up -d
```

**Key points:**
- "Multi-stage Docker builds for optimization"
- "One command to run full stack with Docker Compose"
- "PostgreSQL included for persistence"
- "Environment configuration via .env file"

---

### 3. Live API Demo (7 min)

**Option A: Interactive (via Swagger UI)**
- Navigate to `http://localhost:8080/swagger-ui/index.html`
- Show all available endpoints
- Try endpoints one by one:
  1. Create incident
  2. Get incidents
  3. Simulate Kubernetes event
  4. Ask SRE assistant
  5. Analyze incident with AI

**Option B: Scripted (via demo-api.sh)**
```bash
chmod +x demo-api.sh
./demo-api.sh
```

**Key endpoints to demonstrate:**
- `POST /api/incidents` - Create incident
- `GET /api/incidents` - List incidents with pagination
- `POST /api/simulations/crashloop` - Simulate K8s event
- `POST /api/chat` - Ask SRE assistant (AI fallback if key not set)
- `POST /api/incidents/analyze` - Analyze incident with AI

---

### 4. Code Quality (2 min)
**What to show:**
```bash
# Run tests
mvn test

# Show test files
ls src/test/java/com/rahim/aiops/application/service/
```

**Key points:**
- "Unit tests for all services"
- "Using JUnit 5 + Mockito"
- "CI-ready (can add GitHub Actions)"
- "Easy to extend with integration tests"

---

### 5. AI Capabilities (2 min)
**What to show:**
- Send a question via `/api/chat` endpoint
- Show AI response (or fallback if key not set)
- Explain the fallback strategy

**Key points:**
- "Leverages OpenAI's GPT models"
- "Intelligent SRE guidance even without AI (fallback)"
- "Fault-tolerant design"
- "Can be extended to other AI models"

---

## Post-Demo Q&A Topics

### Architecture
- Q: "Can we scale this?"
  A: "Yes, stateless design + PostgreSQL backend + containerized"
  
- Q: "What about authentication?"
  A: "Currently demo mode; easy to add Spring Security/JWT"

### Customization
- Q: "Can we integrate with our monitoring tools?"
  A: "Yes, we can add webhooks, integrate with Grafana, Datadog, etc."

- Q: "Can we train it on our infrastructure?"
  A: "Yes, via RAG (Retrieval-Augmented Generation) or fine-tuning"

### Deployment
- Q: "How do we deploy to production?"
  A: "Push Docker image to registry, deploy via K8s manifests, Helm charts, or cloud platforms"

- Q: "What about CI/CD?"
  A: "Can add GitHub Actions workflow for automated testing and deployment"

---

## Files to Reference During Demo

- **README.md** - Project overview and quick start
- **DOCKER.md** - Docker setup and usage
- **Swagger UI** - Live API documentation at `http://localhost:8080/swagger-ui/index.html`
- **Unit Tests** - Show test structure in `src/test/java/...`
- **Makefile** - Show available commands with `make help`

---

## Backup / Fallback Plans

### If Docker doesn't work
```bash
# Run locally with Maven
./mvnw spring-boot:run
# Set environment
export OPENAI_API_KEY=your_key
```

### If OpenAI API fails
- Show the fallback SRE guidance in the response
- Explain the resilience pattern

### If network issues
- Use pre-recorded screenshots/videos
- Have API responses saved in a text file

---

## Post-Demo Follow-ups

### Immediate Next Steps
1. [ ] Send repo link and quick-start instructions
2. [ ] Provide API documentation (Swagger export)
3. [ ] Share contact for questions

### For Production Deployment
- [ ] Database backup/recovery plan
- [ ] Monitoring & alerting setup
- [ ] Security audit (secrets management)
- [ ] Performance testing & load balancing
- [ ] Kubernetes manifests (optional)
- [ ] Angular frontend (optional)

### For Further Development
- [ ] Add authentication (Spring Security)
- [ ] Add more AI models (Anthropic Claude, local LLMs)
- [ ] Integrate with real Kubernetes cluster
- [ ] Add Prometheus metrics
- [ ] Add Slack/Teams integration
- [ ] Vector database for RAG

---

## Key Talking Points

✅ **"Production-Ready"**
- Clean architecture, tests, Docker support

✅ **"Easy to Deploy"**
- One command setup with Docker Compose

✅ **"Intelligent SRE Assistance"**
- AI-powered insights + smart fallbacks

✅ **"Scalable & Maintainable"**
- Layered architecture, separated concerns

✅ **"Cloud-Native"**
- Containerized, PostgreSQL-ready, OpenAI-integrated

✅ **"Extensible"**
- Easy to add features, integrate other services

---

## Demo Checklist (Morning of Demo)

- [ ] Internet connection working
- [ ] Docker daemon running
- [ ] All services started (`docker-compose ps`)
- [ ] App responsive at `http://localhost:8080`
- [ ] OPENAI_API_KEY set (if available)
- [ ] Test scripts are executable
- [ ] Have backup localhost setup ready
- [ ] Slides/presentation materials ready
- [ ] All demo URLs bookmarked

---

## Success Metrics

After the demo, aim for:
- Client understanding of architecture
- Confidence in code quality (tests)
- Clear vision of capabilities (AI + fallback)
- Enthusiasm for deployment & integration
- Discussion of roadmap & next features
