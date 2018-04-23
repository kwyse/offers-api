package offers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resources;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

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
    Resources<OfferResource> getAll() {
        List<OfferResource> resources = this.repository.findAll()
                .stream()
                .map(OfferResource::new)
                .collect(Collectors.toList());

        return new Resources<>(resources, linkTo(OffersController.class).withSelfRel());
    }

    @RequestMapping(method = RequestMethod.POST)
    @SuppressWarnings("unused")
    ResponseEntity createSingle(@ModelAttribute Offer offer) {
        Offer persistedOffer = this.repository.saveAndFlush(offer);
        Link offerLink = new OfferResource(offer).getLink("self");

        return ResponseEntity.created(URI.create(offerLink.getHref())).build();
    }

    @RequestMapping(method = RequestMethod.PATCH, value = "/{id}")
    @SuppressWarnings("unused")
    ResponseEntity<?> updateSingle(@PathVariable UUID id, @ModelAttribute OfferUpdate update) {
        Optional<Offer> maybeOffer = this.repository.findById(id);

        if (maybeOffer.isPresent()) {
            Offer offer = maybeOffer.get();
            offer.merge(update);
            this.repository.saveAndFlush(offer);

            return ResponseEntity.accepted().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/{id}")
    @SuppressWarnings("unused")
    ResponseEntity<?> updateSingle(@PathVariable UUID id) {
        Optional<Offer> maybeOffer = this.repository.findById(id);

        if (maybeOffer.isPresent()) {
            Offer offer = maybeOffer.get();
            this.repository.delete(offer);
            this.repository.flush();

            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.noContent().build();
        }
    }
}
