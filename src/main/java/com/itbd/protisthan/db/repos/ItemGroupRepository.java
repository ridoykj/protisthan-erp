package com.itbd.protisthan.db.repos;

import com.itbd.protisthan.db.dao.CategoryDao;
import com.itbd.protisthan.db.dao.ItemGroupDao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ItemGroupRepository extends JpaRepository<ItemGroupDao, Long> , JpaSpecificationExecutor<ItemGroupDao> {
}