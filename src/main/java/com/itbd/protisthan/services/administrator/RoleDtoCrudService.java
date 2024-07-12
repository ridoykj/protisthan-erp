package com.itbd.protisthan.services.administrator;

import com.itbd.protisthan.db.dao.RoleDao;
import com.itbd.protisthan.db.dto.RoleDto;
import com.itbd.protisthan.db.repos.RoleRepository;
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
public class RoleDtoCrudService implements CrudService<RoleDto, String> {
    private final JpaFilterConverter jpaFilterConverter;
    private final RoleRepository roleRepo;

    public RoleDtoCrudService(RoleRepository roleRepo, JpaFilterConverter jpaFilterConverter) {
        this.roleRepo = roleRepo;
        this.jpaFilterConverter = jpaFilterConverter;
    }

    @Override
    @Nonnull
    public List<@Nonnull RoleDto> list(Pageable pageable, @Nullable Filter filter) {
        // Basic list implementation that only covers pagination,
        // but not sorting or filtering
        Specification<RoleDao> spec = filter != null
                ? jpaFilterConverter.toSpec(filter, RoleDao.class)
                : Specification.anyOf();

        Page<RoleDao> accounts = roleRepo.findAll(spec, pageable);
        return accounts.stream().map(RoleDto::toDto).toList();
    }

    @Override
    @Transactional
    public @Nullable RoleDto save(RoleDto value) {
        RoleDao accountDao = value.id() != null && !value.id().isEmpty()
                ? roleRepo.getReferenceById(value.id())
                : new RoleDao();
//        accountDao.setName(value.name());
//        accountDao.setCategory(value.category());
//        accountDao.setPrice(value.price());

        RoleDto.toEntity(value, accountDao);
        return RoleDto.toDto(roleRepo.save(accountDao));
    }

    @Override
    public void delete(String id) {
        roleRepo.deleteById(id);
    }
}