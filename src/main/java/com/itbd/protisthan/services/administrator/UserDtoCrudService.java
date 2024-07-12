package com.itbd.protisthan.services.administrator;

import com.itbd.protisthan.db.dao.UserDao;
import com.itbd.protisthan.db.dto.UserDto;
import com.itbd.protisthan.db.repos.UserRepository;
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
public class UserDtoCrudService implements CrudService<UserDto, Long> {
    private final JpaFilterConverter jpaFilterConverter;
    private final UserRepository userRepo;

    public UserDtoCrudService(UserRepository userRepo, JpaFilterConverter jpaFilterConverter) {
        this.userRepo = userRepo;
        this.jpaFilterConverter = jpaFilterConverter;
    }

    @Override
    @Nonnull
    public List<@Nonnull UserDto> list(Pageable pageable, @Nullable Filter filter) {
        // Basic list implementation that only covers pagination,
        // but not sorting or filtering
        Specification<UserDao> spec = filter != null
                ? jpaFilterConverter.toSpec(filter, UserDao.class)
                : Specification.anyOf();

        Page<UserDao> accounts = userRepo.findAll(spec, pageable);
        return accounts.stream().map(UserDto::toDto).toList();
    }

    @Override
    @Transactional
    public @Nullable UserDto save(UserDto value) {
        UserDao accountDao = value.id() != null && value.id() > 0
                ? userRepo.getReferenceById(value.id())
                : new UserDao();
//        accountDao.setName(value.name());
//        accountDao.setCategory(value.category());
//        accountDao.setPrice(value.price());

        UserDto.toEntity(value, accountDao);
        return UserDto.toDto(userRepo.save(accountDao));
    }

    @Override
    public void delete(Long id) {
        userRepo.deleteById(id);
    }
}