# Rate CS Teaching Staff (Iteration 1)

A Spring Boot full-CRUD web application built with Thymeleaf and PostgreSQL that allows students to view, add, edit, and delete ratings for Computer Science teaching staff.

## Features
- **Full CRUD Operations:** View a summary list, view detailed attributes, add new staff ratings, edit existing entries, and delete ratings.
- **Server-Side Validation:** Form validation guarding email format, score ranges ([1, 10]), non-empty names, and duplicate email checks.
- **OO Design / Polymorphism:** Utilizes an abstract `StaffMemberProfile` class with concrete subclasses (`ProfessorProfile`, `TaProfile`) to render polymorphic role titles dynamically in the UI.
- **Robust Test Suite:** Includes slice tests (`@WebMvcTest`, `@DataJpaTest`) and bean validation tests.

## How to Run Locally

1. **Prerequisites:** 
   - Java 17 or higher installed.
   - Local PostgreSQL instance running.

2. **Configure Database:**
   Update your `src/main/resources/application.properties` with your local PostgreSQL credentials:
   ```properties
   spring.datasource.url=jdbc:postgresql://localhost:5432/ratcsstaff_db
   spring.datasource.username=your_postgres_username
   spring.datasource.password=your_postgres_password
   spring.jpa.hibernate.ddl-auto=update