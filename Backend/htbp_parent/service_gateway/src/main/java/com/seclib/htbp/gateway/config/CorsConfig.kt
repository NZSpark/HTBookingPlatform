package com.seclib.htbp.gateway.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.cors.reactive.CorsWebFilter
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource
import org.springframework.web.util.pattern.PathPatternParser

@Configuration
open class CorsConfig {
    @Bean
    open fun corsFilter(): CorsWebFilter {
        val config = CorsConfiguration()
        config.addAllowedMethod("*")
        config.addAllowedOrigin("*")
        config.addAllowedHeader("*")
        val source = UrlBasedCorsConfigurationSource(PathPatternParser())
        source.registerCorsConfiguration("/**", config)
        return CorsWebFilter(source)
    }
}