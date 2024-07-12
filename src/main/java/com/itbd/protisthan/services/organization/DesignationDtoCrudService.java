package com.itbd.protisthan.services.organization;

import com.itbd.protisthan.db.dao.DesignationDao;
import com.itbd.protisthan.db.dto.DesignationDto;
import com.itbd.protisthan.db.repos.DesignationRepository;
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
public class DesignationDtoCrudService implements CrudService<DesignationDto, Long> {
    private final JpaFilterConverter jpaFilterConverter;
    private final DesignationRepository designationRepo;

    public DesignationDtoCrudService(DesignationRepository designationRepo, JpaFilterConverter jpaFilterConverter) {
        this.designationRepo = designationRepo;
        this.jpaFilterConverter = jpaFilterConverter;
    }

    @Override
    @Nonnull
    public List<@Nonnull DesignationDto> list(Pageable pageable, @Nullable Filter filter) {
        // Basic list implementation that only covers pagination,
        // but not sorting or filtering
        Specification<DesignationDao> spec = filter != null
                ? jpaFilterConverter.toSpec(filter, DesignationDao.class)
                : Specification.anyOf();

        Page<DesignationDao> accounts = designationRepo.findAll(spec, pageable);
        return accounts.stream().map(DesignationDto::toDto).toList();
    }

    @Override
    @Transactional
    public @Nullable DesignationDto save(DesignationDto value) {
        DesignationDao accountDao = value.id() != null && value.id() > 0
                ? designationRepo.getReferenceById(value.id())
                : new DesignationDao();
//        accountDao.setName(value.name());
//        accountDao.setCategory(value.category());
//        accountDao.setPrice(value.price());

        DesignationDto.toEntity(value, accountDao);
        return DesignationDto.toDto(designationRepo.save(accountDao));
    }

    @Override
    public void delete(Long id) {
        designationRepo.deleteById(id);
    }
}