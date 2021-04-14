package me.forself.playground.microservice

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/echo")
class EchoController {
    @GetMapping("hello")
    fun hello() = "Hello World"
}