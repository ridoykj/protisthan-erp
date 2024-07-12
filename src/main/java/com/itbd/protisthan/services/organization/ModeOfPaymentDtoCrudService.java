package com.itbd.protisthan.services.organization;

import com.itbd.protisthan.db.dao.ModeOfPaymentDao;
import com.itbd.protisthan.db.dto.ModeOfPaymentDto;
import com.itbd.protisthan.db.repos.ModeOfPaymentRepository;
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
public class ModeOfPaymentDtoCrudService implements CrudService<ModeOfPaymentDto, Long> {
    private final JpaFilterConverter jpaFilterConverter;
    private final ModeOfPaymentRepository modeOfPaymentRepo;

    public ModeOfPaymentDtoCrudService(ModeOfPaymentRepository modeOfPaymentRepo, JpaFilterConverter jpaFilterConverter) {
        this.modeOfPaymentRepo = modeOfPaymentRepo;
        this.jpaFilterConverter = jpaFilterConverter;
    }

    @Override
    @Nonnull
    public List<@Nonnull ModeOfPaymentDto> list(Pageable pageable, @Nullable Filter filter) {
        // Basic list implementation that only covers pagination,
        // but not sorting or filtering
        Specification<ModeOfPaymentDao> spec = filter != null
                ? jpaFilterConverter.toSpec(filter, ModeOfPaymentDao.class)
                : Specification.anyOf();

        Page<ModeOfPaymentDao> accounts = modeOfPaymentRepo.findAll(spec, pageable);
        return accounts.stream().map(ModeOfPaymentDto::toDto).toList();
    }

    @Override
    @Transactional
    public @Nullable ModeOfPaymentDto save(ModeOfPaymentDto value) {
        ModeOfPaymentDao accountDao = value.id() != null && value.id() > 0
                ? modeOfPaymentRepo.getReferenceById(value.id())
                : new ModeOfPaymentDao();
//        accountDao.setName(value.name());
//        accountDao.setCategory(value.category());
//        accountDao.setPrice(value.price());

        ModeOfPaymentDto.toEntity(value, accountDao);
        return ModeOfPaymentDto.toDto(modeOfPaymentRepo.save(accountDao));
    }

    @Override
    public void delete(Long id) {
        modeOfPaymentRepo.deleteById(id);
    }
}