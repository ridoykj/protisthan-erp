package com.itbd.protisthan.services.configration;

import com.itbd.protisthan.db.dao.UomConversionFactorDao;
import com.itbd.protisthan.db.dao.iddao.UomConversionId;
import com.itbd.protisthan.db.dto.UomConversionFactorDto;
import com.itbd.protisthan.db.repos.UomConversionFactorRepository;
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
public class UomConversionFactorDtoCrudService implements CrudService<UomConversionFactorDto, UomConversionId> {
    private final JpaFilterConverter jpaFilterConverter;
    private final UomConversionFactorRepository uomConversionFactorRepo;

    public UomConversionFactorDtoCrudService(UomConversionFactorRepository uomConversionFactorRepo, JpaFilterConverter jpaFilterConverter) {
        this.uomConversionFactorRepo = uomConversionFactorRepo;
        this.jpaFilterConverter = jpaFilterConverter;
    }

    @Transactional
    @Override
    @Nonnull
    public List<@Nonnull UomConversionFactorDto> list(Pageable pageable, @Nullable Filter filter) {
        // Basic list implementation that only covers pagination,
        // but not sorting or filtering
        Specification<UomConversionFactorDao> spec = filter != null
                ? jpaFilterConverter.toSpec(filter, UomConversionFactorDao.class)
                : Specification.anyOf();

        Page<UomConversionFactorDao> accounts = uomConversionFactorRepo.findAll(spec, pageable);
        return accounts.stream().map(UomConversionFactorDto::toDto).toList();
    }

    @Override
    @Transactional
    public @Nullable UomConversionFactorDto save(UomConversionFactorDto value) {
        UomConversionFactorDao accountDao = value.id() != null && !value.id().getToUomKey().isEmpty() && !value.id().getFromUomKey().isEmpty()
                ? uomConversionFactorRepo.getReferenceById(value.id())
                : new UomConversionFactorDao();
//        accountDao.setName(value.name());
//        accountDao.setCategory(value.category());
//        accountDao.setPrice(value.price());

        UomConversionFactorDto.toEntity(value, accountDao);
        return UomConversionFactorDto.toDto(uomConversionFactorRepo.save(accountDao));
    }

    @Override
    public void delete(UomConversionId id) {
        uomConversionFactorRepo.deleteById(id);
    }
}