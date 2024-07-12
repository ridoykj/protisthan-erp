package com.itbd.protisthan.db.dao;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
@Entity
@Table(name = "t_item_group", schema = "erp_over", indexes = {
        @Index(name = "id_parent_item_group_key", columnList = "id_parent_item_group_key")
})
public class ItemGroupDao {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_item_group_key", nullable = false)
    private Long id;

    @Size(max = 140)
    @Column(name = "tx_name", length = 140)
    private String name;

    @Column(name = "dtt_creation")
    private Instant creation;

    @Column(name = "ct_idx")
    private Integer idx;

    @Lob
    @Column(name = "tx_image")
    private String image;

    @Column(name = "is_group")
    private Boolean isGroup;

    @Column(name = "dtt_modified")
    private Instant modified;

    @Size(max = 140)
    @Column(name = "tx_modified_by", length = 140)
    private String modifiedBy;

    @ManyToOne
    @JoinColumn(name = "id_parent_item_group_key", referencedColumnName = "id_item_group_key")
    private ItemGroupDao parentItemGroup;

}