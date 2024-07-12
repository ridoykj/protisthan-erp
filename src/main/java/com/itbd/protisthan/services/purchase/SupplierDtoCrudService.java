package com.itbd.protisthan.services.purchase;

import com.itbd.protisthan.db.dao.SupplierDao;
import com.itbd.protisthan.db.dto.SupplierDto;
import com.itbd.protisthan.db.repos.SupplierRepository;
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
public class SupplierDtoCrudService implements CrudService<SupplierDto, Long> {
    private final JpaFilterConverter jpaFilterConverter;
    private final SupplierRepository supplierRepo;

    public SupplierDtoCrudService(SupplierRepository supplierRepo, JpaFilterConverter jpaFilterConverter) {
        this.supplierRepo = supplierRepo;
        this.jpaFilterConverter = jpaFilterConverter;
    }

    @Transactional
    @Override
    @Nonnull
    public List<@Nonnull SupplierDto> list(Pageable pageable, @Nullable Filter filter) {
        // Basic list implementation that only covers pagination,
        // but not sorting or filtering
        Specification<SupplierDao> spec = filter != null
                ? jpaFilterConverter.toSpec(filter, SupplierDao.class)
                : Specification.anyOf();

        Page<SupplierDao> accounts = supplierRepo.findAll(spec, pageable);
        return accounts.stream().map(SupplierDto::toDto).toList();
    }

    @Override
    @Transactional
    public @Nullable SupplierDto save(SupplierDto value) {
        SupplierDao accountDao = value.id() != null && value.id() > 0
                ? supplierRepo.getReferenceById(value.id())
                : new SupplierDao();
//        accountDao.setName(value.name());
//        accountDao.setCategory(value.category());
//        accountDao.setPrice(value.price());

        SupplierDto.toEntity(value, accountDao);
        return SupplierDto.toDto(supplierRepo.save(accountDao));
    }

    @Override
    public void delete(Long id) {
        supplierRepo.deleteById(id);
    }
}