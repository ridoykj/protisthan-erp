//package com.itbd.protisthan.services.user;
//
//import com.itbd.protisthan.db.dao.UserEmailDao;
//import com.itbd.protisthan.db.dto.UserEmailDto;
//import com.itbd.protisthan.db.repos.UserEmailRepository;
//import com.itbd.protisthan.db.repos.UserRepository;
//import com.vaadin.flow.server.auth.AnonymousAllowed;
//import com.vaadin.hilla.BrowserCallable;
//import com.vaadin.hilla.Nonnull;
//import com.vaadin.hilla.Nullable;
//import com.vaadin.hilla.crud.CrudService;
//import com.vaadin.hilla.crud.JpaFilterConverter;
//import com.vaadin.hilla.crud.filter.Filter;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.Pageable;
//import org.springframework.data.jpa.domain.Specification;
//
//import java.util.List;
//
//@BrowserCallable
//@AnonymousAllowed
//public class UserEmailDtoCrudService implements CrudService<UserEmailDto, Long> {
//    private final JpaFilterConverter jpaFilterConverter;
//    private final UserEmailRepository userRepo;
//
//    public UserEmailDtoCrudService(UserEmailRepository userRepo, JpaFilterConverter jpaFilterConverter) {
//        this.userRepo = userRepo;
//        this.jpaFilterConverter = jpaFilterConverter;
//    }
//
//    @Override
//    @Nonnull
//    public List<@Nonnull UserEmailDto> list(Pageable pageable, @Nullable Filter filter) {
//        // Basic list implementation that only covers pagination,
//        // but not sorting or filtering
//        Specification<UserEmailDao> spec = filter != null
//                ? jpaFilterConverter.toSpec(filter, UserEmailDao.class)
//                : Specification.anyOf();
//
//        Page<UserEmailDao> accounts = userRepo.findAll(spec, pageable);
//        return accounts.stream().map(UserEmailDto::toDto).toList();
//    }
//
//    @Override
//    public @Nullable UserEmailDto save(UserEmailDto value) {
//        UserEmailDao accountDao = value.id() != null && value.id() > 0
//                ? userRepo.getReferenceById(value.id())
//                : new UserEmailDao();
////        accountDao.setName(value.name());
////        accountDao.setCategory(value.category());
////        accountDao.setPrice(value.price());
//
//        UserEmailDto.toEntity(value, accountDao);
//        return UserEmailDto.toDto(userRepo.save(accountDao));
//    }
//
//    @Override
//    public void delete(Long id) {
//        userRepo.deleteById(id);
//    }
//}