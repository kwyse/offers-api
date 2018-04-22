package offers;

import org.junit.Test;

import static org.junit.Assert.*;

import java.util.Calendar;

public class OfferTest {
    @Test
    public void merge() {
        Offer offer = new OfferBuilder()
                .withDescription("original description")
                .withExpiryDate(Calendar.getInstance().getTime())
                .build();


        assertTrue(offer.getIsValid());
        OfferUpdate update = new OfferUpdate(null, null, false);
        offer.merge(update);
        assertFalse(offer.getIsValid());

        update = new OfferUpdate("new description", null, false);
        offer.merge(update);
        assertEquals("new description", offer.getDescription());
        assertNotEquals(update.getExpiryDate(), offer.getExpiryDate());

        update = new OfferUpdate(null, Calendar.getInstance().getTime(), true);
        offer.merge(update);
        assertEquals(update.getExpiryDate(), offer.getExpiryDate());
        assertEquals("new description", offer.getDescription());
        assertTrue(offer.getIsValid());
    }
}
