package com.itbd.protisthan.db.dao;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "categories", schema = "erp_over")
public class CategoryDao {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_category_key", nullable = false)
    private Long id;

    @Size(max = 15)
    @NotNull
    @Column(name = "tx_category_name", nullable = false, length = 15)
    private String name;

    @Lob
    @Column(name = "tx_picture")
    private String picture;

    @Lob
    @Column(name = "tx_description")
    private String description;
}