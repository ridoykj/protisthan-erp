package com.itbd.protisthan.services.inventory;

import com.itbd.protisthan.db.dao.ItemDao;
import com.itbd.protisthan.db.dto.ItemDto;
import com.itbd.protisthan.db.repos.ItemRepository;
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
import java.util.Optional;

@BrowserCallable
@AnonymousAllowed
public class ItemDtoCrudService implements CrudService<ItemDto, Long> {
    private final JpaFilterConverter jpaFilterConverter;
    private final ItemRepository itemRepo;

    public ItemDtoCrudService(ItemRepository itemRepo, JpaFilterConverter jpaFilterConverter) {
        this.itemRepo = itemRepo;
        this.jpaFilterConverter = jpaFilterConverter;
    }

    @Override
    @Nonnull
    public List<@Nonnull ItemDto> list(Pageable pageable, @Nullable Filter filter) {
        // Basic list implementation that only covers pagination,
        // but not sorting or filtering
        Specification<ItemDao> spec = filter != null
                ? jpaFilterConverter.toSpec(filter, ItemDao.class)
                : Specification.anyOf();

        Page<ItemDao> accounts = itemRepo.findAll(spec, pageable);
        return accounts.stream().map(ItemDto::toDto).toList();
    }

    public ItemDto getItem(Long id){
        Optional<ItemDao> item = itemRepo.findById(id);
       return ItemDto.toDto(item.get());
    }

    @Override
    @Transactional
    public @Nullable ItemDto save(ItemDto value) {
        ItemDao accountDao = value.id() != null && value.id() > 0
                ? itemRepo.getReferenceById(value.id())
                : new ItemDao();
//        accountDao.setName(value.name());
//        accountDao.setCategory(value.category());
//        accountDao.setPrice(value.price());

        ItemDto.toEntity(value, accountDao);
        return ItemDto.toDto(itemRepo.save(accountDao));
    }



    @Override
    public void delete(Long id) {
        itemRepo.deleteById(id);
    }
}