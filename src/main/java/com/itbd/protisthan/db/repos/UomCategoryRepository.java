package com.itbd.protisthan.db.repos;

import com.itbd.protisthan.db.dao.UomCategoryDao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface UomCategoryRepository extends JpaRepository<UomCategoryDao, String>, JpaSpecificationExecutor<UomCategoryDao> {
}