package com.itbd.protisthan.services.sale;

import com.itbd.protisthan.db.dao.ItemDao;
import com.itbd.protisthan.db.dao.OrderDao;
import com.itbd.protisthan.db.dao.OrderDetailDao;
import com.itbd.protisthan.db.dto.OrderDto;
import com.itbd.protisthan.db.repos.ItemRepository;
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
import java.util.Set;
import java.util.stream.Collectors;

@BrowserCallable
@AnonymousAllowed
public class OrderDtoCrudService implements CrudService<OrderDto, Long> {
    private final JpaFilterConverter jpaFilterConverter;
    private final OrderRepository orderRepo;
    private final ItemRepository itemRepo;

    public OrderDtoCrudService(OrderRepository orderRepo, ItemRepository itemRepo, JpaFilterConverter jpaFilterConverter) {
        this.orderRepo = orderRepo;
        this.itemRepo = itemRepo;
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
        OrderDao order = value.id() != null && value.id() > 0
                ? orderRepo.getReferenceById(value.id())
                : new OrderDao();

        OrderDto.toEntity(value, order);
        final Set<OrderDetailDao> items = value.orderDetails().stream().map(e -> {
            ItemDao item = itemRepo.getReferenceById(e.getId().getIdItemKey());
            e.setOrder(order);
            e.setItem(item);
            return e;
        }).collect(Collectors.toSet());
        if (order.getOrderDetails() == null || order.getOrderDetails().isEmpty()) order.setOrderDetails(items);
        else order.getOrderDetails().addAll(items);
        return OrderDto.toDto(orderRepo.save(order));
    }

    @Override
    public void delete(Long id) {
        orderRepo.deleteById(id);
    }
}