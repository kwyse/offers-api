package offers;

import offers.discount.PercentageDiscount;
import offers.discount.RelativeDiscount;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.mock.http.MockHttpOutputMessage;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
@WebAppConfiguration
public class OffersControllerTest {
    private MockMvc mock;

    private MediaType contentType = new MediaType(MediaType.APPLICATION_JSON.getType());

    @Autowired
    private OffersRepository repository;
    @Autowired
    private WebApplicationContext context;
    private HttpMessageConverter jsonMapper;

    private Offer offer;

    @Autowired
    void setConverters(HttpMessageConverter<?>[] converters) {
        this.jsonMapper = Arrays.stream(converters)
                .filter(messageConverter -> messageConverter instanceof MappingJackson2HttpMessageConverter)
                .findAny()
                .orElse(null);
    }

    @Before
    public void setup() {
        this.mock = MockMvcBuilders.webAppContextSetup(context).build();
        this.repository.deleteAllInBatch();

        Calendar calendar = Calendar.getInstance();
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
                .andExpect(jsonPath("$.original_price.value", is(this.offer.getOriginalPrice().toString())))
                .andExpect(jsonPath("$.original_price.currency", is(this.offer.getOriginalPrice().getCurrency().toString())))
                .andExpect(jsonPath("$.discounted_price.value", is(this.offer.getDiscountedPrice().toString())))
                .andExpect(jsonPath("$.discounted_price.currency", is(this.offer.getDiscountedPrice().getCurrency().toString())))
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
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_YEAR, 1);
        Currency currency = Currency.getInstance("GBP");
        Offer anotherOffer = new OfferBuilder()
                .withOriginalPrice(new Amount(new BigDecimal(25.0), currency))
                .withDiscount(new RelativeDiscount(new BigDecimal(5.0)))
                .withExpiryDate(calendar.getTime())
                .build();

        anotherOffer = this.repository.save(anotherOffer);

        this.mock.perform(get("/offers"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].offer.id", is(this.offer.getId().toString())))
                .andExpect(jsonPath("$.content[1].offer.id", is(anotherOffer.getId().toString())));
    }

    @Test
    public void create() throws Exception {
        Currency currency = Currency.getInstance("GBP");
        Offer newOffer = new OfferBuilder()
                .withDescription("a description")
                .withOriginalPrice(new Amount(new BigDecimal(25.0), currency))
                .withDiscount(new RelativeDiscount(new BigDecimal(5.0)))
                .withExpiryDate(Calendar.getInstance().getTime())
                .build();
        String offerJson = this.encodeToJson(newOffer);

        this.mock.perform(post("/offers")
                .contentType(this.contentType)
                .content(offerJson))
                .andExpect(status().isCreated());
    }

    @Test
    public void updateIsValidOnExisting() throws Exception {
        OfferUpdate update = new OfferUpdate(null, null, false);
        String updateJson = this.encodeToJson(update);

        this.mock.perform(patch("/offers/" + this.offer.getId())
                .contentType(this.contentType)
                .content(updateJson))
                .andExpect(status().isAccepted());

        assertFalse(this.repository.findById(this.offer.getId()).get().getIsValid());
    }

    @Test
    public void updateIsValidOnNonExisting() throws Exception {
        OfferUpdate update = new OfferUpdate(null, null, false);
        String updateJson = this.encodeToJson(update);

        this.mock.perform(patch("/offers/" + UUID.randomUUID())
                .contentType(this.contentType)
                .content(updateJson))
                .andExpect(status().isNotFound());

        assertTrue(this.repository.findById(this.offer.getId()).get().getIsValid());
    }

    @Test
    public void deleteExisting() throws Exception {
        this.mock.perform(delete("/offers/" + this.offer.getId()))
                .andExpect(status().isOk());

        assertFalse(this.repository.findById(this.offer.getId()).isPresent());
    }

    @Test
    public void deleteNonExisting() throws Exception {
        this.mock.perform(delete("/offers/" + UUID.randomUUID()))
                .andExpect(status().isNoContent());

        assertTrue(this.repository.findById(this.offer.getId()).isPresent());
    }

    private String encodeToJson(Object o) throws IOException {
        MockHttpOutputMessage message = new MockHttpOutputMessage();
        this.jsonMapper.write(o, MediaType.APPLICATION_JSON, message);
        return message.getBodyAsString();
    }
}
