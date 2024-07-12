package com.itbd.protisthan.db.repos;

import com.itbd.protisthan.db.dao.CategoryDao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface CategoryRepository extends JpaRepository<CategoryDao, Long>, JpaSpecificationExecutor<CategoryDao> {
}