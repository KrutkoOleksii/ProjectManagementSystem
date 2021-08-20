package ua.goit.repository;

import ua.goit.model.BaseEntity;

import java.util.List;
import java.util.Optional;

public interface BaseRepository <ID, E extends BaseEntity<ID>> {

    public List<E> saveAll(Iterable<E> itrbl);

    public List<E> findAll();

    //public void deleteAll();

    public E save(E e);

    public E getOne(ID id);

    public Optional<E> findById(ID id);

    //public void update(ID id, E e);

    public void deleteById(ID id);

    //public boolean existsById(ID id);

    //public long count();

}
