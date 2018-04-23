package offers;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import javax.persistence.Embeddable;
import java.math.BigDecimal;
import java.util.Currency;

@Embeddable
@JsonSerialize(using = AmountSerializer.class)
public class Amount {
    private BigDecimal value;
    private Currency currency;

    public Amount() {
    }

    public Amount(BigDecimal value, Currency currency) {
        this.value = value;
        this.currency = currency;
    }

    public BigDecimal getValue() {
        return this.value;
    }

    public Currency getCurrency() {
        return this.currency;
    }

    public Amount subtract(Amount other) {
        return new Amount(this.value.subtract(other.getValue()), this.currency);
    }

    public Amount multiply(double other) {
        return new Amount(this.value.multiply(BigDecimal.valueOf(other)), this.currency);
    }

    @Override
    public String toString() {
        return String.format("%.2f", this.value);
    }
}
