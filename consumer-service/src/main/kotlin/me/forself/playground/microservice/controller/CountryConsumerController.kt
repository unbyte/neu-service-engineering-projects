package me.forself.playground.microservice.controller

import me.forself.playground.microservice.service.CountryService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController
import javax.ws.rs.QueryParam

@RestController
class CountryConsumerController(
    private val countryService: CountryService
) {
    @GetMapping("/api/countries")
    fun query(
        @QueryParam("code") code: String?,
        @QueryParam("name") name: String?,
        @QueryParam("region") region: String?,
        @QueryParam("continent") continent: String?,
    ) = countryService.query(code, name, region, continent)

    @GetMapping("/api/breakdown")
    fun breakdown() = countryService.breakdown()
}
