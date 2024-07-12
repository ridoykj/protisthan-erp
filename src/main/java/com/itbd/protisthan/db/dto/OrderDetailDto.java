package com.itbd.protisthan.db.dto;

import com.itbd.protisthan.db.dao.ItemDao;
import com.itbd.protisthan.db.dao.OrderDao;
import com.itbd.protisthan.db.dao.OrderDetailDao;
import com.itbd.protisthan.db.dao.iddao.OrderDetailId;
import jakarta.validation.constraints.NotNull;

import java.io.Serializable;

/**
 * DTO for {@link OrderDetailDao}
 */
public record OrderDetailDto(@NotNull Double unitPrice,
                             @NotNull Short quantity,
                             @NotNull Double discount,
                             OrderDetailId id,
                             OrderDao idOrderKey,
                             ItemDao idItemKey
) implements Serializable {

    public static OrderDetailDto toDto(OrderDetailDao dao) {
        return new OrderDetailDto(
                dao.getUnitPrice(),
                dao.getQuantity(),
                dao.getDiscount(),
                dao.getId(),
                dao.getIdOrderKey(),
                dao.getIdItemKey()
        );
    }

    public static void toEntity(OrderDetailDto dto, OrderDetailDao dao) {
        dao.setUnitPrice(dto.unitPrice());
        dao.setQuantity(dto.quantity());
        dao.setDiscount(dto.discount());
        dao.setId(dto.id());
        dao.setIdOrderKey(dto.idOrderKey());
        dao.setIdItemKey(dto.idItemKey());
    }
}