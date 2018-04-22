package offers.discount;

import offers.Amount;

public interface Discount {
    Amount apply(Amount original);
}
