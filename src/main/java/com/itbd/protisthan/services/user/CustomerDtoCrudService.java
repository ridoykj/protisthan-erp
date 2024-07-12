package com.itbd.protisthan.services.user;

import com.itbd.protisthan.db.dao.CustomerDao;
import com.itbd.protisthan.db.dto.CustomerDto;
import com.itbd.protisthan.db.repos.CustomerRepository;
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
public class CustomerDtoCrudService implements CrudService<CustomerDto, Long> {
    private final JpaFilterConverter jpaFilterConverter;
    private final CustomerRepository customerRepo;

    public CustomerDtoCrudService(CustomerRepository customerRepo, JpaFilterConverter jpaFilterConverter) {
        this.customerRepo = customerRepo;
        this.jpaFilterConverter = jpaFilterConverter;
    }

    @Override
    @Nonnull
    @Transactional
    public List<@Nonnull CustomerDto> list(Pageable pageable, @Nullable Filter filter) {
        // Basic list implementation that only covers pagination,
        // but not sorting or filtering
        Specification<CustomerDao> spec = filter != null
                ? jpaFilterConverter.toSpec(filter, CustomerDao.class)
                : Specification.anyOf();

        Page<CustomerDao> accounts = customerRepo.findAll(spec, pageable);
        return accounts.stream().map(CustomerDto::toDto).toList();
    }

    @Override
    @Transactional
    public @Nullable CustomerDto save(CustomerDto value) {
        CustomerDao accountDao = value.id() != null && value.id() > 0
                ? customerRepo.getReferenceById(value.id())
                : new CustomerDao();
//        accountDao.setName(value.name());
//        accountDao.setCategory(value.category());
//        accountDao.setPrice(value.price());

        CustomerDto.toEntity(value, accountDao);
        return CustomerDto.toDto(customerRepo.save(accountDao));
    }

    @Override
    public void delete(Long id) {
        customerRepo.deleteById(id);
    }
}