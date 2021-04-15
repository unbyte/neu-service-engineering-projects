package me.forself.playground.microservice.repository

import me.forself.playground.microservice.model.Country
import org.apache.ibatis.annotations.*

@Mapper
interface CountryRepository {
    @Select("select * from `Country` where `Code` = #{Code} LIMIT 1")
    @ResultMap("countryResult")
    fun selectByCode(Code: String): Country?

    @Select("select * from `Country` where `Name`=#{Name} LIMIT 1")
    @ResultMap("countryResult")
    fun selectByName(Name: String): Country?

    @Select("select * from `Country` where `Region`=#{Region}")
    @ResultMap("countryResult")
    fun findByRegion(Region: String): List<Country>

    @Select("select * from `Country` where `Continent`=#{Continent}")
    @ResultMap("countryResult")
    fun findByContinent(Continent: String): List<Country>

    @Select("select * from `Country`")
    @Results(
        id = "countryResult", value = [Result(id = true, column = "Code", property = "Code")]
    )
    fun findAll(): List<Country>
}
