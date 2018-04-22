package offers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/offers")
public class OffersController {
    private final OffersRepository repository;

    @Autowired
    public OffersController(OffersRepository repository) {
        this.repository = repository;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/{id}")
    @SuppressWarnings("unused")
    ResponseEntity<?> getSingle(@PathVariable UUID id) {
        return this.repository.findById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @RequestMapping(method = RequestMethod.GET)
    @SuppressWarnings("unused")
    Collection<Offer> getAll() {
        return this.repository.findAll();
    }

    @RequestMapping(method = RequestMethod.PATCH, value = "/{id}")
    @SuppressWarnings("unused")
    ResponseEntity<?> updateSingle(@PathVariable UUID id, @ModelAttribute OfferUpdate update) {
        Optional<Offer> offer = this.repository.findById(id);

        if (offer.isPresent()) {
            offer.get().merge(update);
            return ResponseEntity.accepted().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
