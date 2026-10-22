package ca.sfu.ratecsstaff;

import ca.sfu.ratecsstaff.repository.StaffRatingRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class StaffRatingRepositoryTest {

    @Autowired
    private StaffRatingRepository repository;

    @Test
    void testSaveAndRetrieveRating() {
        StaffRating rating = new StaffRating();
        rating.setName("Prof. Alan Turing");
        rating.setEmail("turing@sfu.ca");
        rating.setRoleType(RoleType.PROF);
        rating.setClarity(10);
        rating.setNiceness(9);
        rating.setKnowledgeableScore(10);

        StaffRating saved = repository.save(rating);
        assertNotNull(saved.getId());

        Optional<StaffRating> retrieved = repository.findById(saved.getId());
        assertTrue(retrieved.isPresent());
        assertEquals("Prof. Alan Turing", retrieved.get().getName());
        assertEquals("turing@sfu.ca", retrieved.get().getEmail());
    }

    @Test
    void testDeleteRating() {
        StaffRating rating = new StaffRating();
        rating.setName("Temp Staff");
        rating.setEmail("temp@sfu.ca");
        rating.setRoleType(RoleType.STAFF);
        rating.setClarity(5);
        rating.setNiceness(5);
        rating.setKnowledgeableScore(5);

        StaffRating saved = repository.save(rating);
        Long id = saved.getId();

        assertTrue(repository.existsById(id));

        repository.deleteById(id);
        assertFalse(repository.existsById(id));
    }
}