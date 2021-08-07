package ua.goit.repository;

import ua.goit.model.Company;
import ua.goit.model.Customer;
import ua.goit.model.Project;
import ua.goit.service.EntityServiceImpl;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

public class ProjectRepository implements BaseRepository<Integer, Project>{
    private final Connection connection;
    private final String table = "hw2.projects";
    private final String fields = "project_id,project_name,cost,company_id,customer_id";

    public ProjectRepository() {
        this.connection = DatabaseConnection.getInstance().getConnection();
    }

    @Override
    public List<Project> saveAll(Iterable<Project> itrbl) {
        return null;
    }

    @Override
    public Collection<Project> findAll() {
        String sql = String.format("SELECT %s FROM %s",fields,table);
        List<Project> projects = new ArrayList<>();
        try(Statement statement = connection.createStatement()){
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()){
                Project project = Project.builder()
                        .id(resultSet.getInt("project_id"))
                        .project_name(resultSet.getString("project_name"))
                        .cost(resultSet.getInt("cost"))
                        .company_id((Company) resultSet.getObject("company_id"))
                        .customer_id((Customer) resultSet.getObject("customer_id"))
                        .build();
                projects.add(project);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return projects;
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
    public void save(Project project) {
        if (project!=null) {
            //String values = "10,NewAccounting,12341234";
            String sql = String.format("INSERT INTO %s (%s) VALUES (?,?,?,?,?)",table,fields);
            try(PreparedStatement preparedStatement = connection.prepareStatement(sql)){
                preparedStatement.setInt(1,project.getId());
                preparedStatement.setString(2,project.getProject_name());
                preparedStatement.setInt(3,project.getCost());
                preparedStatement.setInt(4,project.getCompany_id().getId());
                preparedStatement.setInt(5,project.getCustomer_id().getId());
                preparedStatement.executeUpdate();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
    }

    @Override
    public Project getOne(Integer id) {
        return findById(id)
                .map(e -> e)
                .orElseThrow(()-> new RuntimeException("Entity with id " + id + " not found"));
    }

    @Override
    public Optional<Project> findById(Integer id) {
        String sql = String.format("SELECT %s FROM %s WHERE project_id = %s",fields,table,id);
        try(Statement statement = connection.createStatement()){
            ResultSet resultSet = statement.executeQuery(sql);
            if(resultSet.next()){
                return Optional.of(new Project(
                        resultSet.getInt("project_id"),
                        resultSet.getString("project_name"),
                        resultSet.getInt("cost"),
                        (new EntityServiceImpl<>(new CompanyRepository())).read(resultSet.getInt("company_id")),
                        (new EntityServiceImpl<>(new CustomerRepository())).read(resultSet.getInt("customer_id"))
                ));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    public void update(Integer id, Project project) {
        String fieldsAndValues = String.format("project_id=%s,project_name='%s',cost=%s,company_id=%s,customer_id=%s",
                id,
                project.getProject_name(),
                project.getCost(),
                project.getCompany_id().getId(),
                project.getCustomer_id().getId());
        String sql = String.format("UPDATE %s SET %s WHERE project_id=%s",table,fieldsAndValues,id);
        try(Statement statement = connection.createStatement()){
            statement.executeUpdate(sql);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Override
    public void deleteById(Integer id) {
        if (id!=null) {
            String sql = String.format("DELETE FROM %s WHERE project_id=%s",table,id);
            try(Statement statement = connection.createStatement()){
                statement.executeUpdate(sql);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
    }

    @Override
    public boolean existsById(Integer id) {
        return false;
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
}

