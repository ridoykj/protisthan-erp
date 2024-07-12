package com.itbd.protisthan.db.repos;

import com.itbd.protisthan.db.dao.CustomerGroupDao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface CustomerGroupRepository extends JpaRepository<CustomerGroupDao, Long>, JpaSpecificationExecutor<CustomerGroupDao> {
}
