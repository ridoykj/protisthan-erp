package com.itbd.protisthan.db.repos;

import com.itbd.protisthan.db.dao.UomDao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface UomRepository extends JpaRepository<UomDao, String>, JpaSpecificationExecutor<UomDao> {
}