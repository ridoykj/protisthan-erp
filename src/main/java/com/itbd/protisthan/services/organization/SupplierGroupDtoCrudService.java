package com.itbd.protisthan.services.organization;

import com.itbd.protisthan.db.dao.SupplierGroupDao;
import com.itbd.protisthan.db.dto.SupplierGroupDto;
import com.itbd.protisthan.db.repos.SupplierGroupRepository;
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
public class SupplierGroupDtoCrudService implements CrudService<SupplierGroupDto, Long> {
    private final JpaFilterConverter jpaFilterConverter;
    private final SupplierGroupRepository supplierGroupRepo;

    public SupplierGroupDtoCrudService(SupplierGroupRepository supplierGroupRepo, JpaFilterConverter jpaFilterConverter) {
        this.supplierGroupRepo = supplierGroupRepo;
        this.jpaFilterConverter = jpaFilterConverter;
    }

    @Override
    @Nonnull
    public List<@Nonnull SupplierGroupDto> list(Pageable pageable, @Nullable Filter filter) {
        // Basic list implementation that only covers pagination,
        // but not sorting or filtering
        Specification<SupplierGroupDao> spec = filter != null
                ? jpaFilterConverter.toSpec(filter, SupplierGroupDao.class)
                : Specification.anyOf();

        Page<SupplierGroupDao> accounts = supplierGroupRepo.findAll(spec, pageable);
        return accounts.stream().map(SupplierGroupDto::toDto).toList();
    }

    @Override
    @Transactional
    public @Nullable SupplierGroupDto save(SupplierGroupDto value) {
        SupplierGroupDao accountDao = value.id() != null && value.id() > 0
                ? supplierGroupRepo.getReferenceById(value.id())
                : new SupplierGroupDao();
//        accountDao.setName(value.name());
//        accountDao.setCategory(value.category());
//        accountDao.setPrice(value.price());

        SupplierGroupDto.toEntity(value, accountDao);
        return SupplierGroupDto.toDto(supplierGroupRepo.save(accountDao));
    }

    @Override
    public void delete(Long id) {
        supplierGroupRepo.deleteById(id);
    }
}