package com.itbd.protisthan.services.administrator;

import com.itbd.protisthan.db.dao.UserTypeDao;
import com.itbd.protisthan.db.dto.UserTypeDto;
import com.itbd.protisthan.db.repos.UserTypeRepository;
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
public class UserTypeDtoCrudService implements CrudService<UserTypeDto, String> {
    private final JpaFilterConverter jpaFilterConverter;
    private final UserTypeRepository userTypeRepo;

    public UserTypeDtoCrudService(UserTypeRepository userTypeRepo, JpaFilterConverter jpaFilterConverter) {
        this.userTypeRepo = userTypeRepo;
        this.jpaFilterConverter = jpaFilterConverter;
    }

    @Override
    @Nonnull
    public List<@Nonnull UserTypeDto> list(Pageable pageable, @Nullable Filter filter) {
        // Basic list implementation that only covers pagination,
        // but not sorting or filtering
        Specification<UserTypeDao> spec = filter != null
                ? jpaFilterConverter.toSpec(filter, UserTypeDao.class)
                : Specification.anyOf();

        Page<UserTypeDao> accounts = userTypeRepo.findAll(spec, pageable);
        return accounts.stream().map(UserTypeDto::toDto).toList();
    }

    @Override
    @Transactional
    public @Nullable UserTypeDto save(UserTypeDto value) {
        UserTypeDao accountDao = value.id() != null && !value.id().isEmpty()
                ? userTypeRepo.getReferenceById(value.id())
                : new UserTypeDao();
//        accountDao.setName(value.name());
//        accountDao.setCategory(value.category());
//        accountDao.setPrice(value.price());

        UserTypeDto.toEntity(value, accountDao);
        return UserTypeDto.toDto(userTypeRepo.save(accountDao));
    }

    @Override
    public void delete(String id) {
        userTypeRepo.deleteById(id);
    }
}