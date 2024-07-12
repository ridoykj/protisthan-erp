package com.itbd.protisthan.db.repos;

import com.itbd.protisthan.db.dao.CustomerDao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface CustomerRepository extends JpaRepository<CustomerDao, Long>, JpaSpecificationExecutor<CustomerDao> {
}