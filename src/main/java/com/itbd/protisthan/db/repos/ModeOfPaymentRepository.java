package com.itbd.protisthan.db.repos;

import com.itbd.protisthan.db.dao.LanguageDao;
import com.itbd.protisthan.db.dao.ModeOfPaymentDao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ModeOfPaymentRepository extends JpaRepository<ModeOfPaymentDao, Long>, JpaSpecificationExecutor<ModeOfPaymentDao> {
}