package me.forself.playground.microservice

import org.mybatis.spring.annotation.MapperScan
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.client.discovery.EnableDiscoveryClient
import org.springframework.cloud.netflix.eureka.EnableEurekaClient

@SpringBootApplication
@EnableEurekaClient
@EnableDiscoveryClient
@MapperScan("me.forself.playground.microservice.repository")
class ProviderApplication

fun main(args: Array<String>) {
    runApplication<ProviderApplication>(*args)
}
