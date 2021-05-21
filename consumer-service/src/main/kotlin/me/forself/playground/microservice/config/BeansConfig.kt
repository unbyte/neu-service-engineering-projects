package me.forself.playground.microservice.config

import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry
import io.github.resilience4j.feign.FeignDecorators
import io.github.resilience4j.feign.Resilience4jFeign
import me.forself.playground.microservice.service.FallbackOrderService
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean
import org.springframework.cloud.client.loadbalancer.LoadBalanced
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Scope
import org.springframework.web.client.RestTemplate

@Configuration
class BeansConfig {
    @Bean
    @LoadBalanced
    fun restTemplate() = RestTemplate()

    @Bean
    @Scope("prototype")
    @ConditionalOnMissingBean
    fun feignResilience4jBuilder() =
        Resilience4jFeign.builder(
            FeignDecorators.builder()
                .withCircuitBreaker(CircuitBreakerRegistry.ofDefaults().circuitBreaker("orders"))
                .withFallback(FallbackOrderService())
                .build()
        )
}