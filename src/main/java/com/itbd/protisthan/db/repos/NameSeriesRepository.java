package com.itbd.protisthan.db.repos;

import com.itbd.protisthan.db.dao.NameSeriesDao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface NameSeriesRepository extends JpaRepository<NameSeriesDao, Long>, JpaSpecificationExecutor<NameSeriesDao> {
}