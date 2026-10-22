package ca.sfu.ratecsstaff;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class StaffRatingValidationTest {

    private static Validator validator;

    @BeforeAll
    static void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void testValidStaffRatingHasNoErrors() {
        StaffRating rating = new StaffRating();
        rating.setName("Dr. Jane Doe");
        rating.setEmail("jane.doe@sfu.ca");
        rating.setRoleType(RoleType.PROF);
        rating.setClarity(9);
        rating.setNiceness(8);
        rating.setKnowledgeableScore(10);

        Set<ConstraintViolation<StaffRating>> violations = validator.validate(rating);
        assertTrue(violations.isEmpty(), "Valid rating should have no validation errors");
    }

    @Test
    void testInvalidEmailRejected() {
        StaffRating rating = new StaffRating();
        rating.setName("Test User");
        rating.setEmail("not-an-email"); // Invalid format
        rating.setRoleType(RoleType.TA);
        rating.setClarity(5);
        rating.setNiceness(5);
        rating.setKnowledgeableScore(5);

        Set<ConstraintViolation<StaffRating>> violations = validator.validate(rating);
        boolean hasEmailError = violations.stream()
                .anyMatch(v -> v.getPropertyPath().toString().equals("email"));
        assertTrue(hasEmailError, "Invalid email format should be rejected");
    }

    @Test
    void testOutOfRangeScoreRejected() {
        StaffRating rating = new StaffRating();
        rating.setName("Test User");
        rating.setEmail("test@sfu.ca");
        rating.setRoleType(RoleType.TA);
        rating.setClarity(15); // Out of range (> 10)
        rating.setNiceness(0); // Out of range (< 1)
        rating.setKnowledgeableScore(5);

        Set<ConstraintViolation<StaffRating>> violations = validator.validate(rating);
        assertFalse(violations.isEmpty(), "Out-of-range scores should be rejected");
    }

    @Test
    void testMissingRequiredFieldsRejected() {
        StaffRating rating = new StaffRating();
        rating.setName(""); // Empty name
        rating.setEmail(""); // Empty email
        rating.setRoleType(null); // Missing role

        Set<ConstraintViolation<StaffRating>> violations = validator.validate(rating);
        assertFalse(violations.isEmpty(), "Missing required fields should trigger validation errors");
    }
}