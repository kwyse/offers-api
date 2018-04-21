package offers;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface OffersRepository extends JpaRepository<Offer, UUID> {
    Optional<Offer> findById(UUID id);
}
