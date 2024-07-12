package com.itbd.protisthan.db.dto;

import com.itbd.protisthan.db.dao.StockEntryDao;
import com.itbd.protisthan.db.dao.StockEntryTypeDao;
import com.itbd.protisthan.db.dao.SupplierDao;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalTime;

/**
 * DTO for {@link com.itbd.protisthan.db.dao.StockEntryDao}
 */
public record StockEntryDto(
        Long id,
        @NotNull @Size(max = 140) String name,
        Boolean isAddToTransit,
        String addressDisplay,
        @Size(max = 140) String amendedFrom,
        Boolean isApplyPutawayRule,
        @Size(max = 140) String bomNo,
        Instant creation,
        @Size(max = 140) String creditNote,
        @Size(max = 140) String deliveryNoteNo,
        BigDecimal fgCompletedQty,
        Boolean isFromBom,
        @Size(max = 140) String fromWarehouse,
        Integer idx,
        Boolean isInspectionRequired,
        Boolean isOpening,
        Boolean isReturn,
        @Size(max = 140) String jobCard,
        @Size(max = 140) String letterHead,
        Instant modified,
        @Size(max = 140) String modifiedBy,
        @Size(max = 140) String namingSeries,
        @Size(max = 140) String outgoingStockEntry,
        @Size(max = 140) String owner,
        BigDecimal perTransferred,
        @Size(max = 140) String pickList,
        @NotNull LocalDate postingDate,
        @NotNull LocalTime postingTime,
        BigDecimal processLossPercentage,
        BigDecimal processLossQty,
        @Size(max = 140) String project,
        @Size(max = 140) String purchaseOrder,
        @Size(max = 140) String purchaseReceiptNo,
        @Size(max = 140) String purpose,
        String remarks,
        @Size(max = 140) String salesInvoiceNo,
        @Size(max = 140) String scanBarcode,
        @Size(max = 140) String selectPrintHeading,
        Boolean isSetPostingTime,
        String sourceAddressDisplay,
        @Size(max = 140) String sourceWarehouseAddress,
        String targetAddressDisplay,
        @Size(max = 140) String targetWarehouseAddress,
        @Size(max = 140) String toWarehouse,
        @NotNull BigDecimal totalAdditionalCosts,
        @NotNull BigDecimal totalAmount,
        BigDecimal totalIncomingValue,
        BigDecimal totalOutgoingValue,
        Boolean isUseMultiLevelBom,
        BigDecimal valueDifference,
        @Size(max = 140) String workOrder,
        @NotNull StockEntryTypeDao stockEntryType,
        @NotNull SupplierDao supplier) implements Serializable {

    public static StockEntryDto toDto(StockEntryDao dao) {
        SupplierDao supplier = dao.getSupplier();
        supplier.setSupplierGroup(null);
        dao.setSupplier(supplier);
        return new StockEntryDto(
                dao.getId(),
                dao.getName(),
                dao.getIsAddToTransit(),
                dao.getAddressDisplay(),
                dao.getAmendedFrom(),
                dao.getIsApplyPutawayRule(),
                dao.getBomNo(),
                dao.getCreation(),
                dao.getCreditNote(),
                dao.getDeliveryNoteNo(),
                dao.getFgCompletedQty(),
                dao.getIsFromBom(),
                dao.getFromWarehouse(),
                dao.getIdx(),
                dao.getIsInspectionRequired(),
                dao.getIsOpening(),
                dao.getIsReturn(),
                dao.getJobCard(),
                dao.getLetterHead(),
                dao.getModified(),
                dao.getModifiedBy(),
                dao.getNamingSeries(),
                dao.getOutgoingStockEntry(),
                dao.getOwner(),
                dao.getPerTransferred(),
                dao.getPickList(),
                dao.getPostingDate(),
                dao.getPostingTime(),
                dao.getProcessLossPercentage(),
                dao.getProcessLossQty(),
                dao.getProject(),
                dao.getPurchaseOrder(),
                dao.getPurchaseReceiptNo(),
                dao.getPurpose(),
                dao.getRemarks(),
                dao.getSalesInvoiceNo(),
                dao.getScanBarcode(),
                dao.getSelectPrintHeading(),
                dao.getIsSetPostingTime(),
                dao.getSourceAddressDisplay(),
                dao.getSourceWarehouseAddress(),
                dao.getTargetAddressDisplay(),
                dao.getTargetWarehouseAddress(),
                dao.getToWarehouse(),
                dao.getTotalAdditionalCosts(),
                dao.getTotalAmount(),
                dao.getTotalIncomingValue(),
                dao.getTotalOutgoingValue(),
                dao.getIsUseMultiLevelBom(),
                dao.getValueDifference(),
                dao.getWorkOrder(),
                dao.getStockEntryType(),
                dao.getSupplier()
        );
    }

    public static void toEntity(StockEntryDto dto,
                                StockEntryDao dao) {
//        dao.setId(dto.id());
        dao.setName(dto.name());
        dao.setIsAddToTransit(dto.isAddToTransit());
        dao.setAddressDisplay(dto.addressDisplay());
        dao.setAmendedFrom(dto.amendedFrom());
        dao.setIsApplyPutawayRule(dto.isApplyPutawayRule());
        dao.setBomNo(dto.bomNo());
//        dao.setCompany(dto.company());
        dao.setCreation(dto.creation());
        dao.setCreditNote(dto.creditNote());
        dao.setDeliveryNoteNo(dto.deliveryNoteNo());
        dao.setFgCompletedQty(dto.fgCompletedQty());
        dao.setIsFromBom(dto.isFromBom());
        dao.setFromWarehouse(dto.fromWarehouse());
        dao.setIdx(dto.idx());
        dao.setIsInspectionRequired(dto.isInspectionRequired());
        dao.setIsOpening(dto.isOpening());
        dao.setIsReturn(dto.isReturn());
        dao.setJobCard(dto.jobCard());
        dao.setLetterHead(dto.letterHead());
        dao.setModified(dto.modified());
        dao.setModifiedBy(dto.modifiedBy());
        dao.setNamingSeries(dto.namingSeries());
        dao.setOutgoingStockEntry(dto.outgoingStockEntry());
        dao.setOwner(dto.owner());
        dao.setPerTransferred(dto.perTransferred());
        dao.setPickList(dto.pickList());
        dao.setPostingDate(dto.postingDate());
        dao.setPostingTime(dto.postingTime());
        dao.setProcessLossPercentage(dto.processLossPercentage());
        dao.setProcessLossQty(dto.processLossQty());
        dao.setProject(dto.project());
        dao.setPurchaseOrder(dto.purchaseOrder());
        dao.setPurchaseReceiptNo(dto.purchaseReceiptNo());
        dao.setPurpose(dto.purpose());
        dao.setRemarks(dto.remarks());
        dao.setSalesInvoiceNo(dto.salesInvoiceNo());
        dao.setScanBarcode(dto.scanBarcode());
        dao.setSelectPrintHeading(dto.selectPrintHeading());
        dao.setIsSetPostingTime(dto.isSetPostingTime());
        dao.setSourceAddressDisplay(dto.sourceAddressDisplay());
        dao.setSourceWarehouseAddress(dto.sourceWarehouseAddress());
        dao.setTargetAddressDisplay(dto.targetAddressDisplay());
        dao.setTargetWarehouseAddress(dto.targetWarehouseAddress());
        dao.setToWarehouse(dto.toWarehouse());
        dao.setTotalAdditionalCosts(dto.totalAdditionalCosts());
        dao.setTotalAmount(dto.totalAmount());
        dao.setTotalIncomingValue(dto.totalIncomingValue());
        dao.setTotalOutgoingValue(dto.totalOutgoingValue());
        dao.setIsUseMultiLevelBom(dto.isUseMultiLevelBom());
        dao.setValueDifference(dto.valueDifference());
        dao.setWorkOrder(dto.workOrder());
        dao.setStockEntryType(dto.stockEntryType());
        dao.setSupplier(dto.supplier());
    }
}