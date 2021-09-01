package ua.goit.repository;

import ua.goit.model.BaseEntity;

import java.util.List;
import java.util.Optional;

public interface BaseRepository <ID, E extends BaseEntity<ID>> {

    List<E> saveAll(Iterable<E> itrbl);

    List<E> findAll();

    //void deleteAll();

    E save(E e);

    E getOne(ID id);

    Optional<E> findById(ID id);

    //void update(ID id, E e);

    void deleteById(ID id);

}
