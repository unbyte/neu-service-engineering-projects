package me.forself.playground.microservice.service

import org.springframework.cloud.openfeign.FeignClient
import org.springframework.stereotype.Service
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam

@FeignClient("provider-service")
@Service
interface CountryService {

    @GetMapping("/countries")
    fun query(
        @RequestParam("code") code: String?,
        @RequestParam("name") name: String?,
        @RequestParam("region") region: String?,
        @RequestParam("continent") continent: String?,
    ): Any
}
