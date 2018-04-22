package offers;

import com.fasterxml.jackson.annotation.JsonValue;

import javax.persistence.Embeddable;
import java.math.BigDecimal;
import java.util.Currency;

@Embeddable
public class Amount {
    private BigDecimal value;
    private Currency currency;

    public Amount() {
    }

    public Amount(BigDecimal value, Currency currency) {
        this.value = value;
    }

    public BigDecimal getValue() {
        return value;
    }

    public Currency getCurrency() {
        return currency;
    }

    public Amount subtract(Amount other) {
        return new Amount(this.value.subtract(other.getValue()), this.currency);
    }

    public Amount multiply(double other) {
        return new Amount(this.value.multiply(BigDecimal.valueOf(other)), this.currency);
    }

    @Override
    @JsonValue
    public String toString() {
        return String.format("%.2f", this.value);
    }
}
