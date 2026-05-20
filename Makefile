.PHONY: help build test run docker-build docker-run docker-compose-up docker-compose-down clean

help:
	@echo "AI Ops Monitor - Available Commands"
	@echo ""
	@echo "Development:"
	@echo "  make build              Build the project locally"
	@echo "  make run                Run the application locally (Maven)"
	@echo "  make test               Run all unit tests"
	@echo ""
	@echo "Docker:"
	@echo "  make docker-build       Build Docker image"
	@echo "  make docker-run         Run Docker container (needs OPENAI_API_KEY set)"
	@echo "  make docker-compose-up  Start services with docker-compose"
	@echo "  make docker-compose-down Stop docker-compose services"
	@echo ""
	@echo "Utilities:"
	@echo "  make clean              Clean build artifacts"
	@echo "  make help               Show this help message"

build:
	./mvnw clean package -DskipTests

test:
	./mvnw test

run:
	./mvnw spring-boot:run

docker-build:
	docker build -t ai-ops-monitor:latest .

docker-run: docker-build
	docker run -p 8080:8080 \
		-e OPENAI_API_KEY=$(OPENAI_API_KEY) \
		ai-ops-monitor:latest

docker-compose-up:
	docker-compose up -d

docker-compose-down:
	docker-compose down

clean:
	./mvnw clean
	docker-compose down -v 2>/dev/null || true
