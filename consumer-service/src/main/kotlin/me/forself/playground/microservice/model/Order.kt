package me.forself.playground.microservice.model

import java.io.Serializable
import java.math.BigDecimal

class Order(
    val id: Long = 0,
    val orderNo: String,
    val orderStatus: String,
    val customerID: Long,
    val supplierID: Long,
    val productID: Long,
    val productCount: Int,
    val productAmount: BigDecimal,
    val address: String,
    val freight: BigDecimal,
    val paymentNo: String?,
    val paymentAmount: BigDecimal,
    val paymentTime: Long?,
    val deliveryTime: Long?,
    val createdTime: Long,
    val updatedTime: Long,
) : Serializable {
    companion object {
        private const val serialVersionUID = 1L
    }
}