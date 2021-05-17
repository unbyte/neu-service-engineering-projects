package me.forself.playground.microservice

import org.springframework.web.bind.annotation.*
import reactor.core.publisher.Mono

@RestController
@RequestMapping("/fallback")
class FallbackController {
    @PostMapping("/ok")
    fun fallback() = Mono.just("ok")
}