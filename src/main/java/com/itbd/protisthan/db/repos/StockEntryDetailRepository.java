package com.itbd.protisthan.db.repos;

import com.itbd.protisthan.db.dao.StockEntryDetailDao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface StockEntryDetailRepository extends JpaRepository<StockEntryDetailDao, Long>, JpaSpecificationExecutor<StockEntryDetailDao> {
}