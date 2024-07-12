package com.itbd.protisthan.services.inventory;

import com.itbd.protisthan.db.dao.ItemGroupDao;
import com.itbd.protisthan.db.dto.ItemGroupDto;
import com.itbd.protisthan.db.repos.ItemGroupRepository;
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
public class ItemGroupDtoCrudService implements CrudService<ItemGroupDto, Long> {
    private final JpaFilterConverter jpaFilterConverter;
    private final ItemGroupRepository itemGroupRepo;

    public ItemGroupDtoCrudService(ItemGroupRepository itemGroupRepo, JpaFilterConverter jpaFilterConverter) {
        this.itemGroupRepo = itemGroupRepo;
        this.jpaFilterConverter = jpaFilterConverter;
    }

    @Override
    @Nonnull
    public List<@Nonnull ItemGroupDto> list(Pageable pageable, @Nullable Filter filter) {
        // Basic list implementation that only covers pagination,
        // but not sorting or filtering
        Specification<ItemGroupDao> spec = filter != null
                ? jpaFilterConverter.toSpec(filter, ItemGroupDao.class)
                : Specification.anyOf();

        Page<ItemGroupDao> accounts = itemGroupRepo.findAll(spec, pageable);
        return accounts.stream().map(ItemGroupDto::toDto).toList();
    }

    @Override
    @Transactional
    public @Nullable ItemGroupDto save(ItemGroupDto value) {
        ItemGroupDao accountDao = value.id() != null && value.id() > 0
                ? itemGroupRepo.getReferenceById(value.id())
                : new ItemGroupDao();
//        accountDao.setName(value.name());
//        accountDao.setCategory(value.category());
//        accountDao.setPrice(value.price());

        ItemGroupDto.toEntity(value, accountDao);
        return ItemGroupDto.toDto(itemGroupRepo.save(accountDao));
    }

    @Override
    public void delete(Long id) {
        itemGroupRepo.deleteById(id);
    }
}