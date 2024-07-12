package com.itbd.protisthan.db.dao;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.Instant;

@Getter
@Setter
@Entity
@Table(name = "t_stock_entry_detail", schema = "erp_over")
public class StockEntryDetailDao {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_stock_entry_detail_key", nullable = false)
    private Long id;

    @Size(max = 140)
    @Column(name = "tx_name", length = 140)
    private String name;

    @Column(name = "flt_actual_qty", precision = 21, scale = 9)
    private BigDecimal actualQty;

    @Column(name = "flt_additional_cost", precision = 21, scale = 9)
    private BigDecimal additionalCost;

    @Size(max = 140)
    @Column(name = "tx_against_stock_entry", length = 140)
    private String againstStockEntry;

    @Column(name = "is_allow_alternative_item")
    private Boolean isAllowAlternativeItem;

    @Column(name = "is_allow_zero_valuation_rate")
    private Boolean isAllowZeroValuationRate;

    @Column(name = "flt_amount", precision = 21, scale = 9)
    private BigDecimal amount;

    @Size(max = 140)
    @Column(name = "tx_barcode", length = 140)
    private String barcode;

    @Column(name = "flt_basic_amount", precision = 21, scale = 9)
    private BigDecimal basicAmount;

    @Column(name = "flt_basic_rate", precision = 21, scale = 9)
    private BigDecimal basicRate;

    @Size(max = 140)
    @Column(name = "tx_batch_no", length = 140)
    private String batchNo;

    @Size(max = 140)
    @Column(name = "tx_bom_no", length = 140)
    private String bomNo;

    @Size(max = 140)
    @Column(name = "tx_cost_center", length = 140)
    private String costCenter;

    @Column(name = "dtt_creation")
    private Instant creation;

    @Lob
    @Column(name = "tx_description")
    private String description;

    @Size(max = 140)
    @Column(name = "tx_expense_account", length = 140)
    private String expenseAccount;

    @Column(name = "is_has_item_scanned")
    private Boolean isHasItemScanned;

    @Column(name = "ct_idx")
    private Integer idx;

    @Lob
    @Column(name = "tx_image")
    private String image;

    @Column(name = "is_finished_item")
    private Boolean isFinishedItem;

    @Column(name = "is_scrap_item")
    private Boolean isScrapItem;

    @Size(max = 140)
    @Column(name = "tx_job_card_item", length = 140)
    private String jobCardItem;

    @Size(max = 140)
    @Column(name = "tx_material_request", length = 140)
    private String materialRequest;

    @Size(max = 140)
    @Column(name = "tx_material_request_item", length = 140)
    private String materialRequestItem;

    @Column(name = "dtt_modified")
    private Instant modified;

    @Size(max = 140)
    @Column(name = "tx_modified_by", length = 140)
    private String modifiedBy;

    @Size(max = 140)
    @Column(name = "tx_original_item", length = 140)
    private String originalItem;

    @Size(max = 140)
    @Column(name = "tx_po_detail", length = 140)
    private String poDetail;

    @Size(max = 140)
    @Column(name = "tx_project", length = 140)
    private String project;

    @Size(max = 140)
    @Column(name = "tx_putaway_rule", length = 140)
    private String putawayRule;

    @Column(name = "flt_qty", precision = 21, scale = 9)
    private BigDecimal qty;

    @Size(max = 140)
    @Column(name = "tx_quality_inspection", length = 140)
    private String qualityInspection;

    @Size(max = 140)
    @Column(name = "tx_reference_purchase_receipt", length = 140)
    private String referencePurchaseReceipt;

    @Column(name = "is_retain_sample")
    private Boolean isRetainSample;

    @Column(name = "ct_sample_quantity")
    private Integer sampleQuantity;

    @Size(max = 140)
    @Column(name = "tx_sco_rm_detail", length = 140)
    private String scoRmDetail;

    @Size(max = 140)
    @Column(name = "tx_serial_and_batch_bundle", length = 140)
    private String serialAndBatchBundle;

    @Lob
    @Column(name = "tx_serial_no")
    private String serialNo;

    @Column(name = "is_set_basic_rate_manually")
    private Boolean isSetBasicRateManually;

    @Size(max = 140)
    @Column(name = "tx_ste_detail", length = 140)
    private String steDetail;

    @Size(max = 140)
    @Column(name = "tx_stock_uom", length = 140)
    private String stockUom;

    @Size(max = 140)
    @Column(name = "tx_subcontracted_item", length = 140)
    private String subcontractedItem;

    @Size(max = 140)
    @Column(name = "tx_t_warehouse", length = 140)
    private String tWarehouse;

    @Column(name = "flt_transfer_qty", precision = 21, scale = 9)
    private BigDecimal transferQty;

    @Column(name = "flt_transferred_qty", precision = 21, scale = 9)
    private BigDecimal transferredQty;

//    @Size(max = 140)
//    @Column(name = "tx_uom", length = 140)
//    private String uom;

    @Column(name = "is_use_serial_batch_fields")
    private Boolean isUseSerialBatchFields;

    @Column(name = "flt_valuation_rate", precision = 21, scale = 9)
    private BigDecimal valuationRate;

    @Size(max = 256)
    @Column(name = "tx_item_code", length = 256)
    private String itemCode;

//    @NotNull
    @ManyToOne
    @JoinColumn(name = "id_stock_entry_key", nullable = false)
    private StockEntryDao stockEntry;

//    @NotNull
    @ManyToOne
    @JoinColumn(name = "id_item_key", nullable = false)
    private ItemDao item;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "tx_uom_key", nullable = false)
    private UomDao uom;

}