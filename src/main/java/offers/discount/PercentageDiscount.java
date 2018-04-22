package offers.discount;

import offers.Amount;

public class PercentageDiscount implements Discount {
    private double percentage;

    public PercentageDiscount(int percentage) {
        this.percentage = percentage;
    }

    @Override
    public Amount apply(Amount original) {
        return original.subtract(original.multiply(this.percentage / 100));
    }
}
