package offers;

import java.util.Date;

public class OfferUpdate {
    private String description;
    private Date expiryDate;
    private boolean isValid;

    public OfferUpdate() {
    }

    public OfferUpdate(String description, Date expiryDate, boolean isValid) {
        this.description = description;
        this.expiryDate = expiryDate;
        this.isValid = isValid;
    }

    public String getDescription() {
        return description;
    }

    public Date getExpiryDate() {
        return expiryDate;
    }

    public boolean isValid() {
        return isValid;
    }
}
