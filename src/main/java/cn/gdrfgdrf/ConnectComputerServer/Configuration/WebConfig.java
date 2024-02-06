package cn.gdrfgdrf.ConnectComputerServer.Configuration;

import cn.gdrfgdrf.ConnectComputerServer.Resolver.UserArgumentResolver;
import cn.gdrfgdrf.ConnectComputerServer.Result.Result;
import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.support.spring6.http.converter.FastJsonHttpMessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.util.StreamUtils;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.LinkedList;
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
        FastJsonHttpMessageConverter fastJsonHttpMessageConverter = fastJsonHttpMessageConverter();

        converters.add(0, fastJsonHttpMessageConverter);
        WebMvcConfigurer.super.configureMessageConverters(converters);
    }

    @Bean
    public FastJsonHttpMessageConverter fastJsonHttpMessageConverter() {
        FastJsonHttpMessageConverter fastJsonHttpMessageConverter = new FastJsonHttpMessageConverter() {
            @Override
            protected void writeInternal(Object object, HttpOutputMessage outputMessage) throws IOException, HttpMessageNotWritableException {
                HttpHeaders headers = outputMessage.getHeaders();

                MediaType mediaType = headers.getContentType();
                Charset charset = mediaType != null ? mediaType.getCharset() : StandardCharsets.UTF_8;
                if (charset == null) {
                    charset = StandardCharsets.UTF_8;
                }

                if (object instanceof String content) {
                    try {
                        byte[] strBytes = content.getBytes();
                        int contentLength = strBytes.length;
                        StreamUtils.copy(content, charset, outputMessage.getBody());

                        if (headers.getContentLength() < 0
                                && getFastJsonConfig().isWriteContentLength()) {
                            headers.setContentLength(contentLength);
                        }
                    } catch (IOException e) {
                        throw new HttpMessageNotWritableException("I/O error while writing output message", e);
                    }

                    return;
                }
                if (object instanceof Result result) {
                    try {
                        String content = JSON.toJSONString(result);

                        byte[] strBytes = content.getBytes(charset);
                        int contentLength = strBytes.length;
                        StreamUtils.copy(content, charset, outputMessage.getBody());

                        if (headers.getContentLength() < 0
                                && getFastJsonConfig().isWriteContentLength()) {
                            headers.setContentLength(contentLength);
                        }
                    } catch (IOException e) {
                        throw new HttpMessageNotWritableException("I/O error while writing output message", e);
                    }

                    return;
                }

                super.writeInternal(object, outputMessage);
            }
        };

        List<MediaType> supportedMediaTypes = new LinkedList<>();
        supportedMediaTypes.add(MediaType.APPLICATION_JSON);
        fastJsonHttpMessageConverter.setSupportedMediaTypes(supportedMediaTypes);

        return fastJsonHttpMessageConverter;
    }
}
