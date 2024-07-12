//package com.itbd.protisthan.services.configration;
//
//import com.itbd.protisthan.db.dao.LocationDao;
//import com.itbd.protisthan.db.dto.LocationDto;
//import com.itbd.protisthan.db.repos.LocationRepository;
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
//public class LocationDtoCrudService implements CrudService<LocationDto, Long> {
//    private final JpaFilterConverter jpaFilterConverter;
//    private final LocationRepository userRepo;
//
//    public LocationDtoCrudService(LocationRepository userRepo, JpaFilterConverter jpaFilterConverter) {
//        this.userRepo = userRepo;
//        this.jpaFilterConverter = jpaFilterConverter;
//    }
//
//    @Override
//    @Nonnull
//    public List<@Nonnull LocationDto> list(Pageable pageable, @Nullable Filter filter) {
//        // Basic list implementation that only covers pagination,
//        // but not sorting or filtering
//        Specification<LocationDao> spec = filter != null
//                ? jpaFilterConverter.toSpec(filter, LocationDao.class)
//                : Specification.anyOf();
//
//        Page<LocationDao> accounts = userRepo.findAll(spec, pageable);
//        return accounts.stream().map(LocationDto::toDto).toList();
//    }
//
//    @Override
//    public @Nullable LocationDto save(LocationDto value) {
//        LocationDao accountDao = value.id() != null && value.id() > 0
//                ? userRepo.getReferenceById(value.id())
//                : new LocationDao();
////        accountDao.setName(value.name());
////        accountDao.setCategory(value.category());
////        accountDao.setPrice(value.price());
//
//        LocationDto.toEntity(value, accountDao);
//        return LocationDto.toDto(userRepo.save(accountDao));
//    }
//
//    @Override
//    public void delete(Long id) {
//        userRepo.deleteById(id);
//    }
//}