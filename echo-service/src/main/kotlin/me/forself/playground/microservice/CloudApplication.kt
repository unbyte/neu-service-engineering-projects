package me.forself.playground.microservice

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.netflix.eureka.EnableEurekaClient

@SpringBootApplication
@EnableEurekaClient
class CloudApplication

fun main(args: Array<String>) {
    runApplication<CloudApplication>(*args)
}
