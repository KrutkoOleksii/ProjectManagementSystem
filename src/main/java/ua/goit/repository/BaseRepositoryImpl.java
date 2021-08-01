package ua.goit.repository;

import lombok.SneakyThrows;
import ua.goit.model.BaseEntity;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

public class BaseRepositoryImpl  <ID, E extends BaseEntity<ID>> implements BaseRepository <ID,E>{
    private final Connection connection;
    private final String table;
    private final String fields;
    private final String fieldId;

    public BaseRepositoryImpl(String table, String fields,String fieldId) {
        this.connection = DatabaseConnection.getInstance().getConnection();
        this.table = table;
        this.fields = fields;
        this.fieldId = fieldId;
    }

    @Override
    public List<E> saveAll(Iterable<E> itrbl) {
        return null;
    }

    @SneakyThrows
    @Override
    public Collection<E> findAll(){
        List<E> entities = new ArrayList<>();
        Statement statement = connection.createStatement();
//        String sql = "SELECT " + fields + " FROM " + table;
//        ResultSet resultSet = statement.executeQuery(sql);
//        while (resultSet.next()){
//            E element = E.builder()
//                    .id(resultSet.getInt("developer_id"))
//                    .developer_name(resultSet.getString("developer_name"))
//                    .age(resultSet.getInt("age"))
//                    .sex(resultSet.getString("sex"))
//                    .salary(resultSet.getInt("salary"))
//                    .company_id(resultSet.getInt("company_id"))
//                    .build();
//            entities.add(element);
//        }
        statement.close();
        return entities;
    }

    @Override
    @SneakyThrows
    public void deleteAll() {
        Statement statement = connection.createStatement();
        String sql = "DELETE FROM " + table;
        ResultSet resultSet = statement.executeQuery(sql);
    }

    @Override
    @SneakyThrows
    public void save(E e) {
        if (e != null) {
            //String values = "10,Wanda,28,F,3100,3"; <<= example
            String sql = "INSERT INTO "+table+" (" + fields + ") VALUES (?,?,?,?,?)";

//            PreparedStatement preparedStatement = connection.prepareStatement(sql);
//
//            preparedStatement.setInt(1,e.getId());
//            preparedStatement.setString(2,);
//            preparedStatement.setInt(3,developer.getAge());
//            preparedStatement.setString(4, developer.getSex());
//            preparedStatement.setInt(5,developer.getCompany_id().getId());
//
//            ResultSet resultSet = preparedStatement.executeQuery();
        }
    }

    @Override
    public E getOne(ID id) {
        return findById(id)
                .map(e -> e)
                .orElseThrow(()-> new RuntimeException("Entity with id " + id + " not found"));
    }

    @Override
    @SneakyThrows
    public Optional<E> findById(ID id) {
        String sql = "SELECT " + fields + " FROM " + table + " WHERE " + fieldId + " = " + id;
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(sql);
        if(resultSet.next()){
            return resultSet.getObject(fieldId, Optional.class);
        }
        return Optional.empty();
    }

    @Override
    @SneakyThrows
    public void deleteById(ID id) {
        if (id!=null) {
            Statement statement = connection.createStatement();
            String sql = "DELETE FROM hw2.developers WHERE " + fieldId + "="+id;
            ResultSet resultSet = statement.executeQuery(sql);
        }
    }

    @Override
    public boolean existsById(ID id) {

        return false;
    }

    @Override
    @SneakyThrows
    public long count() {
        Statement statement = connection.createStatement();
        String sql = "SELECT COUNT(*) FROM " + table;
        ResultSet resultSet = statement.executeQuery(sql);
        return resultSet.getInt("COUNT(*)");
    }
}
