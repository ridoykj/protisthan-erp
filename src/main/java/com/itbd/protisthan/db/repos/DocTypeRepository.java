package com.itbd.protisthan.db.repos;

import com.itbd.protisthan.db.dao.DocTypeDao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface DocTypeRepository extends JpaRepository<DocTypeDao, String>, JpaSpecificationExecutor<DocTypeDao> {
}