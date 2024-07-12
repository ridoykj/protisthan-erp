package com.itbd.protisthan.services.configration;

import com.itbd.protisthan.db.dao.UomDao;
import com.itbd.protisthan.db.dto.UomDto;
import com.itbd.protisthan.db.repos.UomRepository;
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
public class UomDtoCrudService implements CrudService<UomDto, String> {
    private final JpaFilterConverter jpaFilterConverter;
    private final UomRepository uomRepo;

    public UomDtoCrudService(UomRepository uomRepo, JpaFilterConverter jpaFilterConverter) {
        this.uomRepo = uomRepo;
        this.jpaFilterConverter = jpaFilterConverter;
    }

    @Override
    @Nonnull
    public List<@Nonnull UomDto> list(Pageable pageable, @Nullable Filter filter) {
        // Basic list implementation that only covers pagination,
        // but not sorting or filtering
        Specification<UomDao> spec = filter != null
                ? jpaFilterConverter.toSpec(filter, UomDao.class)
                : Specification.anyOf();

        Page<UomDao> accounts = uomRepo.findAll(spec, pageable);
        return accounts.stream().map(UomDto::toDto).toList();
    }

    @Override
    @Transactional
    public @Nullable UomDto save(UomDto value) {
        UomDao accountDao = value.id() != null && !value.id().isEmpty()
                ? uomRepo.getReferenceById(value.id())
                : new UomDao();
//        accountDao.setName(value.name());
//        accountDao.setCategory(value.category());
//        accountDao.setPrice(value.price());

        UomDto.toEntity(value, accountDao);
        return UomDto.toDto(uomRepo.save(accountDao));
    }

    @Override
    public void delete(String id) {
        uomRepo.deleteById(id);
    }
}