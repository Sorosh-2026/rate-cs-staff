# Rate CS Teaching Staff

A Spring Boot full-CRUD web application built with Thymeleaf and PostgreSQL.

## Run locally

Prerequisites: Java 21 and PostgreSQL.

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/rate_staff
spring.datasource.username=postgres
spring.datasource.password=your_postgres_password
```

```bash
./mvnw spring-boot:run
```

## Deploy to Render

1. Push this repository to GitHub.
2. In Render, choose **New > Blueprint** and select this repository.
3. Render creates `rate-cs-staff` and `rate-cs-staff-db` and connects the database variables automatically.
4. Wait for the first deploy to complete, then open the generated Render URL.

The app accepts `PORT`, `DATABASE_JDBC_URL`, `DATABASE_URL`, or the individual `DB_HOST`, `DB_PORT`, `DB_DATABASE`, `DB_USERNAME`, and `DB_PASSWORD` variables. The included `render.yaml` provisions the split `DB_*` variables automatically.
