package ua.goit.repository;

import ua.goit.model.BaseEntity;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Factory <ID, T extends BaseEntity<ID>>{
    private final static Map<String, BaseRepository> REPOSITORIES  = new ConcurrentHashMap<>();

    public synchronized static <ID, T extends BaseEntity<ID>> BaseRepository<ID, T> of(Class<T> modelClass) {
        final String modelName = modelClass.getName();
        if (!REPOSITORIES.containsKey(modelName)) REPOSITORIES.put(modelName, new BaseRepositoryImpl<>(modelClass));
        return REPOSITORIES.get(modelName);
    }
}
