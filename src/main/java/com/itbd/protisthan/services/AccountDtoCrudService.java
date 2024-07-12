//package com.itbd.protisthan.services;
//
//import com.itbd.protisthan.db.dao.AccountDao;
//import com.itbd.protisthan.db.dto.AccountDto;
//import com.itbd.protisthan.db.repos.AccountRepository;
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
//public class AccountDtoCrudService implements CrudService<AccountDto, Long> {
//    private final JpaFilterConverter jpaFilterConverter;
//    private final AccountRepository accountRepo;
//
//    public AccountDtoCrudService(AccountRepository accountRepo, JpaFilterConverter jpaFilterConverter) {
//        this.accountRepo = accountRepo;
//        this.jpaFilterConverter = jpaFilterConverter;
//    }
//
//    @Override
//    @Nonnull
//    public List<@Nonnull AccountDto> list(Pageable pageable, @Nullable Filter filter) {
//        // Basic list implementation that only covers pagination,
//        // but not sorting or filtering
//        Specification<AccountDao> spec = filter != null
//                ? jpaFilterConverter.toSpec(filter, AccountDao.class)
//                : Specification.anyOf();
//
//        Page<AccountDao> accounts = accountRepo.findAll(spec, pageable);
//        return accounts.stream().map(AccountDto::toDto).toList();
//    }
//
//    @Override
//    public @Nullable AccountDto save(AccountDto value) {
//        AccountDao accountDao = value.id() != null && value.id() > 0
//                ? accountRepo.getReferenceById(value.id())
//                : new AccountDao();
////        accountDao.setName(value.name());
////        accountDao.setCategory(value.category());
////        accountDao.setPrice(value.price());
//
//        AccountDto.toEntity(value, accountDao);
//        return AccountDto.toDto(accountRepo.save(accountDao));
//    }
//
//    @Override
//    public void delete(Long id) {
//        accountRepo.deleteById(id);
//    }
//}