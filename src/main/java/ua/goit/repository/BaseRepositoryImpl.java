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

    private final Connection connection;

    private final Class<E> modelClass;
    private final ObjectMapper jacksonMapper;
    private final Map<String,String> mapColumnField;
    private final String databaseSchemaName;

    private final PreparedStatement findAllPreparedStatement;
    private final PreparedStatement findByIdPreparedStatement;
    private final PreparedStatement deletePreparedStatement;
    private final PreparedStatement createPreparedStatement;
    private final PreparedStatement updatePreparedStatement;

//    private final String table;
//    private final String fields;
//    private final String fieldId;

    //public BaseRepositoryImpl(String table, String fields,String fieldId) {
    @SneakyThrows
    public BaseRepositoryImpl(Class modelClass) {

        this.connection = DatabaseConnection.getInstance().getConnection();
        this.databaseSchemaName = PropertiesLoader.getProperty("db.name");
        this.modelClass = modelClass;
        this.jacksonMapper = new ObjectMapper();
        this.mapColumnField = Arrays.stream(this.modelClass.getDeclaredFields())
                .filter(modelField -> !Modifier.isStatic(modelField.getModifiers()))
                .collect(Collectors.toMap(modelField -> getColumn(modelField), modelField -> modelField.getName()));

        String generatedColumns[] = {getColumn(Arrays.stream(this.modelClass.getDeclaredFields())
                .filter(modelField -> !Modifier.isStatic(modelField.getModifiers()))
                .filter(modelField -> modelField.getAnnotation(Id.class) != null)
                .findAny().orElseThrow(() -> new RuntimeException("Entity mast have id")))};
        String tableName = modelClass.getAnnotation(Entity.class) != null
//                ? modelClass.getAnnotation(Entity.class) : modelClass.getSimpleName().toLowerCase();
                ? "companies" : modelClass.getSimpleName().toLowerCase();
        String countValues = IntStream.range(0, mapColumnField.size()).mapToObj(p -> "?").collect(Collectors.joining(","));
        String fieldsForCreate = mapColumnField.keySet().stream().collect(Collectors.joining(","));
        String fieldsForUpdate = mapColumnField.keySet().stream().map(p -> p+"=?").collect(Collectors.joining(","));
        String table = databaseSchemaName+"."+tableName;
        this.findAllPreparedStatement = connection.prepareStatement(
                "SELECT * FROM " + table, generatedColumns);
        this.findByIdPreparedStatement = connection.prepareStatement(
                "SELECT * FROM " + table + "WHERE id=?", generatedColumns);
        this.deletePreparedStatement = connection.prepareStatement(
                "DELETE FROM " + table + "WHERE id=?", generatedColumns);
        this.createPreparedStatement = connection.prepareStatement(
                "INSERT INTO " + table + "(" + fieldsForCreate + ") VALUES (" + countValues + ")", generatedColumns);
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
    public List<E> saveAll(Iterable<E> itrbl) {
        List<E> list = new ArrayList<>();
        for(E e : itrbl){
            list.add(save(e));
        }
        return list;
    }

    @SneakyThrows
    @Override
    public List<E> findAll(){
        return parse(findAllPreparedStatement.executeQuery());

    }

    //@Override
    public void deleteAll() {
//        String sql = "DELETE FROM " + table;
//        try(Statement statement = connection.createStatement()){
//            statement.executeUpdate(sql);
//        } catch (SQLException throwables) {
//            throwables.printStackTrace();
//        }
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
                //.map(e -> e)
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

//        String sql = String.format("SELECT %s FROM %s WHERE %s = %s", fields, table, fieldId, id.toString());
//        try(Statement statement = connection.createStatement()){
//            ResultSet resultSet = statement.executeQuery(sql);
//            if(resultSet.next()){
//                return resultSet.getObject(fieldId, Optional.class);
//            }
//        } catch (SQLException throwables) {
//            throwables.printStackTrace();
//        }
//        return Optional.empty();
    }

//    //@Override
//    public void update(ID id, E e) {
//
//    }

    @SneakyThrows
    @Override
    public void deleteById(ID id) {
        deletePreparedStatement.setObject(1,id);
        deletePreparedStatement.executeUpdate();

//        if (id!=null) {
//            String sql = String.format("DELETE FROM %s WHERE %s=%s", table, fieldId, id.toString());
//            try(Statement statement = connection.createStatement()){
//                statement.executeUpdate(sql);
//            } catch (SQLException throwables) {
//                throwables.printStackTrace();
//            }
//        }
    }

    @SneakyThrows
    @Override
    public void close() {
        connection.close();
    }

    //@Override
    public boolean existsById(ID id) {
        return false;
    }

    //@Override
//    public long count() {
//        String sql = "SELECT COUNT(*) FROM " + table;
//        try(Statement statement = connection.createStatement()){
//            ResultSet resultSet = statement.executeQuery(sql);
//            return resultSet.getInt("COUNT(*)");
//        } catch (SQLException throwables) {
//            throwables.printStackTrace();
//        }
//        return 0;
//    }

}
