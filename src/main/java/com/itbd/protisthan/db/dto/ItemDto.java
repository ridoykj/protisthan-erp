package com.itbd.protisthan.db.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.itbd.protisthan.db.dao.CategoryDao;
import com.itbd.protisthan.db.dao.ItemDao;
import com.itbd.protisthan.db.dao.ItemGroupDao;
import com.itbd.protisthan.db.dao.UomDao;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * DTO for {@link ItemDao}
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public record ItemDto(
        Long id,
        @NotNull @Size(max = 40) String name,
        @Size(max = 20) String quantityPerUnit,
        @NotNull BigDecimal unitPrice,
        Short unitsInStock,
        Short unitsOnOrder,
        Short reorderLevel,
        Boolean isDiscontinued,
        @NotNull String code,
        String brand,
        String description,
        Boolean isDisabled,
        Boolean isFixedAsset,
        Boolean isPurchaseItem,
        Boolean isSalesItem,
        Boolean isStockItem,
        Long warrantyPeriod,
        @NotNull BigDecimal salePrice,
        BigDecimal serviceCharge,
        BigDecimal supplementaryDuty,
        BigDecimal vat,
        BigDecimal discount,
        BigDecimal valuationRate,
        @NotNull CategoryDao category,
        @NotNull ItemGroupDao itemGroup,
        @NotNull UomDao saleUom) implements Serializable {
    public static ItemDto toDto(ItemDao dao) {
        var itemGroup = dao.getItemGroup();
        itemGroup.setParentItemGroup(null);
        return new ItemDto(
                dao.getId(),
                dao.getName(),
                dao.getQuantityPerUnit(),
                dao.getUnitPrice(),
                dao.getUnitsInStock(),
                dao.getUnitsOnOrder(),
                dao.getReorderLevel(),
                dao.getIsDiscontinued(),
                dao.getCode(),
                dao.getBrand(),
                dao.getDescription(),
                dao.getIsDisabled(),
                dao.getIsFixedAsset(),
                dao.getIsPurchaseItem(),
                dao.getIsSalesItem(),
                dao.getIsStockItem(),
                dao.getWarrantyPeriod(),
                dao.getSalePrice(),
                dao.getServiceCharge(),
                dao.getSupplementaryDuty(),
                dao.getVat(),
                dao.getDiscount(),
                dao.getValuationRate(),
                dao.getCategory(),
                dao.getItemGroup(),
                dao.getSaleUom()
        );

    }

    public static void toEntity(ItemDto dto,
                                ItemDao dao) {
        dao.setName(dto.name());
        dao.setQuantityPerUnit(dto.quantityPerUnit());
        dao.setUnitPrice(dto.unitPrice());
        dao.setUnitsInStock(dto.unitsInStock());
        dao.setUnitsOnOrder(dto.unitsOnOrder());
        dao.setReorderLevel(dto.reorderLevel());
        dao.setIsDiscontinued(dto.isDiscontinued());
        dao.setCode(dto.code());
        dao.setBrand(dto.brand());
        dao.setDescription(dto.description());
        dao.setIsDisabled(dto.isDisabled());
        dao.setIsFixedAsset(dto.isFixedAsset());
        dao.setIsPurchaseItem(dto.isPurchaseItem());
        dao.setIsSalesItem(dto.isSalesItem());
        dao.setIsStockItem(dto.isStockItem());
        dao.setWarrantyPeriod(dto.warrantyPeriod());
        dao.setSalePrice(dto.salePrice());
        dao.setServiceCharge(dto.serviceCharge());
        dao.setSupplementaryDuty(dto.supplementaryDuty());
        dao.setVat(dto.vat());
        dao.setDiscount(dto.discount());
        dao.setValuationRate(dto.valuationRate());
        dao.setCategory(dto.category());
        dao.setItemGroup(dto.itemGroup());
        dao.setSaleUom(dto.saleUom());
    }

    //  dao.setIsDiscontinued(dto.isDiscontinued() != null ? dto.isDiscontinued() : dao.getIsDiscontinued());
}