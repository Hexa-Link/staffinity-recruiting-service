# Staffinity - Recruiting Microservice

Microservice responsible for managing vacancies and candidates. Built with **Java 17**, Spring Boot, and following a strict Hexagonal Architecture.

## 📋 Prerequisites

- **Java 17** (OpenJDK 17).
- **Docker** (recommended for the database) or a local PostgreSQL server.
- **PostgreSQL** running on port `5433` with a database named `recruiting`.

## 🚀 Execution Commands (Gradle)

It is not necessary to have Gradle installed globally; use the included wrapper script (`gradlew`).

### 1. Run the Application
This command downloads dependencies, compiles, and starts the server on port `8080`.

**On Mac/Linux:**
```bash
./gradlew bootRun
```

**On Windows:**
```bash
.\gradlew.bat bootRun
```

### 2. Build the Project
To compile and generate the `.jar` artifact in the `build/libs` folder:

**On Mac/Linux:**
```bash
./gradlew clean build -x test
```

**On Windows:**
```bash
.\gradlew.bat clean build -x test
```

## ⚙️ Verification

### Health Check
Once the application is running, you can verify it has started correctly by checking the Actuator endpoint:

- **URL:** `http://localhost:8080/api/recruiting/actuator/health`
- **Command:** `curl -i http://localhost:8080/api/recruiting/actuator/health`
- **Expected Response:** `{"status":"UP"}`

## 🏗 Project Structure

The project follows a strict **Hexagonal Architecture**, separating Domain, Application, and Infrastructure layers.

```text
src
├── main
│   ├── java
│   │   └── com
│   │       └── staffinity
│   │           └── recruiting
│   │               ├── RecruitingApplication.java
│   │               ├── candidates
│   │               │   ├── application
│   │               │   │   ├── dto
│   │               │   │   └── usecases
│   │               │   ├── domain
│   │               │   │   ├── exception
│   │               │   │   ├── model
│   │               │   │   └── ports
│   │               │   └── infrastructure
│   │               │       ├── adapters
│   │               │       ├── persistance
│   │               │       └── web
│   │               ├── common
│   │               │   ├── dto
│   │               │   ├── exception
│   │               │   └── util
│   │               ├── security
│   │               │   ├── domain
│   │               │   └── infrastructure
│   │               └── vacancies
│   │                   ├── application
│   │                   │   ├── dto
│   │                   │   └── usecases
│   │                   ├── domain
│   │                   │   ├── exception
│   │                   │   ├── model
│   │                   │   └── ports
│   │                   └── infrastructure
│   │                       ├── adapters
│   │                       ├── persistence
│   │                       └── web
│   └── resources
│       ├── application.yaml
│       ├── db
│       │   └── migration
│       ├── static
│       └── templates
└── test
    └── java
        └── com
            └── staffinity
                └── recruiting
                    └── RecruitingApplicationTests.java