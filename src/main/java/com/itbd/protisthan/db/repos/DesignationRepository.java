package com.itbd.protisthan.db.repos;

import com.itbd.protisthan.db.dao.DesignationDao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface DesignationRepository extends JpaRepository<DesignationDao, Long>, JpaSpecificationExecutor<DesignationDao> {
}