package offers.discount;

import offers.Amount;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.Currency;

import static org.junit.Assert.assertEquals;

public class PercentageDiscountTest {
    @Test
    public void apply() {
        Discount discount = new PercentageDiscount(50);
        Amount amount = new Amount(BigDecimal.valueOf(600), Currency.getInstance("GBP"));

        assertEquals(300, discount.apply(amount).getValue().intValue());
    }
}
