package com.itbd.protisthan.db.dao;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.itbd.protisthan.db.dao.iddao.OrderDetailId;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.envers.AuditTable;
import org.hibernate.envers.Audited;
import org.hibernate.envers.NotAudited;

@Getter
@Setter
@Entity
@Table(name = "order_details", schema = "erp_over", indexes = {
        @Index(name = "id_item_key", columnList = "id_item_key")
})
@Audited
@AuditTable(value = "ORDER_DETAIL_AUDIT")
public class OrderDetailDao {
    @EmbeddedId
    private OrderDetailId id;

    @MapsId("idOrderKey")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_order_key", nullable = false)
    @NotAudited
    @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
    @JsonIdentityReference(alwaysAsId = true)
    private OrderDao order;

    @MapsId("idItemKey")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_item_key", nullable = false)
    @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
    @JsonIdentityReference(alwaysAsId = true)
    private ItemDao item;

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