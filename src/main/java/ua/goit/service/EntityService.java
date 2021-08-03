package ua.goit.service;

public interface EntityService <ID, E> {

    void create(E e);

    E read(ID id);

    E update(ID id, E e);

    void delete(ID id);
}
