package me.forself.playground.microservice

import org.springframework.cloud.gateway.filter.GatewayFilter
import org.springframework.cloud.gateway.filter.GatewayFilterChain
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory
import org.springframework.core.Ordered
import org.springframework.http.HttpStatus
import org.springframework.web.server.ServerWebExchange
import reactor.core.publisher.Mono


class AuthFilter : GatewayFilter, Ordered {
    override fun filter(exchange: ServerWebExchange, chain: GatewayFilterChain): Mono<Void> {
        return if (!exchange.request.queryParams.containsKey("token")) {
            exchange.response.statusCode = HttpStatus.UNAUTHORIZED
            Mono.empty()
        } else {
            chain.filter(exchange)
        }
    }

    override fun getOrder() = 0
}

class AuthGatewayFilterFactory : AbstractGatewayFilterFactory<AuthGatewayFilterFactory.Config>(Config::class.java) {
    override fun apply(config: Config) = AuthFilter()
    class Config
}