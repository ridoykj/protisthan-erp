package com.itbd.protisthan.db.dao;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

import java.time.Instant;

@Getter
@Setter
@Entity
@Table(name = "t_stock_entry_type", schema = "erp_over")
public class StockEntryTypeDao {
    @Id
    @Size(max = 140)
    @Column(name = "tx_stock_entry_type_key", nullable = false, length = 140)
    private String id;

    @ColumnDefault("0")
    @Column(name = "is_add_to_transit")
    private Boolean isAddToTransit = false;

    @Column(name = "dtt_creation")
    private Instant creation;

    @Column(name = "ct_idx")
    private Integer idx;

    @Column(name = "dtt_modified")
    private Instant modified;

    @Size(max = 140)
    @Column(name = "tx_modified_by", length = 140)
    private String modifiedBy;

    @Size(max = 140)
    @Column(name = "tx_owner", length = 140)
    private String owner;

}