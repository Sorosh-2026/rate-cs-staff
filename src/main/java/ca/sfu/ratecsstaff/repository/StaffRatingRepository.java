
package ca.sfu.ratecsstaff.repository;

import ca.sfu.ratecsstaff.StaffRating;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface StaffRatingRepository
        extends JpaRepository<StaffRating, Long> {

    Optional<StaffRating> findByEmail(String email);

    boolean existsByEmail(String email);
}
