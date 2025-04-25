# Task Management System

A RESTful API built with Spring Boot for managing tasks.

## Features

- CRUD operations for tasks
- Input validation
- Error handling
- OpenAPI documentation
- H2 in-memory database
- Clean architecture

## Technologies Used

- Java 17
- Spring Boot 3.2.3
- Spring Data JPA
- H2 Database
- OpenAPI (Swagger)
- Maven

## Getting Started

### Prerequisites

- Java 17 or higher
- Maven 3.6 or higher

### Installation

1. Clone the repository
2. Navigate to the project directory
3. Run the application:
   ```bash
   mvn spring-boot:run
   ```

### API Documentation

Once the application is running, you can access:
- Swagger UI: http://localhost:8080/swagger-ui.html
- OpenAPI documentation: http://localhost:8080/api-docs

### H2 Console

Access the H2 database console at: http://localhost:8080/h2-console
- JDBC URL: jdbc:h2:mem:taskdb
- Username: sa
- Password: password

## API Endpoints

- `GET /api/tasks` - Get all tasks
- `GET /api/tasks/{id}` - Get task by ID
- `POST /api/tasks` - Create a new task
- `PUT /api/tasks/{id}` - Update an existing task
- `DELETE /api/tasks/{id}` - Delete a task

## Example Task JSON

```json
{
    "title": "Complete project documentation",
    "description": "Write comprehensive documentation for the task management system",
    "dueDate": "2024-03-31T23:59:59",
    "status": "PENDING"
}
```

## Error Handling

The API includes comprehensive error handling for:
- Task not found
- Validation errors
- General exceptions

## License

This project is licensed under the MIT License. 