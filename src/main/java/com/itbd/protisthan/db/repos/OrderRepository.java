package com.itbd.protisthan.db.repos;

import com.itbd.protisthan.db.dao.OrderDao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface OrderRepository extends JpaRepository<OrderDao, Long>, JpaSpecificationExecutor<OrderDao> {
}