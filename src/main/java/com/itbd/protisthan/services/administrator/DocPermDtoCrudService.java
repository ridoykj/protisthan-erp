package com.itbd.protisthan.services.administrator;

import com.itbd.protisthan.db.dao.DocPermDao;
import com.itbd.protisthan.db.dao.iddao.DocPremId;
import com.itbd.protisthan.db.dto.DocPermDto;
import com.itbd.protisthan.db.repos.DocPermRepository;
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
public class DocPermDtoCrudService implements CrudService<DocPermDto, DocPremId> {
    private final JpaFilterConverter jpaFilterConverter;
    private final DocPermRepository docPermRepo;

    public DocPermDtoCrudService(DocPermRepository docPermRepo, JpaFilterConverter jpaFilterConverter) {
        this.docPermRepo = docPermRepo;
        this.jpaFilterConverter = jpaFilterConverter;
    }

    @Override
    @Nonnull
    public List<@Nonnull DocPermDto> list(Pageable pageable, @Nullable Filter filter) {
        // Basic list implementation that only covers pagination,
        // but not sorting or filtering
        Specification<DocPermDao> spec = filter != null
                ? jpaFilterConverter.toSpec(filter, DocPermDao.class)
                : Specification.anyOf();

        Page<DocPermDao> accounts = docPermRepo.findAll(spec, pageable);
        return accounts.stream().map(DocPermDto::toDto).toList();
    }

    @Override
    @Transactional
    public @Nullable DocPermDto save(DocPermDto value) {
//        DocPermDao accountDao = value.id() != null && !value.id().getDocTypeKey().isEmpty() && !value.id().getRoleKey().isEmpty()
//                ? docPermRepo.getReferenceById(value.id())
//                : new DocPermDao();

        DocPermDao accountDao =  docPermRepo.existsById(value.id())
                ? docPermRepo.getReferenceById(value.id())
                : new DocPermDao();

        DocPermDto.toEntity(value, accountDao);
        return DocPermDto.toDto(docPermRepo.save(accountDao));
    }

    @Override
    public void delete(DocPremId id) {
        docPermRepo.deleteById(id);
    }
}