package me.forself.playground.microservice.controller

import com.fasterxml.jackson.annotation.JsonProperty
import me.forself.playground.microservice.model.Order
import me.forself.playground.microservice.model.withResponse
import me.forself.playground.microservice.service.OrderInsertOptions
import me.forself.playground.microservice.service.OrderService
import me.forself.playground.microservice.service.OrderUpdateOptions
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import javax.servlet.http.HttpServletResponse
import javax.ws.rs.QueryParam

@RestController
class OrderConsumerController(
    private val orderService: OrderService
) {
    @GetMapping("/api/orders")
    fun query(
        @QueryParam("customer") customer: Long?,
        @QueryParam("supplier") supplier: Long?,
        @QueryParam("product") product: Long?,
        @QueryParam("status") status: String?,
    ) = withResponse {
        orderService.query(customer, supplier, product, status)
    }

    @GetMapping("/api/orders/{order_no}")
    fun query(
        @PathVariable("order_no") orderNo: String,
        response: HttpServletResponse
    ) = withResponse {
        orderService.get(orderNo).also {
            if (it === null) response.status = HttpStatus.NOT_FOUND.value()
        }
    }

    @PostMapping("/api/orders")
    fun create(
        @RequestBody order: OrderInsertOptions,
    ) = withResponse {
        // TODO: validate all foreign keys existed
        orderService.create(order).let {
            if (it == 0) "failed to create"
            else "created successfully"
        }
    }


    class PaymentRequest(
        val paymentNo: String,
    )

    @PostMapping("/api/orders/{order_no}/payment")
    fun payment(
        @PathVariable("order_no") orderNo: String,
        @RequestBody payload: PaymentRequest,
        response: HttpServletResponse
    ) = withResponse {
        withOrder(orderNo, response, { order ->
            if (order.orderStatus !== "unpaid") "already paid"
            else null
        }) {
            // TODO validate payment status from third party cashier service

            // FIXME get payment time from third party cashier service
            val paymentTime = System.currentTimeMillis()

            orderService.update(
                orderNo,
                OrderUpdateOptions(orderStatus = "paid", paymentNo = payload.paymentNo, paymentTime = paymentTime)
            ).let {
                if (it == 0) "failed to update payment status"
                else "updated successfully"
            }
        }
    }


    @PostMapping("/api/orders/{order_no}/delivery")
    fun delivery(
        @PathVariable("order_no") orderNo: String,
        response: HttpServletResponse
    ) = withResponse {
        withOrder(orderNo, response, { order ->
            if (order.orderStatus !== "paid") "invalid"
            else null
        }) {
            orderService.update(
                orderNo,
                OrderUpdateOptions(orderStatus = "delivered", deliveryTime = System.currentTimeMillis())
            ).let {
                if (it == 0) "failed to update delivery status"
                else "updated successfully"
            }
        }
    }

    enum class ActionType(val status: String, val validator: (String) -> Boolean) {
        @JsonProperty("done")
        DONE_ORDER("done", { it === "delivered" }),

        @JsonProperty("request_return")
        REQUEST_RETURN("return_requested",
            { it !== "return_requested" && it !== "returning" && it !== "returned" && it !== "canceled" }),

        @JsonProperty("start_return")
        START_RETURN("returning", { it === "return_requested" }),

        @JsonProperty("done_return")
        DONE_RETURN("returned", { it === "returning" }),

        @JsonProperty("cancel")
        CANCEL("canceled", { it === "unpaid" }),
    }

    class StatusRequest(
        val type: ActionType,
    )

    @PostMapping("/api/orders/{order_no}")
    fun delivery(
        @PathVariable("order_no") orderNo: String,
        @RequestBody payload: StatusRequest,
        response: HttpServletResponse
    ) = withResponse {
        withOrder(orderNo, response, { order ->
            if (!payload.type.validator(order.orderStatus)) "action not valid"
            else null
        }) {
            orderService.update(
                orderNo,
                OrderUpdateOptions(orderStatus = payload.type.status)
            ).let {
                if (it == 0) "failed to update status"
                else "updated successfully"
            }
        }
    }


    fun withOrder(
        orderNo: String,
        response: HttpServletResponse,
        guard: (Order) -> String?,
        fn: (Order) -> Any?
    ) {
        orderService.get(orderNo).also {
            if (it === null) {
                response.status = HttpStatus.BAD_REQUEST.value()
                throw Exception("order not existed")
            }
            val res = guard(it)
            if (res !== null) {
                response.status = HttpStatus.BAD_REQUEST.value()
                throw Exception(res)
            }
        }?.let(fn)
    }

    fun withOrder(orderNo: String, response: HttpServletResponse, fn: (Order) -> Any?) {
        orderService.get(orderNo).also {
            if (it === null) {
                response.status = HttpStatus.BAD_REQUEST.value()
                throw Exception("order not existed")
            }
        }?.let(fn)
    }
}
