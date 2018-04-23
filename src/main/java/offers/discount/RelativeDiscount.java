package offers.discount;

import offers.Amount;

import java.math.BigDecimal;

public class RelativeDiscount implements Discount {
    private BigDecimal amount;

    public RelativeDiscount(BigDecimal amount) {
        this.amount = amount;
    }

    @Override
    public Amount apply(Amount original) {
        return original.subtract(this.amount);
    }
}
