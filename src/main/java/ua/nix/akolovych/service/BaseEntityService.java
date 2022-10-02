package ua.nix.akolovych.service;

import ua.nix.akolovych.entity.BaseEntity;

import java.util.List;

public interface BaseEntityService <E extends BaseEntity> {
    void create(E entity);
    void update(E entity);
    void delete(Long Id);
    E findById(Long id);
    List<E> findAll();
}
