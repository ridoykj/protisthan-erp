package com.itbd.protisthan.services.sale;

import com.itbd.protisthan.db.dao.OrderDao;
import com.itbd.protisthan.db.dto.OrderDto;
import com.itbd.protisthan.db.repos.OrderRepository;
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
public class OrderDtoCrudService implements CrudService<OrderDto, Long> {
    private final JpaFilterConverter jpaFilterConverter;
    private final OrderRepository orderRepo;

    public OrderDtoCrudService(OrderRepository orderRepo, JpaFilterConverter jpaFilterConverter) {
        this.orderRepo = orderRepo;
        this.jpaFilterConverter = jpaFilterConverter;
    }

    @Override
    @Nonnull
    public List<@Nonnull OrderDto> list(Pageable pageable, @Nullable Filter filter) {
        // Basic list implementation that only covers pagination,
        // but not sorting or filtering
        Specification<OrderDao> spec = filter != null
                ? jpaFilterConverter.toSpec(filter, OrderDao.class)
                : Specification.anyOf();

        Page<OrderDao> accounts = orderRepo.findAll(spec, pageable);
        return accounts.stream().map(OrderDto::toDto).toList();
    }

    @Override
    @Transactional
    public @Nullable OrderDto save(OrderDto value) {
        OrderDao accountDao = value.id() != null && value.id() > 0
                ? orderRepo.getReferenceById(value.id())
                : new OrderDao();
//        accountDao.setName(value.name());
//        accountDao.setCategory(value.category());
//        accountDao.setPrice(value.price());

        OrderDto.toEntity(value, accountDao);
        return OrderDto.toDto(orderRepo.save(accountDao));
    }

    @Override
    public void delete(Long id) {
        orderRepo.deleteById(id);
    }
}