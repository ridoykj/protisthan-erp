package com.itbd.protisthan.db.dao;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
@Entity
@Table(name = "t_stock_entry", schema = "erp_over")
public class StockEntryDao {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_stock_entry_key", nullable = false)
    private Long id;

//    @NotNull
    @Size(max = 140)
    @Column(name = "tx_name", length = 140, nullable = false)
    private String name;

    @ColumnDefault("0")
    @Column(name = "is_add_to_transit")
    private Boolean isAddToTransit = false;

    @Lob
    @Column(name = "tx_address_display")
    private String addressDisplay;

    @Size(max = 140)
    @Column(name = "tx_amended_from", length = 140)
    private String amendedFrom;

    @ColumnDefault("0")
    @Column(name = "is_apply_putaway_rule")
    private Boolean isApplyPutawayRule = false;

    @Size(max = 140)
    @Column(name = "tx_bom_no", length = 140)
    private String bomNo;

    @Column(name = "dtt_creation")
    private Instant creation;

    @Size(max = 140)
    @Column(name = "tx_credit_note", length = 140)
    private String creditNote;

    @Size(max = 140)
    @Column(name = "tx_delivery_note_no", length = 140)
    private String deliveryNoteNo;

    @Column(name = "flt_fg_completed_qty", precision = 21, scale = 9)
    private BigDecimal fgCompletedQty;

    @ColumnDefault("0")
    @Column(name = "is_from_bom")
    private Boolean isFromBom = false;

    @Size(max = 140)
    @Column(name = "tx_from_warehouse", length = 140)
    private String fromWarehouse;

    @Column(name = "ct_idx")
    private Integer idx;

    @ColumnDefault("0")
    @Column(name = "is_inspection_required")
    private Boolean isInspectionRequired = false;

    @ColumnDefault("0")
    @Column(name = "is_opening")
    private Boolean isOpening = false;

    @ColumnDefault("0")
    @Column(name = "is_return")
    private Boolean isReturn = false;

    @Size(max = 140)
    @Column(name = "tx_job_card", length = 140)
    private String jobCard;

    @Size(max = 140)
    @Column(name = "tx_letter_head", length = 140)
    private String letterHead;

    @Column(name = "dtt_modified")
    private Instant modified;

    @Size(max = 140)
    @Column(name = "tx_modified_by", length = 140)
    private String modifiedBy;

    @Size(max = 140)
    @Column(name = "tx_naming_series", length = 140)
    private String namingSeries;

    @Size(max = 140)
    @Column(name = "tx_outgoing_stock_entry", length = 140)
    private String outgoingStockEntry;

    @Size(max = 140)
    @Column(name = "tx_owner", length = 140)
    private String owner;

    @Column(name = "flt_per_transferred", precision = 21, scale = 9)
    private BigDecimal perTransferred;

    @Size(max = 140)
    @Column(name = "tx_pick_list", length = 140)
    private String pickList;

    @Column(name = "dt_posting_date")
    private LocalDate postingDate;

    @Column(name = "tt_posting_time")
    private LocalTime postingTime;

    @Column(name = "flt_process_loss_percentage", precision = 21, scale = 9)
    private BigDecimal processLossPercentage;

    @Column(name = "flt_process_loss_qty", precision = 21, scale = 9)
    private BigDecimal processLossQty;

    @Size(max = 140)
    @Column(name = "tx_project", length = 140)
    private String project;

    @Size(max = 140)
    @Column(name = "tx_purchase_order", length = 140)
    private String purchaseOrder;

    @Size(max = 140)
    @Column(name = "tx_purchase_receipt_no", length = 140)
    private String purchaseReceiptNo;

    @Size(max = 140)
    @Column(name = "tx_purpose", length = 140)
    private String purpose;

    @Lob
    @Column(name = "tx_remarks")
    private String remarks;

    @Size(max = 140)
    @Column(name = "tx_sales_invoice_no", length = 140)
    private String salesInvoiceNo;

    @Size(max = 140)
    @Column(name = "tx_scan_barcode", length = 140)
    private String scanBarcode;

    @Size(max = 140)
    @Column(name = "tx_select_print_heading", length = 140)
    private String selectPrintHeading;

    @ColumnDefault("0")
    @Column(name = "is_set_posting_time")
    private Boolean isSetPostingTime = false;

    @Lob
    @Column(name = "tx_source_address_display")
    private String sourceAddressDisplay;

    @Size(max = 140)
    @Column(name = "tx_source_warehouse_address", length = 140)
    private String sourceWarehouseAddress;

    @Lob
    @Column(name = "tx_target_address_display")
    private String targetAddressDisplay;

    @Size(max = 140)
    @Column(name = "tx_target_warehouse_address", length = 140)
    private String targetWarehouseAddress;

    @Size(max = 140)
    @Column(name = "tx_to_warehouse", length = 140)
    private String toWarehouse;

    @Column(name = "flt_total_additional_costs", precision = 21, scale = 9)
    private BigDecimal totalAdditionalCosts;

    @Column(name = "flt_total_amount", precision = 21, scale = 9)
    private BigDecimal totalAmount;

    @Column(name = "flt_total_incoming_value", precision = 21, scale = 9)
    private BigDecimal totalIncomingValue;

    @Column(name = "flt_total_outgoing_value", precision = 21, scale = 9)
    private BigDecimal totalOutgoingValue;

    @ColumnDefault("0")
    @Column(name = "is_use_multi_level_bom")
    private Boolean isUseMultiLevelBom = false;

    @Column(name = "flt_value_difference", precision = 21, scale = 9)
    private BigDecimal valueDifference;

    @Size(max = 140)
    @Column(name = "tx_work_order", length = 140)
    private String workOrder;

//    @NotNull
    @ManyToOne
    @JoinColumn(name = "tx_stock_entry_type_key", nullable = false)
    private StockEntryTypeDao stockEntryType;

//    @NotNull
    @ManyToOne
    @JoinColumn(name = "id_supplier_key", nullable = false)
    private SupplierDao supplier;

}