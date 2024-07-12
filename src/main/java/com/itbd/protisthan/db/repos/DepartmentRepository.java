package com.itbd.protisthan.db.repos;

import com.itbd.protisthan.db.dao.DepartmentDao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface DepartmentRepository extends JpaRepository<DepartmentDao, Long>, JpaSpecificationExecutor<DepartmentDao> {
}