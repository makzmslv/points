package game.config;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
public class WebConfig extends WebMvcConfigurerAdapter
{

    public static final String INDEX_PATH = "/frontend/index.html";
    public static final String SWAGGER_PATH = "/swagger-ui.html";

    @Override
    public void configureContentNegotiation(ContentNegotiationConfigurer configurer)
    {
        // @formatter:off
        configurer
            .favorPathExtension(false)
            .favorParameter(true)
            .parameterName("mediaType")
            .ignoreAcceptHeader(true)
            .useJaf(false)
            .defaultContentType(MediaType.APPLICATION_JSON)
            .mediaType("json", MediaType.APPLICATION_JSON);
        // @formatter:on
    }

    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters)
    {
        converters.add(converter());
    }

    @Bean
    MappingJackson2HttpMessageConverter converter()
    {
        MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
        converter.setObjectMapper(new JacksonObjectMapper());
        return converter;
    }

    @Override
    public void addViewControllers(ViewControllerRegistry registry)
    {
        registry.addRedirectViewController("/", INDEX_PATH);
        registry.addRedirectViewController("/frontend", INDEX_PATH);
        registry.addRedirectViewController("/frontend/", INDEX_PATH);
        registry.addRedirectViewController("/swagger", SWAGGER_PATH);
        registry.addRedirectViewController("/swagger/", SWAGGER_PATH);
    }

}