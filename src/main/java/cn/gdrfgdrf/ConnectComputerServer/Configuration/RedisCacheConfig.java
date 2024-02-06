package cn.gdrfgdrf.ConnectComputerServer.Configuration;

import cn.gdrfgdrf.ConnectComputerServer.Result.Result;
import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.support.spring6.data.redis.GenericFastJsonRedisSerializer;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
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
        return new GenericFastJsonRedisSerializer() {
            @Override
            public byte[] serialize(Object object) throws SerializationException {
                if (object instanceof Result result) {
                    String str = JSON.toJSONString(result);
                    return str.getBytes(StandardCharsets.UTF_8);
                }

                return super.serialize(object);
            }

            @Override
            public Object deserialize(byte[] bytes) throws SerializationException {
                String str = new String(bytes);
                return JSON.parseObject(str, Result.class);
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
