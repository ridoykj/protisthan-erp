package com.itbd.protisthan.services.configration;

import com.itbd.protisthan.db.dao.CountryDao;
import com.itbd.protisthan.db.dto.CountryDto;
import com.itbd.protisthan.db.repos.CountryRepository;
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
public class CountryDtoCrudService implements CrudService<CountryDto, String> {
    private final JpaFilterConverter jpaFilterConverter;
    private final CountryRepository countryRepo;

    public CountryDtoCrudService(CountryRepository countryRepo, JpaFilterConverter jpaFilterConverter) {
        this.countryRepo = countryRepo;
        this.jpaFilterConverter = jpaFilterConverter;
    }

    @Override
    @Nonnull
    public List<@Nonnull CountryDto> list(Pageable pageable, @Nullable Filter filter) {
        // Basic list implementation that only covers pagination,
        // but not sorting or filtering
        Specification<CountryDao> spec = filter != null
                ? jpaFilterConverter.toSpec(filter, CountryDao.class)
                : Specification.anyOf();

        Page<CountryDao> accounts = countryRepo.findAll(spec, pageable);
        return accounts.stream().map(CountryDto::toDto).toList();
    }

    @Override
    @Transactional
    public @Nullable CountryDto save(CountryDto value) {
        CountryDao accountDao = value.id() != null && !value.id().isEmpty()
                ? countryRepo.getReferenceById(value.id())
                : new CountryDao();
//        accountDao.setName(value.name());
//        accountDao.setCategory(value.category());
//        accountDao.setPrice(value.price());

        CountryDto.toEntity(value, accountDao);
        return CountryDto.toDto(countryRepo.save(accountDao));
    }

    @Override
    public void delete(String id) {
        countryRepo.deleteById(id);
    }
}