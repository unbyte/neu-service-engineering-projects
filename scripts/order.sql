CREATE TABLE IF NOT EXISTS `order`
(
    `id`             INT(10) UNSIGNED                       NOT NULL AUTO_INCREMENT,
    `order_no`       VARCHAR(32) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '订单编号',
    `order_status`   TINYINT(4)                             NOT NULL DEFAULT 0 COMMENT '订单状态',
    `customer_id`    INT(11)                                NOT NULL COMMENT '客户编号',
    `supplier_id`    INT(11)                                NOT NULL COMMENT '商户编号',
    `product_id`     INT(11)                                NOT NULL COMMENT '商品编号',
    `product_count`  INT(11)                                NOT NULL DEFAULT 0 COMMENT '商品数量',
    `product_amount` DECIMAL(12, 4)                         NOT NULL COMMENT '商品总价',
    `address`        VARCHAR(100)                           NOT NULL COMMENT '收货地址',
    `freight`        DECIMAL(12, 4)                         NOT NULL COMMENT '运费金额',
    `payment_amount` DECIMAL(12, 4)                         NOT NULL COMMENT '实际付款金额',
    `payment_no`     VARCHAR(32) COLLATE utf8mb4_unicode_ci          DEFAULT NULL COMMENT '订单支付编号',

    `payment_time`   TIMESTAMP                                       DEFAULT NULL COMMENT '付款时间',
    `delivery_time`  TIMESTAMP                                       DEFAULT NULL COMMENT '发货时间',

    `created_time`   TIMESTAMP                              NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `updated_time`   TIMESTAMP                              NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_order_no` (`order_no`),
    KEY `idx_customer_status` (`customer_id`, `order_status`),
    KEY `idx_customer_supplier_status` (`customer_id`, `supplier_id`, `order_status`),
    KEY `idx_customer_product_status` (`customer_id`, `product_id`, `order_status`),
    KEY `idx_supplier_status` (`supplier_id`, `order_status`),
    KEY `idx_product_status` (`product_id`, `order_status`)
) ENGINE = InnoDB;