package ua.nix.akolovych.dao;

import jakarta.persistence.Entity;
import jakarta.persistence.MappedSuperclass;
import ua.nix.akolovych.entity.BaseEntity;

import java.util.List;

public interface BaseEntityDao<E extends BaseEntity> {
    void create (E entity);
    void update(E entity);
    void delete(Long id);
    boolean existById(Long id);
    E findById(Long id);
    List<E> findAll();
}
