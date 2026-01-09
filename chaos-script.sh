#!/bin/bash

# Check if docker-compose file exists
if [ ! -f "docker-compose.yml" ]; then
    echo "❌ Error: docker-compose.yml not found in this directory!"
    exit 1
fi

echo "🛡️  Starting Resilience4j Chaos Test..."
echo "Press [CTRL+C] to stop the chaos."
echo "----------------------------------------------------"

while true; do
  echo "✅ [$(date +%T)] UP: Inventory Service is available (20s)"
  docker compose start inventory-service
  echo "👉 Monitor: http://localhost:8080/order-service/actuator/health"
  sleep 20

  echo "🛑 [$(date +%T)] DOWN: Killing Inventory Service (15s)"
  docker compose stop inventory-service
  echo "📉 The Circuit Breaker should detect failures now..."
  sleep 15
done