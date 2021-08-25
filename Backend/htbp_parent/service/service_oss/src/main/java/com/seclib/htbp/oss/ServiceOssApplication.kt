package com.seclib.htbp.oss


import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration
import org.springframework.context.annotation.ComponentScan
import kotlin.jvm.JvmStatic
import org.springframework.boot.SpringApplication
import org.springframework.cloud.client.discovery.EnableDiscoveryClient

@SpringBootApplication(exclude = [DataSourceAutoConfiguration::class])
@EnableDiscoveryClient
@ComponentScan(basePackages = ["com.seclib.htbp"])
open class ServiceOssApplication {
    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            SpringApplication.run(ServiceOssApplication::class.java, *args)
        }
    }
}