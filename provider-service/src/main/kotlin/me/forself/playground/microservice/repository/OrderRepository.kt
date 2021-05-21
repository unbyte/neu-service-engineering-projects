package me.forself.playground.microservice.repository

import me.forself.playground.microservice.model.Order
import org.apache.ibatis.annotations.*
import java.math.BigDecimal

@Mapper
interface OrderRepository {
    @Select("select * from `order` where `order_no` = #{orderNo} LIMIT 1")
    @ResultMap("orderResult")
    fun getByNo(orderNo: String): Order?

    @Select("select * from `order` where `customer_id` = #{customerID}")
    @ResultMap("orderResult")
    fun selectByCustomer(customerID: Long): List<Order>

    @Select("select * from `order` where `supplier_id` = #{supplierID}")
    @ResultMap("orderResult")
    fun selectBySupplier(supplierID: Long): List<Order>

    @Select("select * from `order` where `product_id` = #{productID}")
    @ResultMap("orderResult")
    fun selectByProduct(productID: Long): List<Order>

    @Select("select * from `order` where `customer_id` = #{customerID} and `order_status` = #{status}")
    @ResultMap("orderResult")
    fun selectByCustomerStatus(customerID: Long, status: Int): List<Order>

    @Select("select * from `order` where `supplier_id` = #{supplierID} and `order_status` = #{status}")
    @ResultMap("orderResult")
    fun selectBySupplierStatus(supplierID: Long, status: Int): List<Order>

    @Select("select * from `order` where `product_id` = #{productID} and `order_status` = #{status}")
    @ResultMap("orderResult")
    fun selectByProductStatus(productID: Long, status: Int): List<Order>

    @Select("select * from `order` where `customer_id` = #{customerID} and `supplier_id` = #{supplierID}")
    @ResultMap("orderResult")
    fun selectByCustomerSupplier(customerID: Long, supplierID: Long): List<Order>

    @Select("select * from `order` where `supplier_id` = #{customerID} and `product_id` = #{productID}")
    @ResultMap("orderResult")
    fun selectByCustomerProduct(customerID: Long, productID: Long): List<Order>

    @Select("select * from `order` where `customer_id` = #{customerID} and `product_id` = #{productID} and `order_status` = #{status}")
    @ResultMap("orderResult")
    fun selectByCustomerProductStatus(customerID: Long, productID: Long, status: Int): List<Order>

    @Select("select * from `order` where `customer_id` = #{customerID} and `supplier_id` = #{supplierID} and `order_status` = #{status}")
    @ResultMap("orderResult")
    fun selectByCustomerSupplierStatus(customerID: Long, supplierID: Long, status: Int): List<Order>

    @Select("select * from `order`")
    @Results(
        id = "orderResult", value = [
            Result(id = true, column = "id", property = "id"),
            Result(column = "customer_id", property = "customerID"),
            Result(column = "supplier_id", property = "supplierID"),
            Result(column = "product_id", property = "productID"),
        ]
    )
    fun selectAll(): List<Order>


    @Insert(
        "insert into `order`" +
                "(`order_no`,`customer_id`,`supplier_id`,`product_id`,`product_count`,`product_amount`,`address`,`freight`,`payment_amount`) " +
                "values (#{orderNo},#{customerID},#{supplierID},#{productID},#{productCount},#{productAmount},#{address},#{freight},#{paymentAmount})"
    )
    fun create(order: OrderInsertFields): Long

    @Update("update `order` set `order_status`=#{status} where `order_no`=#{orderNo}")
    fun updateStatus(orderNo: String, status: Int): Int

    @Update(
        "update `order` set `payment_no`=#{paymentNo}, `payment_time`=#{paymentTime}, `order_status`=1 " +
                "where `order_no`=#{orderNo}"
    )
    fun updatePayment(orderNo: String, paymentNo: String, paymentTime: Long): Int

    @Update("update `order` set `delivery_time`=#{deliveryTime}, `order_status`=2 where `order_no`=#{orderNo}")
    fun updateDelivery(orderNo: String, deliveryTime: Long): Int
}

class OrderInsertFields(
    val orderNo: String,
    val customerID: Long,
    val supplierID: Long,
    val productID: Long,
    val productCount: Int,
    val productAmount: BigDecimal,
    val address: String,
    val freight: BigDecimal,
    val paymentAmount: BigDecimal,
)

