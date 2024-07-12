//package com.itbd.protisthan.services.user;
//
//import com.itbd.protisthan.db.dao.UserTypeDao;
//import com.itbd.protisthan.db.dto.UserTypeDto;
//import com.itbd.protisthan.db.dto.UserTypeDto;
//import com.itbd.protisthan.db.repos.UserRepository;
//import com.itbd.protisthan.db.repos.UserTypeRepository;
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
//public class UserTypeDtoCrudService implements CrudService<UserTypeDto, Long> {
//    private final JpaFilterConverter jpaFilterConverter;
//    private final UserTypeRepository userRepo;
//
//    public UserTypeDtoCrudService(UserTypeRepository userRepo, JpaFilterConverter jpaFilterConverter) {
//        this.userRepo = userRepo;
//        this.jpaFilterConverter = jpaFilterConverter;
//    }
//
//    @Override
//    @Nonnull
//    public List<@Nonnull UserTypeDto> list(Pageable pageable, @Nullable Filter filter) {
//        // Basic list implementation that only covers pagination,
//        // but not sorting or filtering
//        Specification<UserTypeDao> spec = filter != null
//                ? jpaFilterConverter.toSpec(filter, UserTypeDao.class)
//                : Specification.anyOf();
//
//        Page<UserTypeDao> accounts = userRepo.findAll(spec, pageable);
//        return accounts.stream().map(UserTypeDto::toDto).toList();
//    }
//
//    @Override
//    public @Nullable UserTypeDto save(UserTypeDto value) {
//        UserTypeDao accountDao = value.id() != null && value.id() > 0
//                ? userRepo.getReferenceById(value.id())
//                : new UserTypeDao();
////        accountDao.setName(value.name());
////        accountDao.setCategory(value.category());
////        accountDao.setPrice(value.price());
//
//        UserTypeDto.toEntity(value, accountDao);
//        return UserTypeDto.toDto(userRepo.save(accountDao));
//    }
//
//    @Override
//    public void delete(Long id) {
//        userRepo.deleteById(id);
//    }
//}