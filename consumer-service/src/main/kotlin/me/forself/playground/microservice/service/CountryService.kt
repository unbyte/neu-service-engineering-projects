package me.forself.playground.microservice.service

import org.springframework.cloud.openfeign.FeignClient
import org.springframework.stereotype.Component
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

    @GetMapping("/breakdown")
    fun breakdown(): Any
}

@Component
class FallbackCountryService : CountryService {
    override fun query(code: String?, name: String?, region: String?, continent: String?) =
        "fallback for query [code=${code} name=${name} region=${region} continent=${continent}]"

    override fun breakdown() = "breakdown!!!"
}
