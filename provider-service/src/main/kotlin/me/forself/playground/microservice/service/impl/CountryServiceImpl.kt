package me.forself.playground.microservice.service.impl

import me.forself.playground.microservice.repository.CountryRepository
import me.forself.playground.microservice.service.CountryService
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class CountryServiceImpl(private val countryRepository: CountryRepository) : CountryService {

    override fun selectByCode(Code: String) = countryRepository.selectByCode(Code)

    override fun selectByName(Name: String) = countryRepository.selectByName(Name)

    override fun findByRegion(Region: String) = countryRepository.findByRegion(Region)

    override fun findByContinent(Continent: String) = countryRepository.findByContinent(Continent)

    override fun findAll() = countryRepository.findAll()
}