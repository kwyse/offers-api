package offers;

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

import java.util.UUID;

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

        this.offer = new Offer("offer description");
        this.repository.save(this.offer);
    }

    @Test
    public void getSingleExisting() throws Exception {
        this.mock.perform(get("/offers/" + this.offer.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.description", is(this.offer.getDescription())));
    }

    @Test
    public void getSingleNotExisting() throws Exception {
        this.mock.perform(get("/offers/" + UUID.randomUUID()))
                .andExpect(status().isNotFound());
    }
}
