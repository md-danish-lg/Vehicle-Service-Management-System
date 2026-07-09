# 🔧 Vehicle Service Management System

A backend system for automotive repair shops that manages the complete vehicle servicing workflow — from customer check-in to vehicle pickup. Built with Java and Spring Boot, featuring a state machine for work order lifecycle management, a dedicated Python AI microservice for repair history summarization, and a fully containerised setup with Docker Compose.

## Tech Stack

- **Java 21** + **Spring Boot 3**
- **Spring Data JPA** + **PostgreSQL** — relational data persistence
- **Docker** + **Docker Compose** — full stack containerisation
- **RestTemplate** — service-to-service HTTP communication
- **JUnit 5** + **Mockito** + **AssertJ** — unit testing
- **GitHub Actions** — CI pipeline

## Architecture

```
HTTP Request → Controller → Service → Repository → PostgreSQL
                               ↓
                     Python AI Service (FastAPI + Groq + ChromaDB)
```

Polyglot microservice architecture — Java handles business operations, Python handles AI/LLM work.

## Entities & Relationships

```
Customer ──< Vehicle ──< WorkOrder ──< ServiceItem
                             │
                          Mechanic
```

- A **Customer** owns many Vehicles
- A **Vehicle** has many WorkOrders (service history)
- A **WorkOrder** belongs to one Vehicle and one Mechanic
- A **WorkOrder** has many ServiceItems (work performed)

## WorkOrder State Machine

```
CREATED → ASSIGNED → IN_PROGRESS → COMPLETED
```

Business rules enforced in the service layer:
- `CREATED → ASSIGNED` — requires a mechanic to be provided
- `ASSIGNED → IN_PROGRESS` — mechanic must already be assigned
- `IN_PROGRESS → COMPLETED` — must have at least one ServiceItem
- No modifications allowed on a `COMPLETED` work order
- Invalid transitions throw `InvalidWorkOrderStateException` → `400 Bad Request`

## Endpoints

### Customers
| Method | Endpoint | Description |
|--------|----------|-------------|
| `POST` | `/api/v1/customers` | Add a customer |
| `GET` | `/api/v1/customers` | Get all customers |
| `GET` | `/api/v1/customers/{id}` | Get customer with their vehicles |

### Vehicles
| Method | Endpoint | Description |
|--------|----------|-------------|
| `POST` | `/api/v1/vehicles` | Register a vehicle |
| `GET` | `/api/v1/vehicles/{id}` | Get vehicle details |
| `GET` | `/api/v1/vehicles/{id}/history` | Get all work orders for this vehicle |
| `GET` | `/api/v1/vehicles/{id}/summary` | AI-generated repair history summary |

### Mechanics
| Method | Endpoint | Description |
|--------|----------|-------------|
| `POST` | `/api/v1/mechanics` | Add a mechanic |
| `GET` | `/api/v1/mechanics` | Get all mechanics |
| `GET` | `/api/v1/mechanics/{id}/workorders` | Get active work orders for a mechanic |

### Work Orders
| Method | Endpoint | Description |
|--------|----------|-------------|
| `POST` | `/api/v1/workorders` | Create a work order |
| `GET` | `/api/v1/workorders/{id}` | Get work order with service items |
| `PATCH` | `/api/v1/workorders/{id}/assign` | Assign mechanic → ASSIGNED |
| `PATCH` | `/api/v1/workorders/{id}/start` | → IN_PROGRESS |
| `PATCH` | `/api/v1/workorders/{id}/complete` | → COMPLETED + stores to AI service |

### Service Items
| Method | Endpoint | Description |
|--------|----------|-------------|
| `POST` | `/api/v1/workorders/{id}/services` | Add a service item to a work order |
| `GET` | `/api/v1/workorders/{id}/services` | Get all service items |

## AI Integration

When a work order is **completed**, the system automatically stores the repair record in ChromaDB via the Python AI microservice. When a vehicle summary is requested, the system retrieves semantically relevant records and generates an LLM summary:

> "The 2019 Toyota Corolla has had 2 service visits. Work performed includes engine oil change, filter replacement, and engine mount inspection and replacement."

Requires the [Vehicle AI Service](https://github.com/md-danish-lg/vehicle-ai-service) running alongside.

## Error Handling

Global exception handling via `@RestControllerAdvice`:

| Exception | HTTP Status |
|-----------|-------------|
| `CustomerNotFoundException` | 404 |
| `VehicleNotFoundException` | 404 |
| `MechanicNotFoundException` | 404 |
| `WorkOrderNotFoundException` | 404 |
| `InvalidWorkOrderStateException` | 400 |
| `NotEnoughItemsException` | 400 |

## Testing

Unit tests for `WorkOrderService` covering all state machine transitions:

| Test | What it verifies |
|------|-----------------|
| `throwsWhenStatusNotCreated` | Can't assign mechanic if already assigned |
| `throwsWhenStatusNotAssigned` | Can't start if not assigned |
| `throwsWhenStatusNotInProgress` | Can't complete if not in progress |
| `throwsWhenNoServiceItems` | Can't complete without service items |
| `assignsMechanicCorrectly` | Status → ASSIGNED, save called |
| `startOrderCorrectly` | Status → IN_PROGRESS, save called |
| `completeWorkOrderCorrectly` | Status → COMPLETED, AI service called, save called |
| `throwsVehicleNotFound` | Throws on invalid vehicle ID |
| `throwsWhenWorkOrderNotFound` | Throws on invalid work order ID |

```bash
./mvnw test
```

## Running with Docker

```bash
docker-compose up --build
```

Starts Spring Boot + PostgreSQL + Python AI Service + ChromaDB together.

## Running Locally

```bash
# Start database
docker-compose up -d postgres

# Run application
./mvnw spring-boot:run
```

API available at `http://localhost:8080`

## What I Learned

- Designing and enforcing a state machine at the service layer
- Multi-entity relational data modeling with Spring Data JPA
- `@EntityGraph` for solving N+1 problems on nested relationships
- Custom exception classes per error type with `@RestControllerAdvice`
- Polyglot microservice architecture — Spring Boot calling a Python FastAPI service
- Service-to-service communication with `RestTemplate` and environment-based URL configuration
- Multi-stage Dockerfiles and Docker Compose for a 4-service stack
- Unit testing business logic with JUnit 5, Mockito, and AssertJ including state machine validation
- CI with GitHub Actions