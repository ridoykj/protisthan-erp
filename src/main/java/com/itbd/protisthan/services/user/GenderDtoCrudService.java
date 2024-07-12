package com.itbd.protisthan.services.user;

import com.itbd.protisthan.db.dao.GenderDao;
import com.itbd.protisthan.db.dto.GenderDto;
import com.itbd.protisthan.db.repos.GenderRepository;
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
public class GenderDtoCrudService implements CrudService<GenderDto, Long> {
    private final JpaFilterConverter jpaFilterConverter;
    private final GenderRepository genderRepo;

    public GenderDtoCrudService(GenderRepository genderRepo, JpaFilterConverter jpaFilterConverter) {
        this.genderRepo = genderRepo;
        this.jpaFilterConverter = jpaFilterConverter;
    }

    @Override
    @Nonnull
    public List<@Nonnull GenderDto> list(Pageable pageable, @Nullable Filter filter) {
        // Basic list implementation that only covers pagination,
        // but not sorting or filtering
        Specification<GenderDao> spec = filter != null
                ? jpaFilterConverter.toSpec(filter, GenderDao.class)
                : Specification.anyOf();

        Page<GenderDao> accounts = genderRepo.findAll(spec, pageable);
        return accounts.stream().map(GenderDto::toDto).toList();
    }

    @Override
    @Transactional
    public @Nullable GenderDto save(GenderDto value) {
        GenderDao accountDao = value.id() != null && value.id() > 0
                ? genderRepo.getReferenceById(value.id())
                : new GenderDao();
//        accountDao.setName(value.name());
//        accountDao.setCategory(value.category());
//        accountDao.setPrice(value.price());

        GenderDto.toEntity(value, accountDao);
        return GenderDto.toDto(genderRepo.save(accountDao));
    }

    @Override
    public void delete(Long id) {
        genderRepo.deleteById(id);
    }
}