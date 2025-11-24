# Task Manager

A simple task management application built with Spring Boot. This application provides a RESTful API to create, read, update, and delete tasks.

## Features

-   **Create Task**: Add new tasks with a title, description, and completion status.
-   **Read Tasks**: Retrieve a list of all tasks or a specific task by ID.
-   **Update Task**: Modify existing tasks.
-   **Delete Task**: Remove tasks from the system.

## Technologies Used

-   **Java 21**
-   **Spring Boot 3.2.0**
-   **Spring Data JPA**
-   **Spring Web**
-   **H2 Database** (Development - In-memory)
-   **PostgreSQL** (Production - Cloud database)

## Prerequisites

-   Java Development Kit (JDK) 21 or higher

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

## Environment Configuration

This application supports two environments:
- **Development (`dev`)**: Uses H2 in-memory database
- **Production (`prod`)**: Uses PostgreSQL (Neon.tech or other cloud providers)

### Development Environment (Default)

The application runs in `dev` mode by default, using H2 database.

**Run the application:**
```bash
mvn spring-boot:run
```

The application will start on `http://localhost:8080`.

**Access H2 Console:**
- URL: `http://localhost:8080/h2-console`
- JDBC URL: `jdbc:h2:mem:testdb`
- Username: `sa`
- Password: `password`

### Production Environment

To run with the production database (PostgreSQL), you need to set up environment variables.

#### Option 1: Using `.env` File (Recommended for Local Testing)

1. **Copy the example file:**
   ```bash
   cp .env.example .env
   ```

2. **Edit `.env` with your database credentials:**
   ```properties
   SPRING_PROFILES_ACTIVE=prod
   SPRING_DATASOURCE_URL=jdbc:postgresql://your-host.neon.tech/your-database?sslmode=require
   SPRING_DATASOURCE_USERNAME=your_username
   SPRING_DATASOURCE_PASSWORD=your_password
   ```

3. **Run the application:**
   ```bash
   mvn spring-boot:run
   ```
   
   The `.env` file will be automatically loaded, and the application will connect to your PostgreSQL database.

#### Option 2: Using Environment Variables (For Cloud Deployment)

Set these environment variables in your hosting platform (Render, Railway, Heroku, etc.):

| Variable | Description | Example |
|:---------|:------------|:--------|
| `SPRING_PROFILES_ACTIVE` | Active profile | `prod` |
| `SPRING_DATASOURCE_URL` | Database connection URL | `jdbc:postgresql://host:5432/db?sslmode=require` |
| `SPRING_DATASOURCE_USERNAME` | Database username | `your_username` |
| `SPRING_DATASOURCE_PASSWORD` | Database password | `your_password` |

### Key Differences Between Environments

| Feature | Development (`dev`) | Production (`prod`) |
|:--------|:-------------------|:--------------------|
| Database | H2 (in-memory) | PostgreSQL (persistent) |
| Data Persistence | Lost on restart | Persisted in cloud |
| H2 Console | Enabled | Disabled |
| Configuration File | `application-dev.properties` | `application-prod.properties` |

## Testing

This project includes a comprehensive JUnit 5 test suite covering Service, Controller, and Repository layers.

### Running Tests

To run all unit tests:
```bash
mvn test
```

### Code Coverage

The project uses JaCoCo for code coverage reporting. To generate the coverage report:

```bash
mvn clean test
```

The report will be available at: `target/site/jacoco/index.html`

## Code Quality & Linting

This project uses two linting tools to ensure code quality and adherence to coding standards:

### Checkstyle

Checkstyle enforces Java coding standards (configured for 4-space indentation). To run Checkstyle:

```bash
mvn checkstyle:check
```

To generate a detailed Checkstyle report:

```bash
mvn checkstyle:checkstyle
```

The report will be available at: `target/site/checkstyle.html`

### PMD

PMD performs static code analysis to find common programming flaws. To run PMD:

```bash
mvn pmd:check
```

To generate a detailed PMD report:

```bash
mvn pmd:pmd
```

The report will be available at: `target/site/pmd.html`

### Running All Quality Checks

To run all quality checks (tests, code coverage, Checkstyle, and PMD) at once:

```bash
mvn clean verify
```

> **Note**: Both Checkstyle and PMD are configured to fail the build if violations are found. This ensures code quality standards are maintained.


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

## Security Notes

- **Never commit `.env` to Git** - It's already in `.gitignore`
- Use `.env.example` as a template for other developers
- For production deployments, use your hosting platform's environment variable settings
