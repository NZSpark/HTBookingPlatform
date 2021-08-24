package com.seclib.htbp.cmn

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.context.annotation.ComponentScan
import kotlin.jvm.JvmStatic
import org.springframework.boot.SpringApplication
import org.springframework.cloud.client.discovery.EnableDiscoveryClient

@SpringBootApplication
@ComponentScan(basePackages = ["com.seclib.htbp"])
@EnableDiscoveryClient

open class ServiceCmnApplication {
    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            SpringApplication.run(ServiceCmnApplication::class.java, *args)
        }
    }
}