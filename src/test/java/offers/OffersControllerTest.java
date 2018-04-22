package offers;

import offers.discount.PercentageDiscount;
import offers.discount.RelativeDiscount;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
@WebAppConfiguration
public class OffersControllerTest {
    private MockMvc mock;

    @Autowired
    private OffersRepository repository;
    @Autowired
    private WebApplicationContext context;

    private Offer offer;

    @Before
    public void setup() {
        this.mock = MockMvcBuilders.webAppContextSetup(context).build();
        this.repository.deleteAllInBatch();

        Calendar calendar = Calendar.getInstance(Locale.getDefault());
        calendar.add(Calendar.DAY_OF_YEAR, 1);

        this.offer = new OfferBuilder()
                .withDescription("offerDescription")
                .withOriginalPrice(new Amount(BigDecimal.valueOf(2.56), Currency.getInstance("GBP")))
                .withDiscount(new PercentageDiscount(10))
                .withExpiryDate(calendar.getTime())
                .build();

        this.repository.save(this.offer);
    }

    @Test
    public void getSingleExisting() throws Exception {
        this.mock.perform(get("/offers/" + this.offer.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(this.offer.getId().toString())))
                .andExpect(jsonPath("$.description", is(this.offer.getDescription())))
                .andExpect(jsonPath("$.original_price", is(this.offer.getOriginalPrice().toString())))
                .andExpect(jsonPath("$.discounted_price", is(this.offer.getDiscountedPrice().toString())))
                .andExpect(jsonPath("$.expiry_date", is(new SimpleDateFormat("yyyy-MM-dd").format(this.offer.getExpiryDate()))))
                .andExpect(jsonPath("$.is_valid", is(this.offer.getIsValid())));
    }

    @Test
    public void getSingleNonExisting() throws Exception {
        this.mock.perform(get("/offers/" + UUID.randomUUID()))
                .andExpect(status().isNotFound());
    }

    @Test
    public void getAll() throws Exception {
        Currency currency = Currency.getInstance("GBP");
        Offer anotherOffer = new OfferBuilder()
                .withOriginalPrice(new Amount(new BigDecimal(25.0), currency))
                .withDiscount(new RelativeDiscount(new Amount(new BigDecimal(5.0), currency)))
                .build();

        anotherOffer = this.repository.save(anotherOffer);

        this.mock.perform(get("/offers"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id", is(this.offer.getId().toString())))
                .andExpect(jsonPath("$[1].id", is(anotherOffer.getId().toString())));
    }
}
