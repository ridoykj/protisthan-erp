package com.itbd.protisthan.services.sale;

import com.itbd.protisthan.db.dao.OrderDetailDao;
import com.itbd.protisthan.db.dao.iddao.OrderDetailId;
import com.itbd.protisthan.db.dto.OrderDetailDto;
import com.itbd.protisthan.db.repos.OrderDetailRepository;
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
public class OrderDetailDtoCrudService implements CrudService<OrderDetailDto, OrderDetailId> {
    private final JpaFilterConverter jpaFilterConverter;
    private final OrderDetailRepository orderDetailRepo;

    public OrderDetailDtoCrudService(OrderDetailRepository orderDetailRepo, JpaFilterConverter jpaFilterConverter) {
        this.orderDetailRepo = orderDetailRepo;
        this.jpaFilterConverter = jpaFilterConverter;
    }

    @Override
    @Nonnull
    public List<@Nonnull OrderDetailDto> list(Pageable pageable, @Nullable Filter filter) {
        // Basic list implementation that only covers pagination,
        // but not sorting or filtering
        Specification<OrderDetailDao> spec = filter != null
                ? jpaFilterConverter.toSpec(filter, OrderDetailDao.class)
                : Specification.anyOf();

        Page<OrderDetailDao> accounts = orderDetailRepo.findAll(spec, pageable);
        return accounts.stream().map(OrderDetailDto::toDto).toList();
    }

    @Override
    @Transactional
    public @Nullable OrderDetailDto save(OrderDetailDto value) {
//        OrderDetailDao accountDao = value.id() != null && value.id() > 0
//                ? orderRepo.getReferenceById(value.id())
//                : new OrderDetailDao();
//        accountDao.setName(value.name());
//        accountDao.setCategory(value.category());
//        accountDao.setPrice(value.price());

        OrderDetailDao accountDao = orderDetailRepo.existsById(value.id())
                ? orderDetailRepo.getReferenceById(value.id())
                : new OrderDetailDao();

        OrderDetailDto.toEntity(value, accountDao);
        return OrderDetailDto.toDto(orderDetailRepo.save(accountDao));
    }

    @Override
    public void delete(OrderDetailId id) {
        orderDetailRepo.deleteById(id);
    }
}