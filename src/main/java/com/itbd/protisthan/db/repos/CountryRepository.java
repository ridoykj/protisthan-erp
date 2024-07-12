package com.itbd.protisthan.db.repos;

import com.itbd.protisthan.db.dao.CountryDao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface CountryRepository extends JpaRepository<CountryDao, String>, JpaSpecificationExecutor<CountryDao> {
}