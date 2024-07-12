package com.itbd.protisthan.db.dto;

import com.itbd.protisthan.db.dao.DocPermDao;
import com.itbd.protisthan.db.dao.DocTypeDao;
import com.itbd.protisthan.db.dao.RoleDao;
import com.itbd.protisthan.db.dao.iddao.DocPremId;
import jakarta.validation.constraints.Size;

import java.io.Serializable;
import java.time.Instant;

/**
 * DTO for {@link com.itbd.protisthan.db.dao.DocPermDao}
 */

public record DocPermDto(
        DocPremId id,
        @Size(max = 255) String name,
        Boolean isAmend,
        Boolean isCancel,
        Boolean isCreate,
        Instant creation,
        Boolean isDelete,
        Boolean isEmail,
        Boolean isExport,
        Integer ctIdx,
        Boolean isIfOwner,
        Boolean isImport,
        Boolean isDocStatus,
        Instant dttModified,
        @Size(max = 140) String modifiedBy,
        @Size(max = 255) String owner,
        Integer permLevel,
        Boolean isPrint,
        Boolean isRead,
        Boolean isReport,
        Boolean isSelect,
        Boolean isShare,
        Boolean isSubmit,
        Boolean isWrite, RoleDao role, DocTypeDao docType) implements Serializable {
    public static DocPermDto toDto(DocPermDao dao) {
        return new DocPermDto(
                dao.getId(),
                dao.getName(),
                dao.getIsAmend(),
                dao.getIsCancel(),
                dao.getIsCreate(),
                dao.getCreation(),
                dao.getIsDelete(),
                dao.getIsEmail(),
                dao.getIsExport(),
                dao.getCtIdx(),
                dao.getIsIfOwner(),
                dao.getIsImport(),
                dao.getIsDocStatus(),
                dao.getDttModified(),
                dao.getModifiedBy(),
                dao.getOwner(),
                dao.getPermLevel(),
                dao.getIsPrint(),
                dao.getIsRead(),
                dao.getIsReport(),
                dao.getIsSelect(),
                dao.getIsShare(),
                dao.getIsSubmit(),
                dao.getIsWrite(),
                dao.getRole(),
                dao.getDocType()
        );
    }

    public static void toEntity(DocPermDto dto, DocPermDao dao) {
        dao.setId(dto.id());
        dao.setName(dto.name());
        dao.setIsAmend(dto.isAmend());
        dao.setIsCancel(dto.isCancel());
        dao.setIsCreate(dto.isCreate());
        dao.setCreation(dto.creation());
        dao.setIsDelete(dto.isDelete());
        dao.setIsEmail(dto.isEmail());
        dao.setIsExport(dto.isExport());
        dao.setCtIdx(dto.ctIdx());
        dao.setIsIfOwner(dto.isIfOwner());
        dao.setIsImport(dto.isImport());
        dao.setIsDocStatus(dto.isDocStatus());
        dao.setDttModified(dto.dttModified());
        dao.setModifiedBy(dto.modifiedBy());
        dao.setOwner(dto.owner());
        dao.setPermLevel(dto.permLevel());
        dao.setIsPrint(dto.isPrint());
        dao.setIsRead(dto.isRead());
        dao.setIsReport(dto.isReport());
        dao.setIsSelect(dto.isSelect());
        dao.setIsShare(dto.isShare());
        dao.setIsSubmit(dto.isSubmit());
        dao.setIsWrite(dto.isWrite());
        dao.setRole(dto.role());
        dao.setDocType(dto.docType());
    }
}