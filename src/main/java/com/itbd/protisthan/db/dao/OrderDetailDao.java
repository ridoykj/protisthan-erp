package com.itbd.protisthan.db.dao;

import com.itbd.protisthan.db.dao.iddao.OrderDetailId;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "order_details", schema = "erp_over", indexes = {
        @Index(name = "id_item_key", columnList = "id_item_key")
})
public class OrderDetailDao {
    @EmbeddedId
    private OrderDetailId id;

    @MapsId("idOrderKey")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_order_key", nullable = false)
    private OrderDao idOrderKey;

    @MapsId("idItemKey")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_item_key", nullable = false)
    private ItemDao idItemKey;

    @NotNull
    @Column(name = "flt_unit_price", nullable = false)
    private Double unitPrice;

    @NotNull
    @Column(name = "ct_quantity", nullable = false)
    private Short quantity;

    @NotNull
    @Column(name = "flt_discount", nullable = false)
    private Double discount;

}