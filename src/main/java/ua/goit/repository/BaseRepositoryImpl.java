package ua.goit.repository;

import ua.goit.model.BaseEntity;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public class BaseRepositoryImpl  <ID, E extends BaseEntity<ID>> implements BaseRepository <ID,E>{
    @Override
    public List<E> saveAll(Iterable<E> itrbl) {
        return null;
    }

    @Override
    public Collection<E> findAll() {
        return null;
    }

    @Override
    public void deleteAll() {

    }

    @Override
    public E save(E e) {
        return null;
    }

    @Override
    public Optional<E> findById(ID id) {
        return Optional.empty();
    }

    @Override
    public void deleteById(ID id) {

    }

    @Override
    public E getOne(ID id) {
        return null;
    }

    @Override
    public boolean existsById(ID id) {
        return false;
    }

    @Override
    public long count() {
        return 0;
    }
}
