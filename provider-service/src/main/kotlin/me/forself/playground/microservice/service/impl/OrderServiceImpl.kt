package me.forself.playground.microservice.service.impl

import me.forself.playground.microservice.repository.OrderInsertFields
import me.forself.playground.microservice.repository.OrderRepository
import me.forself.playground.microservice.service.OrderInsertOptions
import me.forself.playground.microservice.service.OrderQueryOptions
import me.forself.playground.microservice.service.OrderService
import me.forself.playground.microservice.service.OrderUpdateOptions
import me.forself.playground.microservice.utils.generateNowTimestamp
import me.forself.playground.microservice.utils.generateUUID
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class OrderServiceImpl(private val orderRepository: OrderRepository) : OrderService {
    override fun select(option: OrderQueryOptions) = when {
        option.status !== null -> when {
            option.customerID !== null -> when {
                option.productID !== null ->
                    orderRepository.selectByCustomerProductStatus(option.customerID, option.productID, option.status)
                option.supplierID !== null ->
                    orderRepository.selectByCustomerSupplierStatus(option.customerID, option.supplierID, option.status)
                else -> orderRepository.selectByCustomerStatus(option.customerID, option.status)
            }
            option.supplierID !== null -> orderRepository.selectBySupplierStatus(option.supplierID, option.status)
            option.productID !== null -> orderRepository.selectByProductStatus(option.productID, option.status)
            else -> ArrayList()
        }
        option.customerID !== null -> when {
            option.productID !== null -> orderRepository.selectByCustomerProduct(option.customerID, option.productID)
            option.supplierID !== null -> orderRepository.selectByCustomerSupplier(option.customerID, option.supplierID)
            else -> orderRepository.selectByCustomer(option.customerID)
        }
        option.supplierID !== null -> orderRepository.selectBySupplier(option.supplierID)
        option.productID !== null -> orderRepository.selectByProduct(option.productID)
        else -> orderRepository.selectAll()
    }

    override fun get(orderNo: String) = orderRepository.getByNo(orderNo)

    override fun create(order: OrderInsertOptions) = orderRepository.create(
        OrderInsertFields(
            orderNo = generateUUID(),
            customerID = order.customerID,
            supplierID = order.supplierID,
            productID = order.productID,
            productCount = order.productCount,
            productAmount = order.productAmount,
            address = order.address,
            freight = order.freight,
            paymentAmount = order.paymentAmount
        )
    )

    override fun update(order: OrderUpdateOptions) = when {
        order.paymentNo !== null -> orderRepository.updatePayment(
            order.orderNo,
            order.paymentNo,
            order.paymentTime ?: generateNowTimestamp()
        )
        order.deliveryTime !== null -> orderRepository.updateDelivery(order.orderNo, order.deliveryTime)
        order.orderStatus !== null -> orderRepository.updateStatus(order.orderNo, order.orderStatus)
        else -> 0
    }

}