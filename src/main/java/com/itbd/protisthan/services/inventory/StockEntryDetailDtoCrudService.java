package com.itbd.protisthan.services.inventory;

import com.itbd.protisthan.db.dao.StockEntryDetailDao;
import com.itbd.protisthan.db.dto.StockEntryDetailDto;
import com.itbd.protisthan.db.repos.StockEntryDetailRepository;
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
public class StockEntryDetailDtoCrudService implements CrudService<StockEntryDetailDto, Long> {
    private final JpaFilterConverter jpaFilterConverter;
    private final StockEntryDetailRepository stockEntryDetailRepo;

    public StockEntryDetailDtoCrudService(StockEntryDetailRepository stockEntryDetailRepo, JpaFilterConverter jpaFilterConverter) {
        this.stockEntryDetailRepo = stockEntryDetailRepo;
        this.jpaFilterConverter = jpaFilterConverter;
    }

    @Override
    @Nonnull
    public List<@Nonnull StockEntryDetailDto> list(Pageable pageable, @Nullable Filter filter) {
        // Basic list implementation that only covers pagination,
        // but not sorting or filtering
        Specification<StockEntryDetailDao> spec = filter != null
                ? jpaFilterConverter.toSpec(filter, StockEntryDetailDao.class)
                : Specification.anyOf();

        Page<StockEntryDetailDao> accounts = stockEntryDetailRepo.findAll(spec, pageable);
        return accounts.stream().map(StockEntryDetailDto::toDto).toList();
    }

    @Override
    @Transactional
    public @Nullable StockEntryDetailDto save(StockEntryDetailDto value) {
        StockEntryDetailDao accountDao = value.id() != null && value.id() > 0
                ? stockEntryDetailRepo.getReferenceById(value.id())
                : new StockEntryDetailDao();
//        accountDao.setName(value.name());
//        accountDao.setCategory(value.category());
//        accountDao.setPrice(value.price());

        StockEntryDetailDto.toEntity(value, accountDao);
        return StockEntryDetailDto.toDto(stockEntryDetailRepo.save(accountDao));
    }

    @Override
    public void delete(Long id) {
        stockEntryDetailRepo.deleteById(id);
    }
}