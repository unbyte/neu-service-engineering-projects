package me.forself.playground.microservice.service

import me.forself.playground.microservice.model.Order
import java.math.BigDecimal

interface OrderService {
    fun select(option: OrderQueryOptions): List<Order>
    fun get(orderNo: String): Order?

    fun create(order: OrderInsertOptions): Long

    fun update(order: OrderUpdateOptions): Int
}

class OrderQueryOptions(
    val customerID: Long?,
    val supplierID: Long?,
    val productID: Long?,
    val status: Int?,
)

class OrderInsertOptions(
    val customerID: Long,
    val supplierID: Long,
    val productID: Long,
    val productCount: Int,
    val productAmount: BigDecimal,
    val address: String,
    val freight: BigDecimal,
    val paymentAmount: BigDecimal,
)

class OrderUpdateOptions(
    val orderNo: String,
    val orderStatus: Int?,
    val paymentNo: String?,
    val paymentTime: Long?,
    val deliveryTime: Long?,
)

