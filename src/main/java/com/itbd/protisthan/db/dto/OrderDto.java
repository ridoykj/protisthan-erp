package com.itbd.protisthan.db.dto;

import com.itbd.protisthan.db.dao.*;
import jakarta.validation.constraints.NotNull;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * DTO for {@link OrderDao}
 */
public record OrderDto(
        Long id,
        LocalDate orderDate,
        LocalDate requiredDate,
        LocalDate shippedDate,
        Double freight,
        @NotNull CustomerDao customer,
        @NotNull EmployeeDao employee,
        @NotNull ModeOfPaymentDao modeOfPayment,
        Set<OrderDetailDao> orderDetails
) implements Serializable {

    public static OrderDto toDto(OrderDao dao) {
        return new OrderDto(
                dao.getId(),
                dao.getOrderDate(),
                dao.getRequiredDate(),
                dao.getShippedDate(),
                dao.getFreight(),
                dao.getCustomer(),
                dao.getEmployee(),
                dao.getModeOfPayment(),
                dao.getOrderDetails()
        );
    }

    public static void toEntity(OrderDto dto, OrderDao dao) {
//        dao.setId(dto.id());
        dao.setOrderDate(dto.orderDate());
        dao.setRequiredDate(dto.requiredDate());
        dao.setShippedDate(dto.shippedDate());
        dao.setFreight(dto.freight());
        dao.setCustomer(dto.customer());
        dao.setEmployee(dto.employee());
        dao.setModeOfPayment(dto.modeOfPayment());
    }
}