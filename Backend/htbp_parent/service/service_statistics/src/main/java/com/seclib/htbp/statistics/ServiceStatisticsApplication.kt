package com.seclib.htbp.statistics


import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration
import org.springframework.cloud.openfeign.EnableFeignClients
import org.springframework.context.annotation.ComponentScan
import kotlin.jvm.JvmStatic
import org.springframework.boot.SpringApplication
import org.springframework.cloud.client.discovery.EnableDiscoveryClient

@SpringBootApplication(exclude = [DataSourceAutoConfiguration::class])
@EnableDiscoveryClient
@EnableFeignClients(basePackages = ["com.seclib.htbp"])
@ComponentScan(basePackages = ["com.seclib.htbp"])
open class  ServiceStatisticsApplication {
    companion object {
    @JvmStatic
    fun main(args: Array<String>) {
        SpringApplication.run(ServiceStatisticsApplication::class.java, *args)
    }
}
}