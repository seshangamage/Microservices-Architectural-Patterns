# API Gateway

This project is a Spring Cloud Gateway that routes requests to backend microservices.

## Routes
- `/inventory/**` → `http://localhost:8083/inventory/**`
- `/payments/**` → `http://localhost:8082/payments/**`

## How to Run
1. Make sure Java 17+ and Maven are installed.
2. Start your backend services on ports 8082 and 8083.
3. Build and run the gateway:
   ```bash
   mvn spring-boot:run
   ```
4. Access your services via:
   - http://localhost:8080/inventory
   - http://localhost:8080/payments
