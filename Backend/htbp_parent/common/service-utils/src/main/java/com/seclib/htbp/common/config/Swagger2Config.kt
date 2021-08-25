package com.seclib.htbp.common.config

import org.springframework.transaction.annotation.EnableTransactionManagement
import org.springframework.cache.annotation.EnableCaching
import org.springframework.data.redis.connection.RedisConnectionFactory
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.annotation.JsonAutoDetect
import com.google.common.base.Predicates
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
import springfox.documentation.service.Contact

/**
 * Swagger2配置信息
 */
@Configuration
@EnableSwagger2
open class Swagger2Config {
    @Bean
    open fun webApiConfig(): Docket {
        return Docket(DocumentationType.SWAGGER_2)
            .groupName("webApi")
            .apiInfo(webApiInfo())
            .select() //只显示api路径下的页面
            .paths(Predicates.and(PathSelectors.regex("/api/.*")))
            .build()
    }

    @Bean
    open fun adminApiConfig(): Docket {
        return Docket(DocumentationType.SWAGGER_2)
            .groupName("adminApi")
            .apiInfo(adminApiInfo())
            .select() //只显示admin路径下的页面
            .paths(Predicates.and(PathSelectors.regex("/admin/.*")))
            .build()
    }

    private fun webApiInfo(): ApiInfo {
        return ApiInfoBuilder()
            .title("网站-API文档")
            .description("本文档描述了网站微服务接口定义")
            .version("1.0")
            .contact(Contact("seclib", "http://seclib.com", "2377172938@qq.com"))
            .build()
    }

    private fun adminApiInfo(): ApiInfo {
        return ApiInfoBuilder()
            .title("后台管理系统-API文档")
            .description("本文档描述了后台管理系统微服务接口定义")
            .version("1.0")
            .contact(Contact("seclib", "http://seclib.com", "2377172938@qq.com"))
            .build()
    }
}