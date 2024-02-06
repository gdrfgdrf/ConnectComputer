package cn.gdrfgdrf.ConnectComputerServer.Configuration;

import com.github.dreamyoung.mprelation.AutoMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author gdrfgdrf
 */
@Configuration
public class AutoMapperConfig {
    @Bean
    public AutoMapper autoMapper() {
        return new AutoMapper(new String[]{"cn.gdrfgdrf.ConnectComputerServer.Bean.POJO"});
    }
}
