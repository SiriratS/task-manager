# Task Manager

A simple task management application built with Spring Boot. This application provides a RESTful API to create, read, update, and delete tasks.

## Features

-   **Create Task**: Add new tasks with a title, description, and completion status.
-   **Read Tasks**: Retrieve a list of all tasks or a specific task by ID.
-   **Update Task**: Modify existing tasks.
-   **Delete Task**: Remove tasks from the system.

## Technologies Used

-   **Java 17**
-   **Spring Boot 3.2.0**
-   **Spring Data JPA**
-   **Spring Web**
-   **H2 Database** (In-memory database)

## Prerequisites

-   Java Development Kit (JDK) 17 or higher

### Optional: Why `mvnw`?
The Maven Wrapper (`mvnw`) is included as an optional convenience to ensure:
1.  **No Installation Required**: You can build and run the project without manually installing Maven.
2.  **Version Consistency**: It ensures everyone builds the project with the exact same Maven version.

*Note: If you have Maven installed, you can use the `mvn` command instead.*


## Getting Started

### Installation

1.  Clone the repository:
    ```bash
    git clone <repository-url>
    ```
2.  Navigate to the project directory:
    ```bash
    cd task-manager
    ```
3.  Build the project:
    ```bash
    mvn clean install
    ```
    > **Note**: If you don't have Maven installed, use `./mvnw clean install` (or `.\mvnw clean install` on Windows).

### Running the Application

Run the application using Maven:

```bash
mvn spring-boot:run
```
> **Note**: If you don't have Maven installed, use `./mvnw spring-boot:run` (or `.\mvnw spring-boot:run` on Windows).

The application will start on `http://localhost:8080`.

## API Endpoints

| Method | Endpoint      | Description                 |
| :----- | :------------ | :-------------------------- |
| GET    | `/tasks`      | Retrieve all tasks          |
| GET    | `/tasks/{id}` | Retrieve a task by ID       |
| POST   | `/tasks`      | Create a new task           |
| PUT    | `/tasks/{id}` | Update an existing task     |
| DELETE | `/tasks/{id}` | Delete a task by ID         |

### Example Request Body (POST/PUT)

```json
{
  "title": "Buy groceries",
  "description": "Milk, Bread, Eggs",
  "completed": false
}
```

## Database

The application uses an in-memory H2 database. You can access the H2 console at:

`http://localhost:8080/h2-console`

-   **JDBC URL**: `jdbc:h2:mem:testdb`
-   **User Name**: `sa`
-   **Password**: `password`
