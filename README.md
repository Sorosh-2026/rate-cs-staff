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
3. Render will create `rate-cs-staff` and `rate-cs-staff-db` and connect their environment variables.
4. Wait for the first deploy to complete, then open the generated Render URL.

The application uses `${PORT:8080}` and accepts database settings from `DATABASE_JDBC_URL`, `DB_USERNAME`, and `DB_PASSWORD`. The included `render.yaml` provisions these values automatically.
