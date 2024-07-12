package com.itbd.protisthan.db.dto;

import com.itbd.protisthan.db.dao.ItemDao;
import com.itbd.protisthan.db.dao.StockEntryDao;
import com.itbd.protisthan.db.dao.StockEntryDetailDao;
import com.itbd.protisthan.db.dao.UomDao;
import jakarta.validation.constraints.Size;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Instant;

/**
 * DTO for {@link com.itbd.protisthan.db.dao.StockEntryDetailDao}
 */
public record StockEntryDetailDto(
        Long id,
        @Size(max = 140) String name,
        BigDecimal actualQty,
        BigDecimal additionalCost,
        @Size(max = 140) String againstStockEntry,
        Boolean isAllowAlternativeItem,
        Boolean isAllowZeroValuationRate,
        BigDecimal amount,
        @Size(max = 140) String barcode,
        BigDecimal basicAmount,
        BigDecimal basicRate,
        @Size(max = 140) String batchNo,
        @Size(max = 140) String bomNo,
        @Size(max = 140) String costCenter,
        Instant creation,
        String description,
        @Size(max = 140) String expenseAccount,
        Boolean isHasItemScanned,
        Integer idx,
        String image,
        Boolean isFinishedItem,
        Boolean isScrapItem,
        @Size(max = 140) String jobCardItem,
        @Size(max = 140) String materialRequest,
        @Size(max = 140) String materialRequestItem,
        Instant modified,
        @Size(max = 140) String modifiedBy,
        @Size(max = 140) String originalItem,
        @Size(max = 140) String poDetail,
        @Size(max = 140) String project,
        @Size(max = 140) String putawayRule,
        BigDecimal qty,
        @Size(max = 140) String qualityInspection,
        @Size(max = 140) String referencePurchaseReceipt,
        Boolean isRetainSample,
        Integer sampleQuantity,
        @Size(max = 140) String scoRmDetail,
        @Size(max = 140) String serialAndBatchBundle,
        String serialNo,
        Boolean isSetBasicRateManually,
        @Size(max = 140) String steDetail,
        @Size(max = 140) String stockUom,
        @Size(max = 140) String subcontractedItem,
        @Size(max = 140) String tWarehouse,
        BigDecimal transferQty,
        BigDecimal transferredQty,
        Boolean isUseSerialBatchFields,
        BigDecimal valuationRate,
        @Size(max = 256) String itemCode,
        ItemDao item,
        StockEntryDao stockEntry,
        UomDao uom
) implements Serializable {

    public static StockEntryDetailDto toDto(StockEntryDetailDao dao) {
        var item = dao.getItem();
        item.setCategory(null);
        item.setItemGroup(null);
        item.setSaleUom(null);
//        dao.setStockEntry(null);
        var stockEntry = dao.getStockEntry();
        stockEntry.setSupplier(null);
        stockEntry.setStockEntryType(null);
        dao.setItem(item);
        return new StockEntryDetailDto(
                dao.getId(),
                dao.getName(),
                dao.getActualQty(),
                dao.getAdditionalCost(),
                dao.getAgainstStockEntry(),
                dao.getIsAllowAlternativeItem(),
                dao.getIsAllowZeroValuationRate(),
                dao.getAmount(),
                dao.getBarcode(),
                dao.getBasicAmount(),
                dao.getBasicRate(),
                dao.getBatchNo(),
                dao.getBomNo(),
                dao.getCostCenter(),
                dao.getCreation(),
                dao.getDescription(),
                dao.getExpenseAccount(),
                dao.getIsHasItemScanned(),
                dao.getIdx(),
                dao.getImage(),
                dao.getIsFinishedItem(),
                dao.getIsScrapItem(),
                dao.getJobCardItem(),
                dao.getMaterialRequest(),
                dao.getMaterialRequestItem(),
                dao.getModified(),
                dao.getModifiedBy(),
                dao.getOriginalItem(),
                dao.getPoDetail(),
                dao.getProject(),
                dao.getPutawayRule(),
                dao.getQty(),
                dao.getQualityInspection(),
                dao.getReferencePurchaseReceipt(),
                dao.getIsRetainSample(),
                dao.getSampleQuantity(),
                dao.getScoRmDetail(),
                dao.getSerialAndBatchBundle(),
                dao.getSerialNo(),
                dao.getIsSetBasicRateManually(),
                dao.getSteDetail(),
                dao.getStockUom(),
                dao.getSubcontractedItem(),
                dao.getTWarehouse(),
                dao.getTransferQty(),
                dao.getTransferredQty(),
                dao.getIsUseSerialBatchFields(),
                dao.getValuationRate(),
                dao.getItemCode(),
                dao.getItem(),
                dao.getStockEntry(),
                dao.getUom()
        );
    }

    public static void toEntity(StockEntryDetailDto dto,
                                StockEntryDetailDao dao) {
//        dao.setId(dto.id());
        dao.setName(dto.name());
        dao.setActualQty(dto.actualQty());
        dao.setAdditionalCost(dto.additionalCost());
        dao.setAgainstStockEntry(dto.againstStockEntry());
        dao.setIsAllowAlternativeItem(dto.isAllowAlternativeItem());
        dao.setIsAllowZeroValuationRate(dto.isAllowZeroValuationRate());
        dao.setAmount(dto.amount());
        dao.setBarcode(dto.barcode());
        dao.setBasicAmount(dto.basicAmount());
        dao.setBasicRate(dto.basicRate());
        dao.setBatchNo(dto.batchNo());
        dao.setBomNo(dto.bomNo());
        dao.setCostCenter(dto.costCenter());
        dao.setCreation(dto.creation());
        dao.setDescription(dto.description());
        dao.setExpenseAccount(dto.expenseAccount());
        dao.setIsHasItemScanned(dto.isHasItemScanned());
        dao.setIdx(dto.idx());
        dao.setImage(dto.image());
        dao.setIsFinishedItem(dto.isFinishedItem());
        dao.setIsScrapItem(dto.isScrapItem());
        dao.setJobCardItem(dto.jobCardItem());
        dao.setMaterialRequest(dto.materialRequest());
        dao.setMaterialRequestItem(dto.materialRequestItem());
        dao.setModified(dto.modified());
        dao.setModifiedBy(dto.modifiedBy());
        dao.setOriginalItem(dto.originalItem());
        dao.setPoDetail(dto.poDetail());
        dao.setProject(dto.project());
        dao.setPutawayRule(dto.putawayRule());
        dao.setQty(dto.qty());
        dao.setQualityInspection(dto.qualityInspection());
        dao.setReferencePurchaseReceipt(dto.referencePurchaseReceipt());
        dao.setIsRetainSample(dto.isRetainSample());
        dao.setSampleQuantity(dto.sampleQuantity());
        dao.setScoRmDetail(dto.scoRmDetail());
        dao.setSerialAndBatchBundle(dto.serialAndBatchBundle());
        dao.setSerialNo(dto.serialNo());
        dao.setIsSetBasicRateManually(dto.isSetBasicRateManually());
        dao.setSteDetail(dto.steDetail());
        dao.setStockUom(dto.stockUom());
        dao.setSubcontractedItem(dto.subcontractedItem());
        dao.setTWarehouse(dto.tWarehouse());
        dao.setTransferQty(dto.transferQty());
        dao.setTransferredQty(dto.transferredQty());
        dao.setUom(dto.uom());
        dao.setIsUseSerialBatchFields(dto.isUseSerialBatchFields());
        dao.setValuationRate(dto.valuationRate());
        dao.setItemCode(dto.itemCode());
        dao.setItem(dto.item());
        dao.setStockEntry(dto.stockEntry());
        dao.setUom(dto.uom());
    }
}