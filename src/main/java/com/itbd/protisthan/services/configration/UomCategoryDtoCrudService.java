package com.itbd.protisthan.services.configration;

import com.itbd.protisthan.db.dao.UomCategoryDao;
import com.itbd.protisthan.db.dto.UomCategoryDto;
import com.itbd.protisthan.db.repos.UomCategoryRepository;
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
public class UomCategoryDtoCrudService implements CrudService<UomCategoryDto, String> {
    private final JpaFilterConverter jpaFilterConverter;
    private final UomCategoryRepository uomCategoryRepo;

    public UomCategoryDtoCrudService(UomCategoryRepository uomCategoryRepo, JpaFilterConverter jpaFilterConverter) {
        this.uomCategoryRepo = uomCategoryRepo;
        this.jpaFilterConverter = jpaFilterConverter;
    }

    @Override
    @Nonnull
    public List<@Nonnull UomCategoryDto> list(Pageable pageable, @Nullable Filter filter) {
        // Basic list implementation that only covers pagination,
        // but not sorting or filtering
        Specification<UomCategoryDao> spec = filter != null
                ? jpaFilterConverter.toSpec(filter, UomCategoryDao.class)
                : Specification.anyOf();

        Page<UomCategoryDao> accounts = uomCategoryRepo.findAll(spec, pageable);
        return accounts.stream().map(UomCategoryDto::toDto).toList();
    }

    @Override
    @Transactional
    public @Nullable UomCategoryDto save(UomCategoryDto value) {
        UomCategoryDao accountDao = value.id() != null && !value.id().isEmpty()
                ? uomCategoryRepo.getReferenceById(value.id())
                : new UomCategoryDao();
//        accountDao.setName(value.name());
//        accountDao.setCategory(value.category());
//        accountDao.setPrice(value.price());

        UomCategoryDto.toEntity(value, accountDao);
        return UomCategoryDto.toDto(uomCategoryRepo.save(accountDao));
    }

    @Override
    public void delete(String id) {
        uomCategoryRepo.deleteById(id);
    }
}