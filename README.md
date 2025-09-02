# My Todo List

[![CI](https://github.com/philipp-gatzka/my-todo-list/actions/workflows/build.yml/badge.svg)](https://github.com/philipp-gatzka/my-todo-list/actions/workflows/build.yml)
[![Quality Gate Status](https://sonarcloud.io/api/project_badges/measure?project=ch.gatzka%3Amy-todo-list&metric=alert_status)](https://sonarcloud.io/summary/new_code?id=ch.gatzka%3Amy-todo-list)
[![Quality gate](https://sonarcloud.io/api/project_badges/quality_gate?project=ch.gatzka%3Amy-todo-list)](https://sonarcloud.io/summary/new_code?id=ch.gatzka%3Amy-todo-list)
[![Bugs](https://sonarcloud.io/api/project_badges/measure?project=ch.gatzka%3Amy-todo-list&metric=bugs)](https://sonarcloud.io/summary/new_code?id=ch.gatzka%3Amy-todo-list)
[![Code Smells](https://sonarcloud.io/api/project_badges/measure?project=ch.gatzka%3Amy-todo-list&metric=code_smells)](https://sonarcloud.io/summary/new_code?id=ch.gatzka%3Amy-todo-list)
[![Coverage](https://sonarcloud.io/api/project_badges/measure?project=ch.gatzka%3Amy-todo-list&metric=coverage)](https://sonarcloud.io/summary/new_code?id=ch.gatzka%3Amy-todo-list)
[![Lines of Code](https://sonarcloud.io/api/project_badges/measure?project=ch.gatzka%3Amy-todo-list&metric=ncloc)](https://sonarcloud.io/summary/new_code?id=ch.gatzka%3Amy-todo-list)
[![Technical Debt](https://sonarcloud.io/api/project_badges/measure?project=ch.gatzka%3Amy-todo-list&metric=sqale_index)](https://sonarcloud.io/summary/new_code?id=ch.gatzka%3Amy-todo-list)
[![Vulnerabilities](https://sonarcloud.io/api/project_badges/measure?project=ch.gatzka%3Amy-todo-list&metric=vulnerabilities)](https://sonarcloud.io/summary/new_code?id=ch.gatzka%3Amy-todo-list)

A modular Java 21 application demonstrating a clean separation between data access and REST API layers. The project uses Spring MVC, Spring Data JPA, Jakarta Persistence APIs, and Lombok.

[![SonarQube Cloud](https://sonarcloud.io/images/project_badges/sonarcloud-light.svg)](https://sonarcloud.io/summary/new_code?id=ch.gatzka%3Amy-todo-list)

Modules:
- my-todo-list-data: Domain entities, DTOs, mappers, repositories, and data services.
- my-todo-list-rest: REST API layer exposing CRUD endpoints over the data services.

## Tech Stack

- Language: Java 21
- Build: Gradle (Kotlin DSL)
- Frameworks:
  - Spring MVC
  - Spring Data JPA
  - Jakarta Persistence (jakarta.* imports)
- Utilities: Lombok
- Testing: JUnit (via Gradle)
- Packaging: Multi-module Gradle project

## Project Structure

- build.gradle.kts (root) and settings.gradle.kts
- my-todo-list-data
  - src/main/java/ch/gatzka/data
    - core: base classes (AbstractDTO, AbstractEntity, AbstractDataService, IRepository)
    - entity: JPA entities (Account, Task)
    - dto: Data Transfer Objects (AccountDTO, TaskDTO)
    - mapper: Entity ↔ DTO mappers (AccountMapper, TaskMapper)
    - repository: Spring Data repositories (AccountRepository, TaskRepository)
    - service: Data services (AccountDataService, TaskDataService)
- my-todo-list-rest
  - src/... (REST controllers, application bootstrap, configuration)

## Requirements

- Java 21 (ensure JAVA_HOME points to JDK 21)
- Gradle wrapper (included)
- An IDE with Lombok support (enable annotation processing)

## Configuration

- Database and application properties are typically configured under my-todo-list-rest/src/main/resources (e.g., application.properties or application.yml).
- Common settings to review:
  - spring.datasource.url
  - spring.datasource.username
  - spring.datasource.password
  - spring.jpa.hibernate.ddl-auto
  - server.port

If an in-memory database (like H2) is used, the application may start without external DB configuration. Otherwise, provide valid datasource settings.

## API Overview

The REST module typically exposes CRUD endpoints for Account and Task resources. Exact paths may vary by controller implementation, but a common shape is:

- /api/accounts
  - GET /api/accounts
  - GET /api/accounts/{id}
  - POST /api/accounts
  - PUT /api/accounts/{id}
  - DELETE /api/accounts/{id}

- /api/tasks
  - GET /api/tasks
  - GET /api/tasks/{id}
  - POST /api/tasks
  - PUT /api/tasks/{id}
  - DELETE /api/tasks/{id}

DTOs used:
- AccountDTO
- TaskDTO

Entities:
- Account
- Task

Repositories:
- AccountRepository
- TaskRepository

Data services encapsulate CRUD/business logic:
- AccountDataService
- TaskDataService

Check the controllers in my-todo-list-rest for the precise routes and request/response payloads.

## Development Tips

- Lombok: Ensure annotation processing is enabled in your IDE.
- Jakarta imports: Entities and JPA annotations use jakarta.* (not javax.*).
- Validation and error handling (if present) will typically be configured via Spring MVC annotations; review REST module for details.
- If you add new entities/DTOs:
  - Create entity in data/entity
  - Create DTO in data/dto
  - Add a mapper in data/mapper
  - Add a repository in data/repository
  - Add corresponding data service in data/service
  - Expose endpoints in my-todo-list-rest

## Build and Run Examples

- Build without tests:
  - ./gradlew build -x test

- Run only the REST module:
  - ./gradlew :my-todo-list-rest:bootRun

- Run only unit tests in data module:
  - ./gradlew :my-todo-list-data:test

## Troubleshooting

- Lombok errors in IDE:
  - Enable annotation processing and install Lombok plugin if needed.

- Database connectivity issues:
  - Verify datasource properties in my-todo-list-rest application config.
  - If using H2 in-memory, ensure the correct dialect and URL are set.

- Port conflicts:
  - Override server.port in application.properties or via command line:
    - ./gradlew :my-todo-list-rest:bootRun --args='--server.port=8081'

## License

This project is released under a Source-Available license that permits viewing, downloading, and running the code for personal or internal use only. Redistribution, public sharing, sublicensing, and modification (including creation of derivative works) are not permitted. See the LICENSE file for full terms.

Note: This is not an OSI-approved “open source” license because it restricts redistribution and modification.
