# Customer API

A Spring Boot REST API for managing customers with PostgreSQL and Spring Data JPA.

## Requirements

- Java 21
- PostgreSQL 17 or compatible
- Maven Wrapper (included)

## Database Setup

Create a PostgreSQL database named `customerdb` and make it available at `localhost:5433`.
The default development credentials are:

- Username: `postgres`
- Password: `postgres`

Override the connection settings with environment variables when needed:

```powershell
$env:SPRING_DATASOURCE_URL="jdbc:postgresql://localhost:5433/customerdb"
$env:SPRING_DATASOURCE_USERNAME="postgres"
$env:SPRING_DATASOURCE_PASSWORD="your-password"
```

For production, use secure secret management instead of storing credentials in configuration files.

## Run Locally

From the project root:

```powershell
.\mvnw.cmd spring-boot:run
```

The API starts on `http://localhost:8080`.

## Test

Run all unit and application-context tests:

```powershell
.\mvnw.cmd test
```

## API Endpoints

Base path: `/api/v1/customers`

| Method | Endpoint | Description |
| --- | --- | --- |
| `GET` | `/api/v1/customers/health` | Check API health |
| `POST` | `/api/v1/customers` | Create a customer |
| `GET` | `/api/v1/customers` | List all customers |
| `GET` | `/api/v1/customers/{id}` | Get a customer by ID |

### Create a customer

```powershell
Invoke-RestMethod `
  -Method Post `
  -Uri "http://localhost:8080/api/v1/customers" `
  -ContentType "application/json" `
  -Body '{"name":"Ada Lovelace","email":"ada@example.com"}'
```

Alternatively, with `curl`:

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

### Get a customer by ID

```powershell
Invoke-RestMethod -Method Get -Uri "http://localhost:8080/api/v1/customers/1"
```

```bash
curl http://localhost:8080/api/v1/customers/1
```

## Configuration

Application configuration is in `src/main/resources/application.properties`.
Hibernate is configured with `ddl-auto=update`, so the `customers` table is created or updated automatically during startup.
