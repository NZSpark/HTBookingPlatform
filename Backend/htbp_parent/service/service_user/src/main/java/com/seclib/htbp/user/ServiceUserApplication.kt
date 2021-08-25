package com.seclib.htbp.user


import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.context.annotation.ComponentScan
import org.springframework.cloud.openfeign.EnableFeignClients
import kotlin.jvm.JvmStatic
import org.springframework.boot.SpringApplication
import org.springframework.cloud.client.discovery.EnableDiscoveryClient

@SpringBootApplication
@ComponentScan(basePackages = ["com.seclib.htbp"])
@EnableDiscoveryClient
@EnableFeignClients(basePackages = ["com.seclib.htbp"])
open class  ServiceUserApplication {
    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            SpringApplication.run(ServiceUserApplication::class.java, *args)
        }
    }
}