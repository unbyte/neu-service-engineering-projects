package me.forself.playground.microservice.service

import me.forself.playground.microservice.model.Country

interface CountryService {
    fun selectByCode(Code: String): Country?

    fun selectByName(Name: String): Country?

    fun findByRegion(Region: String): List<Country>

    fun findByContinent(Continent: String): List<Country>

    fun findAll(): List<Country>
}