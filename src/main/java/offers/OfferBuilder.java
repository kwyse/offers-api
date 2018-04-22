package offers;

import offers.discount.Discount;

import java.util.Date;

public class OfferBuilder {
    private String description;
    private Amount originalPrice;
    private Discount discount;
    private Date expiryDate;

    public OfferBuilder() {
    }

    public OfferBuilder withDescription(String description) {
        this.description = description;
        return this;
    }

    public OfferBuilder withOriginalPrice(Amount originalPrice) {
        this.originalPrice = originalPrice;
        return this;
    }

    public OfferBuilder withDiscount(Discount discount) {
        this.discount = discount;
        return this;
    }

    public OfferBuilder withExpiryDate(Date expiryDate) {
        this.expiryDate = expiryDate;
        return this;
    }

    public Offer build() {
        Offer offer = new Offer();
        offer.setDescription(this.description);
        offer.setOriginalPrice(this.originalPrice);
        offer.setDiscount(this.discount);
        offer.setExpiryDate(this.expiryDate);

        return offer;
    }
}
