package com.itbd.protisthan.db.repos;

import com.itbd.protisthan.db.dao.UserTypeDao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface UserTypeRepository extends JpaRepository<UserTypeDao, String>, JpaSpecificationExecutor<UserTypeDao> {
}