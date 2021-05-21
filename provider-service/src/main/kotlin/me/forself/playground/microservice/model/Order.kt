package me.forself.playground.microservice.model

import com.fasterxml.jackson.annotation.JsonFormat
import com.fasterxml.jackson.core.JsonGenerator
import com.fasterxml.jackson.databind.SerializerProvider
import com.fasterxml.jackson.databind.annotation.JsonSerialize
import com.fasterxml.jackson.databind.ser.std.StdSerializer
import java.io.Serializable
import java.math.BigDecimal
import java.sql.Timestamp


class Order(
    val id: Long = 0,
    val orderNo: String?,
    @JsonSerialize(using = StatusSerializer::class)
    val orderStatus: Int = OrderStatus.UNPAID,
    val customerID: Long?,
    val supplierID: Long?,
    val productID: Long?,
    val productCount: Int?,
    val productAmount: BigDecimal?,
    val address: String?,
    val freight: BigDecimal?,
    val paymentNo: String?,
    val paymentAmount: BigDecimal?,
    @JsonFormat(shape = JsonFormat.Shape.NUMBER)
    val paymentTime: Timestamp?,
    @JsonFormat(shape = JsonFormat.Shape.NUMBER)
    val deliveryTime: Timestamp?,
    @JsonFormat(shape = JsonFormat.Shape.NUMBER)
    val createdTime: Timestamp?,
    @JsonFormat(shape = JsonFormat.Shape.NUMBER)
    val updatedTime: Timestamp?,
) : Serializable {
    companion object {
        private const val serialVersionUID = 1L
    }
}


object OrderStatus {
    const val UNPAID = 0x00
    const val PAID = 0x01
    const val DELIVERED = 0x02
    const val DONE = 0x03
    const val RETURN_REQUESTED = 0x11
    const val RETURNING = 0x12
    const val RETURNED = 0x13
    const val CANCELED = 0xFF

    fun fromLiteral(status: String?) = when (status) {
        "unpaid" -> UNPAID
        "paid" -> PAID
        "delivered" -> DELIVERED
        "done" -> DONE
        "return_requested" -> RETURN_REQUESTED
        "returning" -> RETURNING
        "returned" -> RETURNED
        "canceled" -> CANCELED
        else -> null
    }

    fun toLiteral(status: Int) = when (status) {
        UNPAID -> "unpaid"
        PAID -> "paid"
        DELIVERED -> "delivered"
        DONE -> "done"
        RETURN_REQUESTED -> "return_requested"
        RETURNING -> "returning"
        RETURNED -> "returned"
        CANCELED -> "canceled"
        else -> "unknown"
    }

}

class StatusSerializer(t: Class<Int>) : StdSerializer<Int>(t) {
    override fun serialize(value: Int, gen: JsonGenerator, pro: SerializerProvider) {
        gen.writeString(OrderStatus.toLiteral(value))
    }
}