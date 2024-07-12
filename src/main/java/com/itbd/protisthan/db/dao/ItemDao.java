package com.itbd.protisthan.db.dao;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

import java.math.BigDecimal;

@Getter
@Setter
@Entity
@Table(name = "items", schema = "erp_over", indexes = {
        @Index(name = "id_category_key", columnList = "id_category_key")
})
public class ItemDao {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_item_key", nullable = false)
    private Long id;

    @Size(max = 40)
    @NotNull
    @Column(name = "tx_item_name", nullable = false, length = 40)
    private String name;

    @Size(max = 64)
    @Column(name = "tx_code", length = 64)
    private String code;

    @Size(max = 20)
    @Column(name = "tx_quantity_per_unit", length = 20)
    private String quantityPerUnit;

    @Size(max = 64)
    @Column(name = "tx_brand", length = 64)
    private String brand;

    @Lob
    @Column(name = "tx_description")
    private String description;

    @ColumnDefault("0.0")
    @Column(name = "flt_unit_price", nullable = false)
    private BigDecimal unitPrice;

    @Column(name = "ct_units_in_stock")
    private Short unitsInStock;

    @Column(name = "ct_units_on_order")
    private Short unitsOnOrder;

    @Column(name = "ct_reorder_level")
    private Short reorderLevel;

    //    @NotNull
    @Column(name = "is_discontinued", nullable = false)
    private Boolean isDiscontinued = false;

    @Column(name = "is_disabled", nullable = false)
    private Boolean isDisabled = false;

    @Column(name = "is_fixed_asset", nullable = false)
    private Boolean isFixedAsset = false;


    @Column(name = "is_purchase_item", nullable = false)
    private Boolean isPurchaseItem = false;

    @Column(name = "is_sales_item", nullable = false)
    private Boolean isSalesItem = false;

    @Column(name = "is_stock_item", nullable = false)
    private Boolean isStockItem = false;

    @Column(name = "ct_warranty_period")
    private Long warrantyPeriod;

    @ColumnDefault("0.0")
    @Column(name = "flt_sale_price", nullable = false)
    private BigDecimal salePrice;

    @ColumnDefault("0.0")
    @Column(name = "flt_service_charge", nullable = false)
    private BigDecimal serviceCharge;

    @ColumnDefault("0.0")
    @Column(name = "flt_supplementary_duty", nullable = false)
    private BigDecimal supplementaryDuty;

    @ColumnDefault("0.0")
    @Column(name = "flt_vat", nullable = false)
    private BigDecimal vat;

    @ColumnDefault("0.0")
    @Column(name = "flt_discount", nullable = false)
    private BigDecimal discount;

    @ColumnDefault("0.0")
    @Column(name = "flt_valuation_rate", nullable = false)
    private BigDecimal valuationRate;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "id_category_key", nullable = false)
    private CategoryDao category;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "id_item_group_key", nullable = false)
    private ItemGroupDao itemGroup;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "tx_uom_key", nullable = false)
    private UomDao saleUom;
}