package com.seclib.htbp.hosp

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration
import org.springframework.context.annotation.ComponentScan
import org.springframework.cloud.openfeign.EnableFeignClients
import kotlin.jvm.JvmStatic
import org.springframework.boot.SpringApplication
import org.springframework.cloud.client.discovery.EnableDiscoveryClient

//@SpringBootApplication
@SpringBootApplication(exclude = [SecurityAutoConfiguration::class])
@ComponentScan(basePackages = ["com.seclib.htbp"]) //@EnableAutoConfiguration
@EnableDiscoveryClient
@EnableFeignClients(basePackages = ["com.seclib.htbp"])
open class ServiceHospApplication {
    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            SpringApplication.run(ServiceHospApplication::class.java, *args)
        }
    }
}