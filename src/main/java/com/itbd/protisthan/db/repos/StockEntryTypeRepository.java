package com.itbd.protisthan.db.repos;

import com.itbd.protisthan.db.dao.StockEntryTypeDao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface StockEntryTypeRepository extends JpaRepository<StockEntryTypeDao, String>, JpaSpecificationExecutor<StockEntryTypeDao> {
}