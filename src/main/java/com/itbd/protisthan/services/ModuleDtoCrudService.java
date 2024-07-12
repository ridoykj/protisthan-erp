//package com.itbd.protisthan.services;
//
//import com.itbd.protisthan.db.dao.ModuleDao;
//import com.itbd.protisthan.db.dto.ModuleDto;
//import com.itbd.protisthan.db.repos.ModuleRepository;
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
//public class ModuleDtoCrudService implements CrudService<ModuleDto, String> {
//    private final JpaFilterConverter jpaFilterConverter;
//    private final ModuleRepository moduleRepo;
//
//    public ModuleDtoCrudService(ModuleRepository moduleRepo, JpaFilterConverter jpaFilterConverter) {
//        this.moduleRepo = moduleRepo;
//        this.jpaFilterConverter = jpaFilterConverter;
//    }
//
//    @Override
//    @Nonnull
//    public List<@Nonnull ModuleDto> list(Pageable pageable, @Nullable Filter filter) {
//        // Basic list implementation that only covers pagination,
//        // but not sorting or filtering
//        Specification<ModuleDao> spec = filter != null
//                ? jpaFilterConverter.toSpec(filter, ModuleDao.class)
//                : Specification.anyOf();
//
//        Page<ModuleDao> products = moduleRepo.findAll(spec, pageable);
//        return products.stream().map(ModuleDto::toDto).toList();
//    }
//
//    @Override
//    public @Nullable ModuleDto save(ModuleDto value) {
//        ModuleDao accountDao = value.id() != null && !value.id().isEmpty()
//                ? moduleRepo.getReferenceById(value.id())
//                : new ModuleDao();
////        accountDao.setName(value.name());
////        accountDao.setCategory(value.category());
////        accountDao.setPrice(value.price());
//
//        ModuleDto.toEntity(value, accountDao);
//        return ModuleDto.toDto(moduleRepo.save(accountDao));
//    }
//
//    @Override
//    public void delete(String id) {
//        moduleRepo.deleteById(id);
//    }
//}