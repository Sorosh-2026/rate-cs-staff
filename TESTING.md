# Testing Documentation

## Test Strategy & Choice of Slice Tests
We chose **slice tests** (`@WebMvcTest` and `@DataJpaTest`) along with standalone bean validation tests to isolate components effectively without needing a full-stack running server for every test execution:
1. **Validation Tests (`StaffRatingValidationTest`):** Directly tests Jakarta Bean Validation annotations on the `StaffRating` model entity to ensure emails, score ranges ([1, 10]), and required fields are strictly guarded.
2. **Web / Controller Tests (`StaffRatingControllerTest`):** Uses `@WebMvcTest` combined with `MockMvc` to test HTTP endpoints (`GET /`, `POST /ratings`), ensuring correct status codes, views, redirection paths, and model attribute passing on validation failure.
3. **Persistence Tests (`StaffRatingRepositoryTest`):** Uses `@DataJpaTest` with an in-memory database configuration to verify that JPA repositories successfully save, retrieve by ID, check existence, and delete records.

## How to Run Tests Locally
Open your terminal in the project root directory and run:

```bash
./mvnw test