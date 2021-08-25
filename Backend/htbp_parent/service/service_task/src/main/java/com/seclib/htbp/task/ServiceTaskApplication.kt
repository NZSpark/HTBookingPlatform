package com.seclib.htbp.task

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration
import org.springframework.context.annotation.ComponentScan
import kotlin.jvm.JvmStatic
import org.springframework.boot.SpringApplication
import org.springframework.cloud.client.discovery.EnableDiscoveryClient

@SpringBootApplication(exclude = [DataSourceAutoConfiguration::class]) //取消数据源自动配置
@EnableDiscoveryClient
@ComponentScan(basePackages = ["com.seclib.htbp"])
open class  ServiceTaskApplication {
    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            SpringApplication.run(ServiceTaskApplication::class.java, *args)
        }
    }
}