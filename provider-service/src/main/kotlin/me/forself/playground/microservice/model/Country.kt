package me.forself.playground.microservice.model

import java.io.Serializable

class Country(
    val Code: String,
    val Name: String,
    val Continent: String,
    val Region: String,
    val SurfaceArea: Float,
    val IndepYear: Int?,
    val Population: Long,
    val LifeExpectancy: Float?,
    val GNP: Float?,
    val GNPOld: Float?,
    val LocalName: String,
    val GovernmentForm: String,
    val HeadOfState: String?,
    val Capital: Int?,
    val Code2: String
) : Serializable {
    companion object {
        private const val serialVersionUID = 1L
    }
}
