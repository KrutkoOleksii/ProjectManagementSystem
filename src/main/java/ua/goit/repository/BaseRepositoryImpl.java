package ua.goit.repository;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import ua.goit.model.BaseEntity;
import ua.goit.util.DatabaseConnection;
import ua.goit.util.PropertiesLoader;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Closeable;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.sql.*;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class BaseRepositoryImpl  <ID, E extends BaseEntity<ID>> implements Closeable, BaseRepository <ID,E>{

    protected final Connection connection;

    private final Class<E> modelClass;
    private final ObjectMapper jacksonMapper;
    private final Map<String,String> mapColumnField;

    private final PreparedStatement findAllPreparedStatement;
    private final PreparedStatement findByIdPreparedStatement;
    private final PreparedStatement deletePreparedStatement;
    private final PreparedStatement createPreparedStatement;
    private final PreparedStatement updatePreparedStatement;

    @SneakyThrows
    public BaseRepositoryImpl(Class<E> modelClass) {
        String databaseSchemaName = PropertiesLoader.getProperty("db.name");
        this.connection = DatabaseConnection.getInstance().getConnection();
        this.modelClass = modelClass;
        this.jacksonMapper = new ObjectMapper();
        this.mapColumnField = Arrays.stream(this.modelClass.getDeclaredFields())
                .filter(modelField -> !Modifier.isStatic(modelField.getModifiers()))
                .collect(Collectors.toMap(modelField -> getColumn(modelField), modelField -> modelField.getName()));

        String[] generatedColumns = {getColumn(Arrays.stream(this.modelClass.getDeclaredFields())
                .filter(modelField -> !Modifier.isStatic(modelField.getModifiers()))
                .filter(modelField -> modelField.getAnnotation(Id.class) != null)
                .findAny().orElseThrow(() -> new RuntimeException("Entity must have id")))};
        String tableName = modelClass.getAnnotation(Entity.class) != null
                ? modelClass.getAnnotation(Entity.class).name() : modelClass.getSimpleName().toLowerCase();
        String countValues = IntStream.range(0, mapColumnField.size()).mapToObj(p -> "?").collect(Collectors.joining(","));
        String fieldsForCreate = mapColumnField.keySet().stream().collect(Collectors.joining(","));
        String fieldsForUpdate = mapColumnField.keySet().stream().map(p -> p+"=?").collect(Collectors.joining(","));
        String table = databaseSchemaName +"."+tableName;

        this.findAllPreparedStatement = connection.prepareStatement(
                "SELECT * FROM " + table, generatedColumns);
        this.findByIdPreparedStatement = connection.prepareStatement(
                "SELECT * FROM " + table + " WHERE id=?", generatedColumns);
        this.deletePreparedStatement = connection.prepareStatement(
                "DELETE FROM " + table + " WHERE id=?", generatedColumns);
        this.createPreparedStatement = connection.prepareStatement(
                "INSERT INTO " + table + " (" + fieldsForCreate + ") VALUES (" + countValues + ")", generatedColumns);
        this.updatePreparedStatement = connection.prepareStatement(
                "UPDATE " + table + " SET " + fieldsForUpdate + " WHERE id=?", generatedColumns);
    }

    private  String getColumn(Field modelField) {
        return modelField.getAnnotationsByType(Column.class) == null ? modelField.getName() : modelField.getAnnotation(Column.class).name();
    }

    @SneakyThrows
    private List<E> parse(ResultSet resultSet) {
        final List<E> list = new ArrayList<>();
        while (resultSet.next()) {
            final Map<String,Object> map = new HashMap<>();
            for (String fieldName : mapColumnField.keySet()){
                map.put(mapColumnField.get(fieldName), resultSet.getObject(fieldName));
            }
            list.add(jacksonMapper.convertValue(map,modelClass));
        }
        return list;
    }

    @SneakyThrows
    private E executeStatement(PreparedStatement preparedStatement, E e) {
        int count = 1;
        for (String fieldName : mapColumnField.values()) {
            Field declaredField = modelClass.getDeclaredField(fieldName);
            declaredField.setAccessible(true);
            preparedStatement.setObject(count++, declaredField.get(e));
        }
        preparedStatement.executeUpdate();
        ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
        generatedKeys.next();
        return findById((ID) generatedKeys.getObject(1)).get();
    }

    @SneakyThrows
    @Override
    public List<E> saveAll(Iterable<E> iterable) {
        List<E> list = new ArrayList<>();
        for(E e : iterable){
            list.add(save(e));
        }
        return list;
    }

    @SneakyThrows
    @Override
    public List<E> findAll(){
        return parse(findAllPreparedStatement.executeQuery());
    }

    @SneakyThrows
    @Override
    public E save(E e) {
        if (e.getId() == null || !findById(e.getId()).isPresent()) {
            return executeStatement(createPreparedStatement, e);
        } else {
            updatePreparedStatement.setObject(mapColumnField.size()+1,e.getId());
            return executeStatement(updatePreparedStatement, e);
        }
    }

    @Override
    public E getOne(ID id) {
        return findById(id)
                .orElseThrow(()-> new RuntimeException("Entity with id " + id + " not found"));
    }

    @SneakyThrows
    @Override
    public Optional<E> findById(ID id) {
        findByIdPreparedStatement.setObject(1, id);
        final List<E> list = parse(findByIdPreparedStatement.executeQuery());
        if (list.isEmpty()) return Optional.empty();
        if (list.size() > 1) throw  new RuntimeException("return more than one result");
        return Optional.of(list.get(0));
    }

    @SneakyThrows
    @Override
    public void deleteById(ID id) {
        deletePreparedStatement.setObject(1,id);
        deletePreparedStatement.executeUpdate();
    }

    @SneakyThrows
    @Override
    public void close() {
        connection.close();
    }

}
