package ua.goit.repository;

import ua.goit.model.Company;
import ua.goit.model.Developer;
import lombok.SneakyThrows;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

public class DeveloperRepository implements BaseRepository<Integer,Developer>{
    //private final DatabaseConnection databaseConnection;
    private final Connection connection;
    private final String table = "hw2.developers";
    private final String fields = "developer_id,developer_name,age,sex,salary,company_id";

    public DeveloperRepository() {
        //this.databaseConnection = DatabaseConnection.getInstance();
        this.connection = DatabaseConnection.getInstance().getConnection();
    }

    @Override
    @SneakyThrows
    public List<Developer> saveAll(Iterable<Developer> itrbl) {
        List<Developer> devs = new ArrayList<>();
        String sql = String.format("INSERT INTO %s (%s) VALUES (?,?,?,?,?)",table,fields);
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        for (Developer developer: itrbl) {
            //String.join("",sql, "(?,?,?,?,?)");
            preparedStatement.setInt(1,developer.getId());
            preparedStatement.setString(2,developer.getDeveloper_name());
            preparedStatement.setInt(3,developer.getAge());
            preparedStatement.setString(4, developer.getSex());
            preparedStatement.setInt(5,developer.getCompany_id().getId());
            ResultSet resultSet = preparedStatement.executeQuery();
            devs.add(developer);
        }
        return devs;
    }

    @Override
    @SneakyThrows
    public Collection findAll() {
        Statement statement = connection.createStatement();
        String sql = String.format("SELECT %s FROM %s",fields,table);
        ResultSet resultSet = statement.executeQuery(sql);
        List<Developer> developers = new ArrayList<>();
        while (resultSet.next()){
            Developer developer = Developer.builder()
                    .id(resultSet.getInt("developer_id"))
                    .developer_name(resultSet.getString("developer_name"))
                    .age(resultSet.getInt("age"))
                    .sex(resultSet.getString("sex"))
                    .salary(resultSet.getInt("salary"))
                    .company_id((Company) resultSet.getObject("company_id"))
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
        ResultSet resultSet = statement.executeQuery(sql);
    }

    @Override
    @SneakyThrows
    public void save(Developer developer) {
        if (developer!=null) {
            //String values = "10,Wanda,28,F,3100,3";
            String sql = String.format("INSERT INTO %s (%s) VALUES (?,?,?,?,?,?)",table,fields);
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1,developer.getId());
            preparedStatement.setString(2,developer.getDeveloper_name());
            preparedStatement.setInt(3,developer.getAge());
            preparedStatement.setString(4, developer.getSex());
            preparedStatement.setInt(5,developer.getSalary());
            preparedStatement.setInt(6,developer.getCompany_id().getId());
            preparedStatement.executeUpdate();
        }
    }

    @Override
    @SneakyThrows
    public Developer getOne(Integer id) {
        return findById(id)
                .map(e -> e)
                .orElseThrow(()-> new RuntimeException("Entity with id " + id + " not found"));
    }

    @Override
    @SneakyThrows
    public Optional<Developer> findById(Integer id) {
        String sql = String.format("SELECT %s FROM %s WHERE developer_id = %s",fields,table,id);
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(sql);
        if(resultSet.next()){
            //return resultSet.getObject("developer_id", Optional.class);
            Developer developer = resultSet.getObject("developer_id", Developer.class);
            return Optional.ofNullable(developer);
        };
        return Optional.empty();
    }

    @Override
    public void update(Integer integer, Developer developer) {

    }

    @Override
    @SneakyThrows
    public void deleteById(Integer id) {
        if (id!=null) {
            Statement statement = connection.createStatement();
            String sql = String.format("DELETE FROM %s WHERE developer_id = %s",table,id);
            ResultSet resultSet = statement.executeQuery(sql);
        }
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

}
