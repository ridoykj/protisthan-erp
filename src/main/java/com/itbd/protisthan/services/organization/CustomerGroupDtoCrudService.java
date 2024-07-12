package com.itbd.protisthan.services.organization;

import com.itbd.protisthan.db.dao.CustomerGroupDao;
import com.itbd.protisthan.db.dto.CustomerGroupDto;
import com.itbd.protisthan.db.repos.CustomerGroupRepository;
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
public class CustomerGroupDtoCrudService implements CrudService<CustomerGroupDto, Long> {
    private final JpaFilterConverter jpaFilterConverter;
    private final CustomerGroupRepository customerGroupRepo;

    public CustomerGroupDtoCrudService(CustomerGroupRepository customerGroupRepo, JpaFilterConverter jpaFilterConverter) {
        this.customerGroupRepo = customerGroupRepo;
        this.jpaFilterConverter = jpaFilterConverter;
    }

    @Override
    @Nonnull
    public List<@Nonnull CustomerGroupDto> list(Pageable pageable, @Nullable Filter filter) {
        // Basic list implementation that only covers pagination,
        // but not sorting or filtering
        Specification<CustomerGroupDao> spec = filter != null
                ? jpaFilterConverter.toSpec(filter, CustomerGroupDao.class)
                : Specification.anyOf();

        Page<CustomerGroupDao> accounts = customerGroupRepo.findAll(spec, pageable);
        return accounts.stream().map(CustomerGroupDto::toDto).toList();
    }

    @Override
    @Transactional
    public @Nullable CustomerGroupDto save(CustomerGroupDto value) {
        CustomerGroupDao accountDao = value.id() != null && value.id() > 0
                ? customerGroupRepo.getReferenceById(value.id())
                : new CustomerGroupDao();
//        accountDao.setName(value.name());
//        accountDao.setCategory(value.category());
//        accountDao.setPrice(value.price());

        CustomerGroupDto.toEntity(value, accountDao);
        return CustomerGroupDto.toDto(customerGroupRepo.save(accountDao));
    }

    @Override
    public void delete(Long id) {
        customerGroupRepo.deleteById(id);
    }
}