package me.forself.playground.microservice

import org.springframework.cloud.gateway.filter.GatewayFilter
import org.springframework.cloud.gateway.filter.GatewayFilterChain
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory
import org.springframework.core.Ordered
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.web.server.ServerWebExchange
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import java.nio.charset.StandardCharsets


class AuthFilter : GatewayFilter, Ordered {
    override fun filter(exchange: ServerWebExchange, chain: GatewayFilterChain): Mono<Void> {
        return exchange.request.headers.getFirst("Useless-Auth").let {
            if (it != "SECRET") {
                exchange.response.statusCode = HttpStatus.UNAUTHORIZED
                exchange.response.headers.set("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                exchange.response.writeWith(
                    Flux.just(exchange.response.bufferFactory().wrap(
                        """{"code":-1,"msg":"unauthorized"}""".toByteArray(StandardCharsets.UTF_8)
                    ))
                )
            } else {
                chain.filter(exchange)
            }
        }
    }

    override fun getOrder() = 0
}

class AuthGatewayFilterFactory : AbstractGatewayFilterFactory<AuthGatewayFilterFactory.Config>(Config::class.java) {
    override fun apply(config: Config) = AuthFilter()
    class Config
}