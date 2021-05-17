package me.forself.playground.microservice

import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig
import io.github.resilience4j.timelimiter.TimeLimiterConfig
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.circuitbreaker.resilience4j.ReactiveResilience4JCircuitBreakerFactory
import org.springframework.cloud.circuitbreaker.resilience4j.Resilience4JConfigBuilder
import org.springframework.cloud.client.circuitbreaker.CircuitBreaker
import org.springframework.cloud.client.circuitbreaker.CircuitBreakerFactory
import org.springframework.cloud.client.circuitbreaker.Customizer
import org.springframework.cloud.gateway.filter.factory.SpringCloudCircuitBreakerFilterFactory
import org.springframework.cloud.gateway.filter.factory.SpringCloudCircuitBreakerResilience4JFilterFactory
import org.springframework.cloud.gateway.filter.ratelimit.KeyResolver
import org.springframework.cloud.gateway.route.RouteLocator
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder
import org.springframework.cloud.netflix.eureka.EnableEurekaClient
import org.springframework.context.annotation.Bean
import reactor.core.publisher.Mono
import java.time.Duration

@SpringBootApplication
@EnableEurekaClient
class GatewayApplication {
    @Bean
    fun keyResolver() = KeyResolver { Mono.just("1") }

    @Bean
    fun circuitBreakerFactory(): Customizer<ReactiveResilience4JCircuitBreakerFactory> =
        Customizer { factory: ReactiveResilience4JCircuitBreakerFactory ->
            factory.configureDefault { id: String? ->
                Resilience4JConfigBuilder(id)
                    .timeLimiterConfig(
                        TimeLimiterConfig.custom()
                            .timeoutDuration(Duration.ofMillis(500))
                            .build()
                    )
                    .circuitBreakerConfig(
                        CircuitBreakerConfig.custom()
                            .slidingWindowSize(10)
                            .failureRateThreshold(33.3F)
                            .slowCallRateThreshold(33.3F)
                            .build()
                    )
                    .build()
            }
        }

    @Bean
    fun authFilter() = AuthGatewayFilterFactory()
}

fun main(args: Array<String>) {
    runApplication<GatewayApplication>(*args)
}
