package com.itbd.protisthan.db.repos;

import com.itbd.protisthan.db.dao.LanguageDao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface LanguageRepository extends JpaRepository<LanguageDao, String>, JpaSpecificationExecutor<LanguageDao> {
}