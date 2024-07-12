package com.itbd.protisthan.db.repos;

import com.itbd.protisthan.db.dao.UserDao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface UserRepository extends JpaRepository<UserDao, Long>, JpaSpecificationExecutor<UserDao> {
}