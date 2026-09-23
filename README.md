# Customer API

A Spring Boot REST API for managing customers with PostgreSQL, Spring Data JPA, Flyway, validation, and OpenAPI support.

## Overview

This project exposes a simple customer management API with CRUD-style operations and a PostgreSQL-backed persistence layer. It includes:

- Spring Boot 4.1.1
- Java 21
- PostgreSQL database integration
- Flyway schema migration support
- Spring Validation for request payloads
- Global exception handling
- Actuator health endpoint
- OpenAPI documentation via SpringDoc
- Unit tests for controller behavior

## Prerequisites

- Java 21+
- Maven Wrapper included in the repo
- PostgreSQL 15+ (17 recommended)

## Database setup

Create a PostgreSQL database named `customerdb` and ensure it is reachable at `localhost:5433`.

Default development configuration:

- Host: `localhost`
- Port: `5433`
- Database: `customerdb`
- Username: `postgres`
- Password: `postgres`

These defaults are defined in `src/main/resources/application.yaml` and `pom.xml`.

### Override with environment variables

```powershell
$env:SPRING_DATASOURCE_URL="jdbc:postgresql://localhost:5433/customerdb?serverTimezone=UTC"
$env:SPRING_DATASOURCE_USERNAME="postgres"
$env:SPRING_DATASOURCE_PASSWORD="postgres"
```

> For production, prefer managed secrets and environment-based configuration instead of hard-coded credentials.

## Run locally

From the project root, start the application:

```powershell
./mvnw spring-boot:run
```

On Windows PowerShell, you can also use:

```powershell
.\mvnw.cmd spring-boot:run
```

The app runs on:

- `http://localhost:8080`

## Health and API documentation

The project exposes:

- Actuator health: `http://localhost:8080/actuator/health`
- Actuator info: `http://localhost:8080/actuator/info`
- OpenAPI Swagger UI: `http://localhost:8080/swagger-ui/index.html`

The `info` endpoint includes application metadata configured in `application.yaml`, such as:

- app name: `Customer API`
- version: `1.0.0`
- description: `Enterprise Customer API`

Example:

```bash
curl http://localhost:8080/actuator/info
```

Example output:

```json
{
  "app": {
    "name": "Customer API",
    "version": "1.0.0",
    "description": "Enterprise Customer API"
  }
}
```

## API endpoints

Base path: `/api/v1/customers`

| Method | Endpoint | Description |
| --- | --- | --- |
| `POST` | `/api/v1/customers` | Create a customer |
| `GET` | `/api/v1/customers` | List all customers |
| `GET` | `/api/v1/customers/{id}` | Fetch a customer by ID |

### Create customer

```powershell
Invoke-RestMethod `
  -Method Post `
  -Uri "http://localhost:8080/api/v1/customers" `
  -ContentType "application/json" `
  -Body '{"name":"Ada Lovelace","email":"ada@example.com"}'
```

```bash
curl -X POST http://localhost:8080/api/v1/customers \
  -H "Content-Type: application/json" \
  -d '{"name":"Ada Lovelace","email":"ada@example.com"}'
```

### List customers

```powershell
Invoke-RestMethod -Method Get -Uri "http://localhost:8080/api/v1/customers"
```

```bash
curl http://localhost:8080/api/v1/customers
```

### Get customer by ID

```powershell
Invoke-RestMethod -Method Get -Uri "http://localhost:8080/api/v1/customers/1"
```

```bash
curl http://localhost:8080/api/v1/customers/1
```

## Data model

The customer record includes:

- `id`: generated primary key
- `name`: required customer name
- `email`: required and unique

The schema is created by the Flyway migration in:

- `src/main/resources/db/migration/V1__create_customers_table.sql`

## Validation and errors

The API validates request bodies and returns structured error responses for:

- invalid input payloads
- missing or malformed customer data
- customer lookup failures (404 responses)

## Testing

Run the project tests:

```powershell
./mvnw test
```

or on Windows:

```powershell
.\mvnw.cmd test
```

## Project structure

```text
src/
  main/
    java/com/example/customer/
      controller/
      domain/
      dto/
      repository/
      service/
      GlobalExceptionHandler.java
      CustomerApiApplication.java
    resources/
      application.yaml
      db/migration/V1__create_customers_table.sql
  test/
    java/com/example/customer/
```

## Configuration

Application settings are in:

- `src/main/resources/application.yaml`

Key configuration includes:

- datasource connection values
- JPA Hibernate settings
- app metadata and info endpoints
- actuator exposure for health and info
