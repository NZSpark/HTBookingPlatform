package com.seclib.htbp.gateway

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration
import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration
import kotlin.jvm.JvmStatic
import org.springframework.boot.SpringApplication

@SpringBootApplication(exclude = [SecurityAutoConfiguration::class, DataSourceAutoConfiguration::class])
@EnableAutoConfiguration(exclude = [MongoAutoConfiguration::class]) //@ComponentScan(basePackages = "com.seclib.htbp")
public open class ServerGatewayApplication {
    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            SpringApplication.run(ServerGatewayApplication::class.java, *args)
        }
    }
}