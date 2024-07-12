package com.itbd.protisthan.db.repos;

import com.itbd.protisthan.db.dao.OrderDetailDao;
import com.itbd.protisthan.db.dao.iddao.OrderDetailId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface OrderDetailRepository extends JpaRepository<OrderDetailDao, OrderDetailId>, JpaSpecificationExecutor<OrderDetailDao> {
}