package offers.discount;

import offers.Amount;

public class RelativeDiscount implements Discount {
    private Amount amount;

    public RelativeDiscount(Amount percentage) {
        this.amount = percentage;
    }

    @Override
    public Amount apply(Amount original) {
        return original.subtract(this.amount);
    }
}
