package offers;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.UUID;

@Entity
public class Offer {
    @Id
    @GeneratedValue
    private UUID id;

    private String description;

    public Offer() {
    }

    public Offer(String description) {
        this.description = description;
    }

    public UUID getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }
}
