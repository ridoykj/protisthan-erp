package com.itbd.protisthan.services.inventory;

import com.itbd.protisthan.db.dao.CategoryDao;
import com.itbd.protisthan.db.dto.CategoryDto;
import com.itbd.protisthan.db.repos.CategoryRepository;
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
public class CategoryDtoCrudService implements CrudService<CategoryDto, Long> {
    private final JpaFilterConverter jpaFilterConverter;
    private final CategoryRepository categoryRepo;

    public CategoryDtoCrudService(CategoryRepository categoryRepo, JpaFilterConverter jpaFilterConverter) {
        this.categoryRepo = categoryRepo;
        this.jpaFilterConverter = jpaFilterConverter;
    }

    @Override
    @Nonnull
    public List<@Nonnull CategoryDto> list(Pageable pageable, @Nullable Filter filter) {
        // Basic list implementation that only covers pagination,
        // but not sorting or filtering
        Specification<CategoryDao> spec = filter != null
                ? jpaFilterConverter.toSpec(filter, CategoryDao.class)
                : Specification.anyOf();

        Page<CategoryDao> accounts = categoryRepo.findAll(spec, pageable);
        return accounts.stream().map(CategoryDto::toDto).toList();
    }

    @Override
    @Transactional
    public @Nullable CategoryDto save(CategoryDto value) {
        CategoryDao accountDao = value.id() != null && value.id() > 0
                ? categoryRepo.getReferenceById(value.id())
                : new CategoryDao();
//        accountDao.setName(value.name());
//        accountDao.setCategory(value.category());
//        accountDao.setPrice(value.price());

        CategoryDto.toEntity(value, accountDao);
        return CategoryDto.toDto(categoryRepo.save(accountDao));
    }

    @Override
    public void delete(Long id) {
        categoryRepo.deleteById(id);
    }
}