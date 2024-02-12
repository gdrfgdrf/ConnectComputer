package cn.gdrfgdrf.ConnectComputerServer.Configuration;

import cn.gdrfgdrf.ConnectComputerServer.Converter.JacksonHttpMessageConverter;
import cn.gdrfgdrf.ConnectComputerServer.Resolver.UserArgumentResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.ArrayList;
import java.util.List;

/**
 * @author gdrfgdrf
 */
@Configuration
@EnableWebMvc
public class WebConfig implements WebMvcConfigurer {
    private final UserArgumentResolver userArgumentResolver;

    @Autowired
    public WebConfig(UserArgumentResolver userArgumentResolver) {
        this.userArgumentResolver = userArgumentResolver;
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(userArgumentResolver);
    }

    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        JacksonHttpMessageConverter jacksonHttpMessageConverter = jacksonHttpMessageConverter();

        converters.add(0, jacksonHttpMessageConverter);
        WebMvcConfigurer.super.configureMessageConverters(converters);
    }

    @Bean
    public JacksonHttpMessageConverter jacksonHttpMessageConverter() {
        JacksonHttpMessageConverter jacksonHttpMessageConverter = new JacksonHttpMessageConverter();

        List<MediaType> supportedMediaTypes = new ArrayList<>();
        supportedMediaTypes.add(MediaType.APPLICATION_JSON);
        jacksonHttpMessageConverter.setSupportedMediaTypes(supportedMediaTypes);

        return jacksonHttpMessageConverter;
    }
}
