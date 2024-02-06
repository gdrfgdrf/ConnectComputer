package cn.gdrfgdrf.ConnectComputerServer.Configuration;

import cn.gdrfgdrf.ConnectComputerServer.Interceptor.AuthenticateInterceptor;
import cn.gdrfgdrf.ConnectComputerServer.Interceptor.ClientVersionInterceptor;
import cn.gdrfgdrf.ConnectComputerServer.Interceptor.LoggerInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author gdrfgdrf
 */
@Configuration
public class InterceptorConfig implements WebMvcConfigurer {
    @Bean
    public LoggerInterceptor loggerInterceptor() {
        return new LoggerInterceptor();
    }

    @Bean
    public ClientVersionInterceptor clientVersionInterceptor() {
        return new ClientVersionInterceptor();
    }

    @Bean
    public AuthenticateInterceptor authenticationInterceptor() {
        return new AuthenticateInterceptor();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(loggerInterceptor())
                .addPathPatterns("/**");

        registry.addInterceptor(clientVersionInterceptor())
                .addPathPatterns("/**")
                .excludePathPatterns("/user/**/avatar");

        registry.addInterceptor(authenticationInterceptor())
                .addPathPatterns("/**");
    }
}
