package com.itbd.protisthan.db.repos;

import com.itbd.protisthan.db.dao.ItemDao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ItemRepository extends JpaRepository<ItemDao, Long>, JpaSpecificationExecutor<ItemDao> {
    ItemDao findByName(String name);
}