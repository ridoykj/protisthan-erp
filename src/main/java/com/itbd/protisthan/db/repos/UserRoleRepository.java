package com.itbd.protisthan.db.repos;

import com.itbd.protisthan.db.dao.UserRoleDao;
import com.itbd.protisthan.db.dao.iddao.UserRoleId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface UserRoleRepository extends JpaRepository<UserRoleDao, UserRoleId>, JpaSpecificationExecutor<UserRoleDao> {
}