package com.seclib.htbp.common.config

import com.baomidou.mybatisplus.extension.plugins.OptimisticLockerInterceptor
import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor
import org.springframework.transaction.annotation.EnableTransactionManagement
import org.springframework.cache.annotation.EnableCaching
import org.springframework.data.redis.connection.RedisConnectionFactory
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.annotation.JsonAutoDetect
import org.mybatis.spring.annotation.MapperScan
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.redis.serializer.StringRedisSerializer
import org.springframework.data.redis.serializer.RedisSerializer
import org.springframework.data.redis.serializer.RedisSerializationContext
import org.springframework.data.redis.cache.RedisCacheManager
import springfox.documentation.swagger2.annotations.EnableSwagger2
import springfox.documentation.spring.web.plugins.Docket
import springfox.documentation.spi.DocumentationType
import springfox.documentation.builders.PathSelectors
import springfox.documentation.service.ApiInfo
import springfox.documentation.builders.ApiInfoBuilder

/**
 * MybatisPlus Configuration Class
 */
@EnableTransactionManagement
@Configuration
@MapperScan("com.seclib.htbp.*.mapper")
open class MybatisPlusConfig {
    /**
     * 分页插件
     */
    @Bean
    open fun paginationInterceptor(): PaginationInterceptor {
        return PaginationInterceptor()
    }

    /**
     * 乐观锁配置
     */
    @Bean
    open fun optimisticLockerInterceptor(): OptimisticLockerInterceptor {
        return OptimisticLockerInterceptor()
    }
}