package offers;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import org.springframework.hateoas.ResourceSupport;

public class OfferResource extends ResourceSupport {
    private final Offer offer;

    OfferResource(Offer offer) {
        this.offer = offer;
        this.add(linkTo(OffersController.class)
                .slash(offer.getId())
                .withSelfRel());
    }

    @SuppressWarnings("unused")
    public Offer getOffer() {
        return offer;
    }
}