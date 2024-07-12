package com.itbd.protisthan.services.inventory;

import com.itbd.protisthan.db.dao.StockEntryTypeDao;
import com.itbd.protisthan.db.dto.StockEntryTypeDto;
import com.itbd.protisthan.db.repos.StockEntryTypeRepository;
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
public class StockEntryTypeDtoCrudService implements CrudService<StockEntryTypeDto, String> {
    private final JpaFilterConverter jpaFilterConverter;
    private final StockEntryTypeRepository stockEntryTypeRepo;

    public StockEntryTypeDtoCrudService(StockEntryTypeRepository stockEntryTypeRepo, JpaFilterConverter jpaFilterConverter) {
        this.stockEntryTypeRepo = stockEntryTypeRepo;
        this.jpaFilterConverter = jpaFilterConverter;
    }

    @Transactional
    @Override
    @Nonnull
    public List<@Nonnull StockEntryTypeDto> list(Pageable pageable, @Nullable Filter filter) {
        // Basic list implementation that only covers pagination,
        // but not sorting or filtering
        Specification<StockEntryTypeDao> spec = filter != null
                ? jpaFilterConverter.toSpec(filter, StockEntryTypeDao.class)
                : Specification.anyOf();

        Page<StockEntryTypeDao> accounts = stockEntryTypeRepo.findAll(spec, pageable);
        return accounts.stream().map(StockEntryTypeDto::toDto).toList();
    }

    @Override
    @Transactional
    public @Nullable StockEntryTypeDto save(StockEntryTypeDto value) {
        StockEntryTypeDao accountDao = value.id() != null && !value.id().isEmpty()
                ? stockEntryTypeRepo.getReferenceById(value.id())
                : new StockEntryTypeDao();
//        accountDao.setName(value.name());
//        accountDao.setCategory(value.category());
//        accountDao.setPrice(value.price());

        StockEntryTypeDto.toEntity(value, accountDao);
        return StockEntryTypeDto.toDto(stockEntryTypeRepo.save(accountDao));
    }

    @Override
    public void delete(String id) {
        stockEntryTypeRepo.deleteById(id);
    }
}