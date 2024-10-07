package com.itbd.protisthan.db.dto.redis;

import com.itbd.protisthan.db.dao.OrderDetailDao;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Set;

public record SellQueueDto(
        Long employeeId,
        Long customerId,
        Long paymentMode,
        BigDecimal totalItem,
        BigDecimal invDiscount,
        BigDecimal invService,
        BigDecimal totalCost,
        Date orderDate,
        Date queueDate,
        Set<OrderDetailDao> orderDetails
) {


public SellQueueDto toModify(){
    return new SellQueueDto(
            this.employeeId,
            this.customerId,
            this.paymentMode,
            this.totalItem,
            this.invDiscount,
            this.invService,
            this.totalCost,
            this.orderDate,
            new Date(),
            this.orderDetails
            );
}
}
