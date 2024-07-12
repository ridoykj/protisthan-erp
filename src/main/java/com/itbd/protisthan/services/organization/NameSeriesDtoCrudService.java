package com.itbd.protisthan.services.organization;

import com.itbd.protisthan.db.dao.NameSeriesDao;
import com.itbd.protisthan.db.dto.NameSeriesDto;
import com.itbd.protisthan.db.repos.NameSeriesRepository;
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
public class NameSeriesDtoCrudService implements CrudService<NameSeriesDto, Long> {
    private final JpaFilterConverter jpaFilterConverter;
    private final NameSeriesRepository nameSeriesRepo;

    public NameSeriesDtoCrudService(NameSeriesRepository nameSeriesRepo, JpaFilterConverter jpaFilterConverter) {
        this.nameSeriesRepo = nameSeriesRepo;
        this.jpaFilterConverter = jpaFilterConverter;
    }

    @Override
    @Nonnull
    public List<@Nonnull NameSeriesDto> list(Pageable pageable, @Nullable Filter filter) {
        // Basic list implementation that only covers pagination,
        // but not sorting or filtering
        Specification<NameSeriesDao> spec = filter != null
                ? jpaFilterConverter.toSpec(filter, NameSeriesDao.class)
                : Specification.anyOf();

        Page<NameSeriesDao> accounts = nameSeriesRepo.findAll(spec, pageable);
        return accounts.stream().map(NameSeriesDto::toDto).toList();
    }

    @Override
    @Transactional
    public @Nullable NameSeriesDto save(NameSeriesDto value) {
        NameSeriesDao accountDao = value.id() != null && value.id() > 0
                ? nameSeriesRepo.getReferenceById(value.id())
                : new NameSeriesDao();
//        accountDao.setName(value.name());
//        accountDao.setCategory(value.category());
//        accountDao.setPrice(value.price());

        NameSeriesDto.toEntity(value, accountDao);
        return NameSeriesDto.toDto(nameSeriesRepo.save(accountDao));
    }

    @Override
    public void delete(Long id) {
        nameSeriesRepo.deleteById(id);
    }
}