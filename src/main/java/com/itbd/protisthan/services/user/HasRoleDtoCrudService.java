//package com.itbd.protisthan.services.user;
//
//import com.itbd.protisthan.db.dao.HasRoleDao;
//import com.itbd.protisthan.db.dto.HasRoleDto;
//import com.itbd.protisthan.db.repos.HasRoleRepository;
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
//public class HasRoleDtoCrudService implements CrudService<HasRoleDto, String> {
//    private final JpaFilterConverter jpaFilterConverter;
//    private final HasRoleRepository userRepo;
//
//    public HasRoleDtoCrudService(HasRoleRepository userRepo, JpaFilterConverter jpaFilterConverter) {
//        this.userRepo = userRepo;
//        this.jpaFilterConverter = jpaFilterConverter;
//    }
//
//    @Override
//    @Nonnull
//    public List<@Nonnull HasRoleDto> list(Pageable pageable, @Nullable Filter filter) {
//        // Basic list implementation that only covers pagination,
//        // but not sorting or filtering
//        Specification<HasRoleDao> spec = filter != null
//                ? jpaFilterConverter.toSpec(filter, HasRoleDao.class)
//                : Specification.anyOf();
//
//        Page<HasRoleDao> accounts = userRepo.findAll(spec, pageable);
//        return accounts.stream().map(HasRoleDto::toDto).toList();
//    }
//
//    @Override
//    public @Nullable HasRoleDto save(HasRoleDto value) {
//        HasRoleDao accountDao = value.name() != null && !value.name().isEmpty()
//                ? userRepo.getReferenceById(value.name())
//                : new HasRoleDao();
////        accountDao.setName(value.name());
////        accountDao.setCategory(value.category());
////        accountDao.setPrice(value.price());
//
//        HasRoleDto.toEntity(value, accountDao);
//        return HasRoleDto.toDto(userRepo.save(accountDao));
//    }
//
//    @Override
//    public void delete(String id) {
//        userRepo.deleteById(id);
//    }
//}