package me.forself.playground.microservice.controller

import me.forself.playground.microservice.model.OrderStatus
import me.forself.playground.microservice.service.OrderInsertOptions
import me.forself.playground.microservice.service.OrderQueryOptions
import me.forself.playground.microservice.service.OrderService
import me.forself.playground.microservice.service.OrderUpdateOptions
import org.springframework.cloud.client.discovery.DiscoveryClient
import org.springframework.web.bind.annotation.*
import javax.ws.rs.QueryParam

@RestController
class OrderController(
    private val orderService: OrderService,
    val client: DiscoveryClient
) {
    @GetMapping("/orders")
    fun query(
        @QueryParam("customer") customer: Long?,
        @QueryParam("supplier") supplier: Long?,
        @QueryParam("product") product: Long?,
        @QueryParam("status") status: String?,
    ) = orderService.select(OrderQueryOptions(customer, supplier, product, OrderStatus.fromLiteral(status)))

    @GetMapping("/orders/{order_no}")
    fun query(
        @PathVariable("order_no") orderNo: String,
    ) = orderService.get(orderNo)

    @PostMapping("/orders")
    fun create(
        @RequestBody order: OrderInsertOptions,
    ) = orderService.create(order)

    @PostMapping("/orders/{order_no}")
    fun update(
        @PathVariable("order_no") orderNo: String,
        @RequestBody order: OrderUpdateRequest,
    ) = orderService.update(
        OrderUpdateOptions(
            orderNo,
            OrderStatus.fromLiteral(order.orderStatus),
            order.paymentNo,
            order.paymentTime,
            order.deliveryTime
        )
    )

    @GetMapping("/discovery")
    fun discovery() = client
}

class OrderUpdateRequest(
    val orderStatus: String?,
    val paymentNo: String?,
    val paymentTime: Long?,
    val deliveryTime: Long?,
)