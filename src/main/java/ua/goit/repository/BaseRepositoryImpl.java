package ua.goit.repository;

import ua.goit.model.BaseEntity;
import ua.goit.util.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class BaseRepositoryImpl  <ID, E extends BaseEntity<ID>> implements BaseRepository <ID,E>{
    private final Connection connection;
    private final String table;
    private final String fields;
    private final String fieldId;

    //public BaseRepositoryImpl(String table, String fields,String fieldId) {
    public BaseRepositoryImpl(Class modelClass) {
        this.connection = DatabaseConnection.getInstance().getConnection();

        this.table = "";//table;
        this.fields = "";//fields;
        this.fieldId = "";//fieldId;
    }

    @Override
    public List<E> saveAll(Iterable<E> itrbl) {
        return null;
    }

    @Override
    public List<E> findAll(){
        String sql = String.format("SELECT %s FROM %s",fields,table);
        List<E> entities = new ArrayList<>();
        try(Statement statement = connection.createStatement()){
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
//                E element = E.builder()
//                        .id(resultSet.getInt("developer_id"))
//                        .developer_name(resultSet.getString("developer_name"))
//                        .age(resultSet.getInt("age"))
//                        .sex(resultSet.getString("sex"))
//                        .salary(resultSet.getInt("salary"))
//                        .company_id(resultSet.getInt("company_id"))
//                        .build();
//                entities.add(element);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return entities;
    }

    //@Override
    public void deleteAll() {
        String sql = "DELETE FROM " + table;
        try(Statement statement = connection.createStatement()){
            statement.executeUpdate(sql);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Override
    public E save(E e) {
        if (e != null) {
            String sql = String.format("INSERT INTO %s (%s) VALUES (?,?,?,?,?)", table, fields);
            try(PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
//                preparedStatement.setInt(1, e.getId());
//                preparedStatement.setString(2, );
//                preparedStatement.setInt(3, e.getAge());
//                preparedStatement.setString(4, e.getSex());
//                preparedStatement.setInt(5, e.getCompany_id().getId());
                preparedStatement.executeUpdate();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        return e;
    }

    @Override
    public E getOne(ID id) {
        return findById(id)
                .map(e -> e)
                .orElseThrow(()-> new RuntimeException("Entity with id " + id + " not found"));
    }

    @Override
    public Optional<E> findById(ID id) {
        String sql = String.format("SELECT %s FROM %s WHERE %s = %s", fields, table, fieldId, id.toString());
        try(Statement statement = connection.createStatement()){
            ResultSet resultSet = statement.executeQuery(sql);
            if(resultSet.next()){
                return resultSet.getObject(fieldId, Optional.class);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return Optional.empty();
    }

    //@Override
    public void update(ID id, E e) {

    }

    @Override
    public void deleteById(ID id) {
        if (id!=null) {
            String sql = String.format("DELETE FROM %s WHERE %s=%s", table, fieldId, id.toString());
            try(Statement statement = connection.createStatement()){
                statement.executeUpdate(sql);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
    }

    //@Override
    public boolean existsById(ID id) {
        return false;
    }

    //@Override
    public long count() {
        String sql = "SELECT COUNT(*) FROM " + table;
        try(Statement statement = connection.createStatement()){
            ResultSet resultSet = statement.executeQuery(sql);
            return resultSet.getInt("COUNT(*)");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return 0;
    }
}
