package ua.goit.service;

import ua.goit.model.BaseEntity;
import ua.goit.repository.Factory;

public abstract class BaseService <ID, E extends BaseEntity<ID>> {

    public E readEntity(Class<E> aClass, ID id) {
        return Factory.of(aClass).getOne(id);
    }

    public void deleteEntity(Class<E> aClass, ID id) {
        Factory.of(aClass).deleteById(id);
    }
}
