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
        if (this.description != null) offer.setDescription(this.description);
        if (this.originalPrice != null) offer.setOriginalPrice(this.originalPrice);
        if (this.discount != null) offer.setDiscount(this.discount);
        if (this.expiryDate != null) offer.setExpiryDate(this.expiryDate);

        return offer;
    }
}
