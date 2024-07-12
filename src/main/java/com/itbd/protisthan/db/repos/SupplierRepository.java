package com.itbd.protisthan.db.repos;

import com.itbd.protisthan.db.dao.SupplierDao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface SupplierRepository extends JpaRepository<SupplierDao, Long>, JpaSpecificationExecutor<SupplierDao> {
}