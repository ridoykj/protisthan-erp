package com.itbd.protisthan.services.organization;

import com.itbd.protisthan.db.dao.DepartmentDao;
import com.itbd.protisthan.db.dto.DepartmentDto;
import com.itbd.protisthan.db.repos.DepartmentRepository;
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
public class DepartmentDtoCrudService implements CrudService<DepartmentDto, Long> {
    private final JpaFilterConverter jpaFilterConverter;
    private final DepartmentRepository departmentRepo;

    public DepartmentDtoCrudService(DepartmentRepository departmentRepo, JpaFilterConverter jpaFilterConverter) {
        this.departmentRepo = departmentRepo;
        this.jpaFilterConverter = jpaFilterConverter;
    }

    @Override
    @Nonnull
    public List<@Nonnull DepartmentDto> list(Pageable pageable, @Nullable Filter filter) {
        // Basic list implementation that only covers pagination,
        // but not sorting or filtering
        Specification<DepartmentDao> spec = filter != null
                ? jpaFilterConverter.toSpec(filter, DepartmentDao.class)
                : Specification.anyOf();

        Page<DepartmentDao> accounts = departmentRepo.findAll(spec, pageable);
        return accounts.stream().map(DepartmentDto::toDto).toList();
    }

    @Override
    @Transactional
    public @Nullable DepartmentDto save(DepartmentDto value) {
        DepartmentDao accountDao = value.id() != null && value.id() > 0
                ? departmentRepo.getReferenceById(value.id())
                : new DepartmentDao();
//        accountDao.setName(value.name());
//        accountDao.setCategory(value.category());
//        accountDao.setPrice(value.price());

        DepartmentDto.toEntity(value, accountDao);
        return DepartmentDto.toDto(departmentRepo.save(accountDao));
    }

    @Override
    public void delete(Long id) {
        departmentRepo.deleteById(id);
    }
}