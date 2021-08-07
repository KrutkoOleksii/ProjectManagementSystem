package ua.goit.repository;

import ua.goit.model.Company;
import ua.goit.model.Developer;
import ua.goit.service.EntityServiceImpl;

import java.sql.*;
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
    public List<Developer> saveAll(Iterable<Developer> itrbl) {
        String sql = String.format("INSERT INTO %s (%s) VALUES (?,?,?,?,?)",table,fields);
        List<Developer> developers = new ArrayList<>();
        try(PreparedStatement preparedStatement = connection.prepareStatement(sql)){
            for (Developer developer: itrbl) {
                //String.join("",sql, "(?,?,?,?,?)");
                preparedStatement.setInt(1,developer.getId());
                preparedStatement.setString(2,developer.getDeveloper_name());
                preparedStatement.setInt(3,developer.getAge());
                preparedStatement.setString(4, developer.getSex());
                preparedStatement.setInt(5,developer.getCompany_id().getId());
                ResultSet resultSet = preparedStatement.executeQuery();
                developers.add(developer);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return developers;
    }

    @Override
    public Collection findAll() {
        String sql = String.format("SELECT %s FROM %s",fields,table);
        List<Developer> developers = new ArrayList<>();
        try(Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
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
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return developers;
    }

    @Override
    public void deleteAll() {
        String sql = "DELETE FROM " + table;
        try(Statement statement = connection.createStatement()){
            statement.executeUpdate(sql);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Override
    public void save(Developer developer) {
        if (developer!=null) {
            //String values = "10,Wanda,28,F,3100,3";
            String sql = String.format("INSERT INTO %s (%s) VALUES (?,?,?,?,?,?)",table,fields);
            try(PreparedStatement preparedStatement = connection.prepareStatement(sql)){
                preparedStatement.setInt(1,developer.getId());
                preparedStatement.setString(2,developer.getDeveloper_name());
                preparedStatement.setInt(3,developer.getAge());
                preparedStatement.setString(4, developer.getSex());
                preparedStatement.setInt(5,developer.getSalary());
                preparedStatement.setInt(6,developer.getCompany_id().getId());
                preparedStatement.executeUpdate();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
    }

    @Override
    public Developer getOne(Integer id) {
        return findById(id)
                .map(e -> e)
                .orElseThrow(()-> new RuntimeException("Entity with id " + id + " not found"));
    }

    @Override
    public Optional<Developer> findById(Integer id) {
        String sql = String.format("SELECT %s FROM %s WHERE developer_id = %s",fields,table,id);
        try(Statement statement = connection.createStatement()){
            ResultSet resultSet = statement.executeQuery(sql);
            if(resultSet.next()){
                return Optional.of(new Developer(
                        resultSet.getInt("developer_id"),
                        resultSet.getString("developer_name"),
                        resultSet.getInt("age"),
                        resultSet.getString("sex"),
                        resultSet.getInt("salary"),
                        (new EntityServiceImpl<>(new CompanyRepository())).read(resultSet.getInt("company_id"))
                ));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    public void update(Integer id, Developer developer) {
        String fieldsAndValues = String.format("developer_id=%s,developer_name='%s',age=%s,sex='%s',salary=%s,company_id=%s",
                id,
                developer.getDeveloper_name(),
                developer.getAge(),
                developer.getSex(),
                developer.getSalary(),
                developer.getCompany_id().getId());
        String sql = String.format("UPDATE %s SET %s WHERE developer_id = %s",table,fieldsAndValues,id);
        try(Statement statement = connection.createStatement()){
            statement.executeUpdate(sql);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Override
    public void deleteById(Integer id) {
        if (id!=null) {
            String sql = String.format("DELETE FROM %s WHERE developer_id = %s",table,id);
            try(Statement statement = connection.createStatement()){
                statement.executeUpdate(sql);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
    }

    @Override
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

    @Override
    public boolean existsById(Integer id) {
        return false;
    }

}
