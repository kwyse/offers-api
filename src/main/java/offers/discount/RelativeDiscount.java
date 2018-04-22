package offers.discount;

import offers.Amount;

public class RelativeDiscount implements Discount {
    private Amount amount;

    public RelativeDiscount(Amount amount) {
        this.amount = amount;
    }

    @Override
    public Amount apply(Amount original) {
        return original.subtract(this.amount);
    }
}
