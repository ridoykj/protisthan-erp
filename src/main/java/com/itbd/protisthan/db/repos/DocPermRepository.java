package com.itbd.protisthan.db.repos;

import com.itbd.protisthan.db.dao.DocPermDao;
import com.itbd.protisthan.db.dao.DocTypeDao;
import com.itbd.protisthan.db.dao.RoleDao;
import com.itbd.protisthan.db.dao.iddao.DocPremId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.lang.NonNull;

public interface DocPermRepository extends JpaRepository<DocPermDao, DocPremId>, JpaSpecificationExecutor<DocPermDao> {
}