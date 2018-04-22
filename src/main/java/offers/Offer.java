package offers;

import offers.discount.Discount;

import javax.persistence.*;
import java.util.Date;
import java.util.UUID;

@Entity
public class Offer {
    @Id
    @GeneratedValue
    private UUID id;

    private String description;
    private Date expiryDate;
    private boolean isValid;

    @AttributeOverrides({
            @AttributeOverride(name = "value", column = @Column(name = "original_value")),
            @AttributeOverride(name = "currency", column = @Column(name = "original_currency"))
    })
    private Amount originalPrice;

    @AttributeOverrides({
            @AttributeOverride(name = "value", column = @Column(name = "discounted_value")),
            @AttributeOverride(name = "currency", column = @Column(name = "discounted_currency"))
    })
    private Amount discountedPrice;


    public Offer() {
        this.isValid = true;
    }

    public UUID getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public Amount getOriginalPrice() {
        return originalPrice;
    }

    public Amount getDiscountedPrice() {
        return discountedPrice;
    }

    public Date getExpiryDate() {
        return expiryDate;
    }

    public boolean getIsValid() {
        return isValid;
    }

    void setDescription(String description) {
        this.description = description;
    }

    void setOriginalPrice(Amount originalPrice) {
        this.originalPrice = originalPrice;
    }

    void setDiscount(Discount discount) {
        this.discountedPrice = discount.apply(this.originalPrice);
    }

    void setExpiryDate(Date expiryDate) {
        this.expiryDate = expiryDate;
    }
}
