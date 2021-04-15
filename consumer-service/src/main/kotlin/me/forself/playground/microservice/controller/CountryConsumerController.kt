package me.forself.playground.microservice.controller

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.client.RestTemplate
import org.springframework.web.client.getForObject
import javax.servlet.http.HttpServletRequest

@RestController
class CountryConsumerController(
    private val restTemplate: RestTemplate,
) {
    @GetMapping("/api/countries")
    fun query(
        request: HttpServletRequest,
    ) = restTemplate.getForObject<Any>(
        "http://provider-service/countries?" + request.queryString
    )
}
