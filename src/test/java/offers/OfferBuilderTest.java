package offers;

import offers.discount.Discount;
import offers.discount.PercentageDiscount;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Currency;
import java.util.Date;

import static org.junit.Assert.*;

public class OfferBuilderTest {
    @Test
    public void build() {
        Date expiryDate = Calendar.getInstance().getTime();
        Amount price = new Amount(BigDecimal.valueOf(40.00), Currency.getInstance("GBP"));
        Discount discount = new PercentageDiscount(25);
        Offer built = new OfferBuilder()
                .withDescription("the description")
                .withExpiryDate(expiryDate)
                .withOriginalPrice(price)
                .withDiscount(discount)
                .build();

        Amount discountedPrice = new Amount(BigDecimal.valueOf(30.00), Currency.getInstance("GBP"));
        assertEquals("the description", built.getDescription());
        assertEquals(expiryDate, built.getExpiryDate());
        assertEquals(price, built.getOriginalPrice());
        assertEquals(discountedPrice.getValue().intValue(), discount.apply(built.getOriginalPrice()).getValue().intValue());
    }
}
