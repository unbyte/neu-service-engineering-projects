package me.forself.playground.microservice.controller

import me.forself.playground.microservice.model.Country
import me.forself.playground.microservice.service.CountryService
import org.springframework.cloud.client.discovery.DiscoveryClient
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController
import javax.ws.rs.QueryParam

@RestController
class CountryController(
    private val countryService: CountryService,
    val client: DiscoveryClient
) {
    @GetMapping("/countries")
    fun query(
        @QueryParam("code") code: String?,
        @QueryParam("name") name: String?,
        @QueryParam("region") region: String?,
        @QueryParam("continent") continent: String?,
    ): List<Country> = when {
        code != null -> listOfNotNull(countryService.selectByCode(code))
        name != null -> listOfNotNull(countryService.selectByName(name))
        continent != null -> countryService.findByContinent(continent)
        region != null -> countryService.findByRegion(region)
        else -> countryService.findAll()
    }

    @GetMapping("/breakdown")
    fun breakdown(): String {
        throw Exception("breakdown!")
    }

    @GetMapping("/discovery")
    fun discovery() = client
}
