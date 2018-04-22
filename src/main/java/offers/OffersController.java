package offers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;
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
}
