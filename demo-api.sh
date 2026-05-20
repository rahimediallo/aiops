#!/bin/bash

# Demo API Script for AI Ops Monitor
# This script demonstrates all the main API endpoints

set -e

API_URL="http://localhost:8080"
NAMESPACE="demo"
POD_NAME="app-service-1"

echo "🎯 AI Ops Monitor - API Demo"
echo "=================================="
echo ""
echo "Note: Make sure the application is running on $API_URL"
echo ""

# Color codes for output
GREEN='\033[0;32m'
BLUE='\033[0;34m'
YELLOW='\033[1;33m'
NC='\033[0m' # No Color

# Function to print section headers
print_header() {
    echo ""
    echo -e "${BLUE}=== $1 ===${NC}"
    echo ""
}

# Function to print curl command before execution
print_curl() {
    echo -e "${YELLOW}$ $1${NC}"
}

# 1. Create an Incident
print_header "1. Create an Incident"
print_curl "curl -X POST $API_URL/api/incidents ..."
curl -X POST "$API_URL/api/incidents" \
  -H "Content-Type: application/json" \
  -d '{
    "namespace": "'$NAMESPACE'",
    "podName": "'$POD_NAME'",
    "severity": "HIGH",
    "status": "OPEN",
    "message": "CrashLoopBackOff detected after deployment"
  }' | jq .
echo ""

# 2. Get All Incidents with Pagination
print_header "2. Get All Incidents"
print_curl "curl $API_URL/api/incidents?page=0&size=10"
curl -s "$API_URL/api/incidents?page=0&size=10" | jq .
echo ""

# 3. Simulate a Kubernetes Event (CrashLoopBackOff)
print_header "3. Simulate Kubernetes Event"
print_curl "curl -X POST $API_URL/api/simulations/crashloop ..."
curl -X POST "$API_URL/api/simulations/crashloop" \
  -H "Content-Type: application/json" \
  -d '{
    "namespace": "'$NAMESPACE'",
    "podName": "payment-service",
    "reason": "CrashLoopBackOff",
    "message": "Container exited with code 137",
    "status": "Failed"
  }' | jq .
echo ""

# 4. Ask SRE Assistant
print_header "4. Ask SRE Assistant (AI Chat)"
print_curl "curl -X POST $API_URL/api/chat ..."
curl -X POST "$API_URL/api/chat" \
  -H "Content-Type: application/json" \
  -d '{
    "question": "Our payment-service pod in prod is stuck in CrashLoopBackOff. What should we check first?"
  }' | jq .
echo ""

# 5. Analyze an Incident with AI
print_header "5. Analyze Incident with AI"
print_curl "curl -X POST $API_URL/api/incidents/analyze ..."
curl -X POST "$API_URL/api/incidents/analyze" \
  -H "Content-Type: application/json" \
  -d '{
    "namespace": "prod",
    "podName": "payment-service",
    "severity": "CRITICAL",
    "status": "OPEN",
    "message": "Pod entered CrashLoopBackOff state after deployment rollout"
  }' | jq .
echo ""

# 6. API Documentation
print_header "6. API Documentation"
echo -e "${GREEN}✅ API documentation available at:${NC}"
echo "   $API_URL/swagger-ui/index.html"
echo ""

# 7. Summary
print_header "Demo Complete!"
echo -e "${GREEN}✅ All API endpoints demonstrated!${NC}"
echo ""
echo "Next steps:"
echo "  1. Open Swagger UI: $API_URL/swagger-ui/index.html"
echo "  2. Try other endpoints interactively"
echo "  3. Check the application logs: docker-compose logs -f"
echo ""
