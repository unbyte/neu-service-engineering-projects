package me.forself.playground.microservice.service

import me.forself.playground.microservice.model.Order
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.stereotype.Service
import org.springframework.web.bind.annotation.*
import java.math.BigDecimal

@FeignClient("provider-service")
@Service
interface OrderService {
    @GetMapping("/orders")
    fun query(
        @RequestParam("customer") customer: Long?,
        @RequestParam("supplier") supplier: Long?,
        @RequestParam("product") product: Long?,
        @RequestParam("status") status: String?,
    ): List<Order>

    @GetMapping("/orders/{order_no}")
    fun get(
        @PathVariable("order_no") orderNo: String
    ): Order?

    @PostMapping("/orders")
    fun create(
        @RequestBody order: OrderInsertOptions,
    ): Int

    @PostMapping("/orders/{order_no}")
    fun update(
        @PathVariable("order_no") orderNo: String,
        @RequestBody order: OrderUpdateOptions,
    ): Int

}

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
    val orderStatus: String? = null,
    val paymentNo: String? = null,
    val paymentTime: Long? = null,
    val deliveryTime: Long? = null,
)


class FallbackOrderService : OrderService {
    override fun query(customer: Long?, supplier: Long?, product: Long?, status: String?): List<Order> = ArrayList()

    override fun get(orderNo: String): Order? = null

    override fun create(order: OrderInsertOptions) = 0

    override fun update(orderNo: String, order: OrderUpdateOptions) = 0
}
