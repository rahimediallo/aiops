#!/bin/bash

# Quick Start Script for AI Ops Monitor
# This script builds and runs the application with Docker Compose

set -e

echo "🚀 AI Ops Monitor - Quick Start"
echo ""

# Check if Docker is installed
if ! command -v docker &> /dev/null; then
    echo "❌ Docker is not installed. Please install Docker first."
    exit 1
fi

# Check if docker-compose is installed
if ! command -v docker-compose &> /dev/null; then
    echo "❌ Docker Compose is not installed. Please install Docker Compose first."
    exit 1
fi

# Check if OPENAI_API_KEY is set
if [ -z "$OPENAI_API_KEY" ]; then
    echo "⚠️  OPENAI_API_KEY environment variable is not set."
    echo "You can still run the app, but AI features will use fallback mode."
    echo ""
    read -p "Enter your OpenAI API key (or press Enter to skip): " api_key
    if [ -n "$api_key" ]; then
        export OPENAI_API_KEY="$api_key"
    fi
fi

echo "📦 Starting services with Docker Compose..."
docker-compose up -d

echo ""
echo "✅ Services started!"
echo ""
echo "📊 Access the application:"
echo "   - API:     http://localhost:8080"
echo "   - Swagger: http://localhost:8080/swagger-ui/index.html"
echo ""
echo "📝 Useful commands:"
echo "   - View logs:     docker-compose logs -f ai-ops-monitor"
echo "   - Stop services: docker-compose down"
echo "   - Run tests:     ./mvnw test"
echo ""
echo "🎯 Try this curl to create an incident:"
echo ""
echo 'curl -X POST http://localhost:8080/api/incidents \'
echo '  -H "Content-Type: application/json" \'
echo '  -d '\''{
echo '    "namespace": "prod",
echo '    "podName": "payment-service",
echo '    "severity": "HIGH",
echo '    "status": "OPEN",
echo '    "message": "CrashLoopBackOff detected after deployment"
echo '  }'\''
echo ""
