package com.itbd.protisthan.services.administrator;

import com.itbd.protisthan.db.dao.DocPermDao;
import com.itbd.protisthan.db.dao.UserRoleDao;
import com.itbd.protisthan.db.dao.iddao.UserRoleId;
import com.itbd.protisthan.db.dto.UserRoleDto;
import com.itbd.protisthan.db.repos.UserRoleRepository;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import com.vaadin.hilla.BrowserCallable;
import com.vaadin.hilla.Nonnull;
import com.vaadin.hilla.Nullable;
import com.vaadin.hilla.crud.CrudService;
import com.vaadin.hilla.crud.JpaFilterConverter;
import com.vaadin.hilla.crud.filter.Filter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@BrowserCallable
@AnonymousAllowed
public class UserRoleDtoCrudService implements CrudService<UserRoleDto, UserRoleId> {
    private final JpaFilterConverter jpaFilterConverter;
    private final UserRoleRepository userRoleRepo;

    public UserRoleDtoCrudService(UserRoleRepository userRoleRepo, JpaFilterConverter jpaFilterConverter) {
        this.userRoleRepo = userRoleRepo;
        this.jpaFilterConverter = jpaFilterConverter;
    }

    @Override
    @Nonnull
    public List<@Nonnull UserRoleDto> list(Pageable pageable, @Nullable Filter filter) {
        // Basic list implementation that only covers pagination,
        // but not sorting or filtering
        Specification<UserRoleDao> spec = filter != null
                ? jpaFilterConverter.toSpec(filter, UserRoleDao.class)
                : Specification.anyOf();

        Page<UserRoleDao> accounts = userRoleRepo.findAll(spec, pageable);
        return accounts.stream().map(UserRoleDto::toDto).toList();
    }

    @Override
    @Transactional
    public @Nullable UserRoleDto save(UserRoleDto value) {
//        UserRoleDao accountDao = value.id() != null && value.id() > 0
//                ? userRepo.getReferenceById(value.id())
//                : new UserRoleDao();

        UserRoleDao accountDao =  userRoleRepo.existsById(value.id())
                ? userRoleRepo.getReferenceById(value.id())
                : new UserRoleDao();

        UserRoleDto.toEntity(value, accountDao);
        return UserRoleDto.toDto(userRoleRepo.save(accountDao));
    }

    @Override
    public void delete(UserRoleId id) {
        userRoleRepo.deleteById(id);
    }
}