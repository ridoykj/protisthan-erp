package com.itbd.protisthan.db.repos;

import com.itbd.protisthan.db.dao.StockEntryDao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface StockEntryRepository extends JpaRepository<StockEntryDao, Long>, JpaSpecificationExecutor<StockEntryDao> {
}