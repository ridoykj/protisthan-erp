package com.itbd.protisthan.db.repos;

import com.itbd.protisthan.db.dao.GenderDao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface GenderRepository extends JpaRepository<GenderDao, Long>, JpaSpecificationExecutor<GenderDao> {
}