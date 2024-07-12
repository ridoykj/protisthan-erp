package com.itbd.protisthan.services.configration;

import com.itbd.protisthan.db.dao.LanguageDao;
import com.itbd.protisthan.db.dto.LanguageDto;
import com.itbd.protisthan.db.repos.LanguageRepository;
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
public class LanguageDtoCrudService implements CrudService<LanguageDto, String> {
    private final JpaFilterConverter jpaFilterConverter;
    private final LanguageRepository languageRepo;

    public LanguageDtoCrudService(LanguageRepository languageRepo, JpaFilterConverter jpaFilterConverter) {
        this.languageRepo = languageRepo;
        this.jpaFilterConverter = jpaFilterConverter;
    }

    @Override
    @Nonnull
    public List<@Nonnull LanguageDto> list(Pageable pageable, @Nullable Filter filter) {
        // Basic list implementation that only covers pagination,
        // but not sorting or filtering
        Specification<LanguageDao> spec = filter != null
                ? jpaFilterConverter.toSpec(filter, LanguageDao.class)
                : Specification.anyOf();

        Page<LanguageDao> accounts = languageRepo.findAll(spec, pageable);
        return accounts.stream().map(LanguageDto::toDto).toList();
    }

    @Override
    @Transactional
    public @Nullable LanguageDto save(LanguageDto value) {
        LanguageDao accountDao = value.id() != null && !value.id().isEmpty()
                ? languageRepo.getReferenceById(value.id())
                : new LanguageDao();
//        accountDao.setName(value.name());
//        accountDao.setCategory(value.category());
//        accountDao.setPrice(value.price());

        LanguageDto.toEntity(value, accountDao);
        return LanguageDto.toDto(languageRepo.save(accountDao));
    }

    @Override
    public void delete(String id) {
        languageRepo.deleteById(id);
    }
}