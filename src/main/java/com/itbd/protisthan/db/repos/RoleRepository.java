package com.itbd.protisthan.db.repos;

import com.itbd.protisthan.db.dao.RoleDao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface RoleRepository extends JpaRepository<RoleDao, String>, JpaSpecificationExecutor<RoleDao> {
}