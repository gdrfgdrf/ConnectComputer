package cn.gdrfgdrf.ConnectComputerServer.Configuration;

import cn.gdrfgdrf.ConnectComputerServer.Result.Result;
import cn.gdrfgdrf.ConnectComputerServer.Utils.JacksonUtils;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.SerializationException;

import java.nio.charset.StandardCharsets;

/**
 * @author gdrfgdrf
 */
@Configuration
@EnableCaching
public class RedisCacheConfig {
    @Bean
    @ConfigurationProperties(prefix = "spring.redis")
    public RedisConnectionFactory redisConnectionFactory() {
        return new LettuceConnectionFactory();
    }

    @Bean
    public RedisSerializer<?> redisSerializer() {
        return new GenericJackson2JsonRedisSerializer() {
            @Override
            public byte[] serialize(Object source) throws SerializationException {
                if (source instanceof Result result) {
                    String str;
                    try {
                        str = JacksonUtils.writeJsonString(result);
                    } catch (JsonProcessingException e) {
                        throw new RuntimeException(e);
                    }
                    return str.getBytes(StandardCharsets.UTF_8);
                }
                return super.serialize(source);
            }

            @Override
            public Object deserialize(byte[] source) throws SerializationException {
                String str = new String(source);
                try {
                    return JacksonUtils.readString(str, Result.class);
                } catch (JsonProcessingException e) {
                    throw new RuntimeException(e);
                }
            }
        };
    }

    @Bean
    public CacheManager cacheManager() {
        RedisCacheConfiguration redisCacheConfiguration = RedisCacheConfiguration.defaultCacheConfig()
                .disableCachingNullValues()
                .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(redisSerializer()));

        return RedisCacheManager.builder(redisConnectionFactory())
                .cacheDefaults(redisCacheConfiguration)
                .build();
    }
}
