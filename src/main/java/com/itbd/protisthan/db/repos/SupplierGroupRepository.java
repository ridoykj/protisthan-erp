package com.itbd.protisthan.db.repos;

import com.itbd.protisthan.db.dao.SupplierGroupDao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface SupplierGroupRepository extends JpaRepository<SupplierGroupDao, Long>, JpaSpecificationExecutor<SupplierGroupDao> {
}