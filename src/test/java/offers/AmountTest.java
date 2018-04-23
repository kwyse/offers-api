package offers;

import org.junit.Test;

import java.math.BigDecimal;
import java.util.Currency;

import static org.junit.Assert.assertEquals;

public class AmountTest {
    @Test
    public void multiple() {
        Amount amount = new Amount(BigDecimal.valueOf(100), Currency.getInstance("GBP"));
        assertEquals(200, amount.multiply(2).getValue().intValue());
        assertEquals(50, amount.multiply(0.5).getValue().intValue());
        assertEquals("GBP", amount.multiply(0.5).getCurrency().toString());
    }

    @Test
    public void subtract() {
        Currency currency = Currency.getInstance("GBP");
        Amount amount = new Amount(BigDecimal.valueOf(100), currency);

        assertEquals(90, amount.subtract(BigDecimal.valueOf(10)).getValue().intValue());
        assertEquals(110, amount.subtract(BigDecimal.valueOf(-10)).getValue().intValue());
        assertEquals("GBP", amount.subtract(BigDecimal.valueOf(-10)).getCurrency().toString());
    }

    @Test
    public void constructStringRepresentation() {
        Amount amount = new Amount(BigDecimal.valueOf(100.342), Currency.getInstance("GBP"));
        assertEquals("100.34", amount.toString());
    }
}
