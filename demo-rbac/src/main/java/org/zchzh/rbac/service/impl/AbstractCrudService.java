package org.zchzh.rbac.service.impl;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.util.CollectionUtils;
import org.zchzh.rbac.service.BaseCrudService;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 * @author zengchzh
 * @date 2021/5/11
 */
public abstract class AbstractCrudService<ENTITY, ID> implements BaseCrudService<ENTITY, ID> {

    private final JpaRepository<ENTITY, ID> repo;

    protected AbstractCrudService(JpaRepository<ENTITY, ID> repo) {
        this.repo = repo;
    }

    @Override
    public List<ENTITY> list() {
        return repo.findAll();
    }

    @Override
    public List<ENTITY> list(Sort sort) {
        return repo.findAll(sort);
    }

    @Override
    public Page<ENTITY> list(Pageable pageable) {
        return repo.findAll(pageable);
    }

    @Override
    public List<ENTITY> list(Example<ENTITY> example) {
        return repo.findAll(example);
    }

    @Override
    public List<ENTITY> list(Example<ENTITY> example, Sort sort) {
        return repo.findAll(example, sort);
    }

    @Override
    public Page<ENTITY> list(Example<ENTITY> example, Pageable pageable) {
        return repo.findAll(example, pageable);
    }

    @Override
    public List<ENTITY> list(Collection<ID> ids) {
        return repo.findAllById(ids);
    }

    @Override
    public Optional<ENTITY> get(ID id) {
        return repo.findById(id);
    }

    @Override
    public ENTITY create(ENTITY entity) {
        return repo.save(entity);
    }

    @Override
    public List<ENTITY> createAll(Collection<ENTITY> entities) {
        return CollectionUtils.isEmpty(entities) ? Collections.emptyList() : repo.saveAll(entities);
    }

    @Override
    public ENTITY update(ENTITY entity) {
        return repo.saveAndFlush(entity);
    }

    @Override
    public List<ENTITY> updateAll(Collection<ENTITY> entities) {
        return CollectionUtils.isEmpty(entities) ? Collections.emptyList() : repo.saveAll(entities);
    }

    @Override
    public ENTITY remove(ID id) {
        ENTITY entity = get(id).orElse(null);
        repo.deleteById(id);
        return entity;
    }

    @Override
    public List<ENTITY> removeAll(Collection<ID> ids) {
        List<ENTITY> entities = list(ids);
        repo.deleteInBatch(entities);
        return entities;
    }

    @Override
    public boolean isExist(ID id) {
        return repo.existsById(id);
    }

    @Override
    public long count() {
        return repo.count();
    }

    @Override
    public void flush() {
        repo.flush();
    }
}
