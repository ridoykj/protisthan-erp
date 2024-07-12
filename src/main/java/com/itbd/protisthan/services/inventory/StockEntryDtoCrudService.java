package com.itbd.protisthan.services.inventory;

import com.itbd.protisthan.db.dao.StockEntryDao;
import com.itbd.protisthan.db.dto.StockEntryDto;
import com.itbd.protisthan.db.repos.StockEntryRepository;
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
public class StockEntryDtoCrudService implements CrudService<StockEntryDto, Long> {
    private final JpaFilterConverter jpaFilterConverter;
    private final StockEntryRepository stockEntryRepo;

    public StockEntryDtoCrudService(StockEntryRepository stockEntryRepo, JpaFilterConverter jpaFilterConverter) {
        this.stockEntryRepo = stockEntryRepo;
        this.jpaFilterConverter = jpaFilterConverter;
    }

    @Override
    @Nonnull
    public List<@Nonnull StockEntryDto> list(Pageable pageable, @Nullable Filter filter) {
        // Basic list implementation that only covers pagination,
        // but not sorting or filtering
        Specification<StockEntryDao> spec = filter != null
                ? jpaFilterConverter.toSpec(filter, StockEntryDao.class)
                : Specification.anyOf();

        Page<StockEntryDao> accounts = stockEntryRepo.findAll(spec, pageable);
        return accounts.stream().map(StockEntryDto::toDto).toList();
    }

    @Override
    @Transactional
    public @Nullable StockEntryDto save(StockEntryDto value) {
        StockEntryDao accountDao = value.id() != null && value.id() > 0
                ? stockEntryRepo.getReferenceById(value.id())
                : new StockEntryDao();
//        accountDao.setName(value.name());
//        accountDao.setCategory(value.category());
//        accountDao.setPrice(value.price());

        StockEntryDto.toEntity(value, accountDao);
        return StockEntryDto.toDto(stockEntryRepo.save(accountDao));
    }

    @Override
    public void delete(Long id) {
        stockEntryRepo.deleteById(id);
    }
}