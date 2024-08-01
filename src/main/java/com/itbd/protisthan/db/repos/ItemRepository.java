package com.itbd.protisthan.db.repos;

import com.itbd.protisthan.db.dao.ItemDao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.history.RevisionRepository;

public interface ItemRepository
        extends JpaRepository<ItemDao, Long>,
//        RevisionRepository<ItemDao, Long, Long>,
        JpaSpecificationExecutor<ItemDao> {
    ItemDao findByName(String name);
}