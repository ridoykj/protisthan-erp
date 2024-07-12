package com.itbd.protisthan.db.repos;

import com.itbd.protisthan.db.dao.UomConversionFactorDao;
import com.itbd.protisthan.db.dao.iddao.UomConversionId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface UomConversionFactorRepository extends JpaRepository<UomConversionFactorDao, UomConversionId>, JpaSpecificationExecutor<UomConversionFactorDao> {
}