package ua.goit.repository;

import ua.goit.model.Developer;
import lombok.SneakyThrows;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

public class Developers implements BaseRepository<Integer,Developer>{
    //private final DatabaseConnection databaseConnection;
    private final Connection connection;
    private final String table = "hw2.developers";
    private final String fields = "developer_id,developer_name,age,sex,salary,company_id";

    public Developers() {
        //this.databaseConnection = DatabaseConnection.getInstance();
        this.connection = DatabaseConnection.getInstance().getConnection();
    }

    @Override
    public List saveAll(Iterable itrbl) {
        return null;
    }

    @Override
    @SneakyThrows
    public Collection findAll() {
        //Connection connection = databaseConnection.getConnection();
        Statement statement = connection.createStatement();
        String sql = "SELECT * FROM " + table;
        ResultSet resultSet = statement.executeQuery(sql);
        List<Developer> developers = new ArrayList<>();
        while (resultSet.next()){
            Developer developer = Developer.builder()
                    .id(resultSet.getInt("developer_id"))
                    .developer_name(resultSet.getString("developer_name"))
                    .age(resultSet.getInt("age"))
                    .sex(resultSet.getString("sex"))
                    .salary(resultSet.getInt("salary"))
                    .company_id(resultSet.getInt("company_id"))
                    .build();
            developers.add(developer);
        }
        statement.close();
        return developers;
    }

    @Override
    @SneakyThrows
    public void deleteAll() {
        Statement statement = connection.createStatement();
        String sql = "DELETE FROM " + table;
        //ResultSet resultSet = statement.executeQuery(sql);
    }

    @Override
    @SneakyThrows
    public long count() {
        Statement statement = connection.createStatement();
        String sql = "SELECT COUNT(*) FROM " + table;
        ResultSet resultSet = statement.executeQuery(sql);
        return resultSet.getInt("COUNT(*)");
    }

    @Override
    public boolean existsById(Integer id) {
        return false;
    }

    @Override
    public Developer getOne(Integer id) {
        return null;
    }

    @Override
    @SneakyThrows
    public void deleteById(Integer id) {
        if (id!=null) {
            Statement statement = connection.createStatement();
            String sql = "DELETE FROM hw2.developers WHERE id="+id;
            //ResultSet resultSet = statement.executeQuery(sql);
        }
    }

    @Override
    public Optional findById(Integer id) {
        return Optional.empty();
    }

    @Override
    @SneakyThrows
    public Developer save(Developer developer) {
        if (developer!=null) {

            String values = "10,Vanda,28,F,3100,3";

            Statement statement = connection.createStatement();
            String sql = "INSERT INTO "+table+" (" + fields + ") VALUES (" + values + ")";
            //ResultSet resultSet = statement.executeQuery(sql);
        }
        return null;
    }
}
