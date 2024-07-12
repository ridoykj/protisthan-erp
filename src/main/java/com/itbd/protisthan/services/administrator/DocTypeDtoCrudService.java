package com.itbd.protisthan.services.administrator;

import com.itbd.protisthan.db.dao.DocTypeDao;
import com.itbd.protisthan.db.dto.DocTypeDto;
import com.itbd.protisthan.db.repos.DocTypeRepository;
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
public class DocTypeDtoCrudService implements CrudService<DocTypeDto, String> {
    private final JpaFilterConverter jpaFilterConverter;
    private final DocTypeRepository docTypeRepo;

    public DocTypeDtoCrudService(DocTypeRepository docTypeRepo, JpaFilterConverter jpaFilterConverter) {
        this.docTypeRepo = docTypeRepo;
        this.jpaFilterConverter = jpaFilterConverter;
    }

    @Override
    @Nonnull
    public List<@Nonnull DocTypeDto> list(Pageable pageable, @Nullable Filter filter) {
        // Basic list implementation that only covers pagination,
        // but not sorting or filtering
        Specification<DocTypeDao> spec = filter != null
                ? jpaFilterConverter.toSpec(filter, DocTypeDao.class)
                : Specification.anyOf();

        Page<DocTypeDao> accounts = docTypeRepo.findAll(spec, pageable);
        return accounts.stream().map(DocTypeDto::toDto).toList();
    }

    @Override
    @Transactional
    public @Nullable DocTypeDto save(DocTypeDto value) {
        DocTypeDao accountDao = value.id() != null && !value.id().isEmpty()
                ? docTypeRepo.getReferenceById(value.id())
                : new DocTypeDao();
//        accountDao.setName(value.name());
//        accountDao.setCategory(value.category());
//        accountDao.setPrice(value.price());

        DocTypeDto.toEntity(value, accountDao);
        return DocTypeDto.toDto(docTypeRepo.save(accountDao));
    }

    @Override
    public void delete(String id) {
        docTypeRepo.deleteById(id);
    }
}