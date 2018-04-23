package offers;

import org.junit.Test;

import static org.junit.Assert.*;

import java.util.Calendar;

public class OfferTest {
    @Test
    public void merge() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_YEAR, 1);

        Offer offer = new OfferBuilder()
                .withDescription("original description")
                .withExpiryDate(calendar.getTime())
                .build();

        assertTrue(offer.getIsValid());
        OfferUpdate update = new OfferUpdate(null, null, false);
        offer.merge(update);
        assertFalse(offer.getIsValid());

        update = new OfferUpdate("new description", null, false);
        offer.merge(update);
        assertEquals("new description", offer.getDescription());
        assertNotEquals(update.getExpiryDate(), offer.getExpiryDate());

        update = new OfferUpdate(null, calendar.getTime(), true);
        offer.merge(update);
        assertEquals(update.getExpiryDate(), offer.getExpiryDate());
        assertEquals("new description", offer.getDescription());
        assertTrue(offer.getIsValid());
    }

    @Test
    public void invalidateExpiredOffer() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_YEAR, 1);

        Offer offer = new OfferBuilder().withExpiryDate(calendar.getTime()).build();
        assertTrue(offer.getIsValid());

        calendar.add(Calendar.DAY_OF_YEAR, -2);
        offer = new OfferBuilder().withExpiryDate(calendar.getTime()).build();
        assertFalse(offer.getIsValid());
    }
}
