package com.itbd.protisthan.services.user;

import com.itbd.protisthan.db.dao.EmployeeDao;
import com.itbd.protisthan.db.dto.EmployeeDto;
import com.itbd.protisthan.db.repos.EmployeeRepository;
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
public class EmployeeDtoCrudService implements CrudService<EmployeeDto, Long> {
    private final JpaFilterConverter jpaFilterConverter;
    private final EmployeeRepository employeeRepo;

    public EmployeeDtoCrudService(EmployeeRepository employeeRepo, JpaFilterConverter jpaFilterConverter) {
        this.employeeRepo = employeeRepo;
        this.jpaFilterConverter = jpaFilterConverter;
    }

    @Override
    @Nonnull
    @Transactional
    public List<@Nonnull EmployeeDto> list(Pageable pageable, @Nullable Filter filter) {
        // Basic list implementation that only covers pagination,
        // but not sorting or filtering
        Specification<EmployeeDao> spec = filter != null
                ? jpaFilterConverter.toSpec(filter, EmployeeDao.class)
                : Specification.anyOf();

        Page<EmployeeDao> accounts = employeeRepo.findAll(spec, pageable);
        return accounts.stream().map(EmployeeDto::toDto).toList();
    }

    @Override
    @Transactional
    public @Nullable EmployeeDto save(EmployeeDto value) {
        EmployeeDao accountDao = value.id() != null && value.id() > 0
                ? employeeRepo.getReferenceById(value.id())
                : new EmployeeDao();
//        accountDao.setName(value.name());
//        accountDao.setCategory(value.category());
//        accountDao.setPrice(value.price());

        EmployeeDto.toEntity(value, accountDao);
        return EmployeeDto.toDto(employeeRepo.save(accountDao));
    }

    @Override
    public void delete(Long id) {
        employeeRepo.deleteById(id);
    }
}