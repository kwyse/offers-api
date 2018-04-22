package offers;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.text.SimpleDateFormat;
import java.util.List;

@EnableWebMvc
@Configuration
@SuppressWarnings("unused")
public class WebMvcConfig implements WebMvcConfigurer {

    @Autowired
    @SuppressWarnings("unused")
    private ObjectMapper objectMapper;

    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        ObjectMapper webObjectMapper = objectMapper.copy();

        webObjectMapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd"));
        webObjectMapper.setPropertyNamingStrategy(PropertyNamingStrategy.SNAKE_CASE);
        webObjectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);

        converters.add(new MappingJackson2HttpMessageConverter(webObjectMapper));
    }
}