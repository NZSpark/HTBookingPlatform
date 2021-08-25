package com.seclib.htbp.common.config

import org.springframework.cache.annotation.EnableCaching
import org.springframework.data.redis.connection.RedisConnectionFactory
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.annotation.JsonAutoDetect
import com.fasterxml.jackson.annotation.PropertyAccessor
import org.springframework.cache.CacheManager
import org.springframework.cache.interceptor.KeyGenerator
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.redis.cache.RedisCacheConfiguration
import org.springframework.data.redis.serializer.StringRedisSerializer
import org.springframework.data.redis.serializer.RedisSerializer
import org.springframework.data.redis.serializer.RedisSerializationContext
import org.springframework.data.redis.cache.RedisCacheManager
import java.lang.StringBuilder
import java.time.Duration

@Configuration
@EnableCaching
open class RedisConfig {
    /**
     * 自定义key规则
     * @return
     */
    @Bean
    open fun keyGenerator(): KeyGenerator {
        return KeyGenerator { target, method, params ->
            val sb = StringBuilder()
            sb.append(target.javaClass.name)
            sb.append(method.name)
            for (obj in params) {
                sb.append(obj.toString())
            }
            sb.toString()
        }
    }

    /**
     * 设置RedisTemplate规则
     * @param redisConnectionFactory
     * @return
     */
    @Bean
    open fun redisTemplate(redisConnectionFactory: RedisConnectionFactory?): RedisTemplate<Any, Any> {
        val redisTemplate = RedisTemplate<Any, Any>()
        redisTemplate.connectionFactory = redisConnectionFactory
        val jackson2JsonRedisSerializer: Jackson2JsonRedisSerializer<*> = Jackson2JsonRedisSerializer(
            Any::class.java
        )

//解决查询缓存转换异常的问题
        val om = ObjectMapper()
        // 指定要序列化的域，field,get和set,以及修饰符范围，ANY是都有包括private和public
        om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY)
        // 指定序列化输入的类型，类必须是非final修饰的，final修饰的类，比如String,Integer等会跑出异常
        om.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL)
        jackson2JsonRedisSerializer.setObjectMapper(om)

//序列号key value
        redisTemplate.keySerializer = StringRedisSerializer()
        redisTemplate.valueSerializer = jackson2JsonRedisSerializer
        redisTemplate.hashKeySerializer = StringRedisSerializer()
        redisTemplate.hashValueSerializer = jackson2JsonRedisSerializer
        redisTemplate.afterPropertiesSet()
        return redisTemplate
    }

    /**
     * 设置CacheManager缓存规则
     * @param factory
     * @return
     */
    @Bean
    open fun cacheManager(factory: RedisConnectionFactory?): CacheManager {
        val redisSerializer: RedisSerializer<String> = StringRedisSerializer()
        val jackson2JsonRedisSerializer: Jackson2JsonRedisSerializer<Any> = Jackson2JsonRedisSerializer(
            Any::class.java
        )

//解决查询缓存转换异常的问题
        val om = ObjectMapper()
        om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY)
        om.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL)
        jackson2JsonRedisSerializer.setObjectMapper(om)

// 配置序列化（解决乱码的问题）,过期时间600秒
        val config =
            RedisCacheConfiguration.defaultCacheConfig()
                .entryTtl(Duration.ofSeconds(600))
                .serializeKeysWith(
                    RedisSerializationContext.SerializationPair.fromSerializer(
                        redisSerializer
                    )
                )
                .serializeValuesWith(
                    RedisSerializationContext.SerializationPair.fromSerializer<Any>(
                        jackson2JsonRedisSerializer
                    )
                )
                .disableCachingNullValues()
        return RedisCacheManager.builder(factory)
            .cacheDefaults(config)
            .build()
    }
}