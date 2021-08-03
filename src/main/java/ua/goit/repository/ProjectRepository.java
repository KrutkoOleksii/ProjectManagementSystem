package ua.goit.repository;

import lombok.SneakyThrows;
import ua.goit.model.Company;
import ua.goit.model.Project;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
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
    @SneakyThrows
    public Collection<Project> findAll() {
        List<Project> projects = new ArrayList<>();
        Statement statement = connection.createStatement();
        String sql = "SELECT " + fields + " FROM " + table;
        ResultSet resultSet = statement.executeQuery(sql);
        while (resultSet.next()){
            Project project = Project.builder()
                    .id(resultSet.getInt("project_id"))
                    .project_name(resultSet.getString("project_name"))
                    .cost(resultSet.getInt("cost"))
                    //.company_id(resultSet.getString("company_id"))
                    //.customer_id(resultSet.getString("customer_id"))
                    .build();
            projects.add(project);
        }
        statement.close();
        return projects;
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
    public void save(Project project) {
        if (project!=null) {
            //String values = "10,NewAccounting,12341234";
            String sql = "INSERT INTO "+table+" (" + fields + ") VALUES (?,?,?,?,?)";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1,project.getId());
            preparedStatement.setString(2,project.getProject_name());
            preparedStatement.setInt(3,project.getCost());
            preparedStatement.setInt(4,project.getCompany_id().getId());
            preparedStatement.setInt(5,project.getCustomer_id().getId());
            preparedStatement.executeUpdate();
        }
    }

    @Override
    @SneakyThrows
    public Project getOne(Integer id) {
        return findById(id)
                .map(e -> e)
                .orElseThrow(()-> new RuntimeException("Entity with id " + id + " not found"));
    }

    @Override
    @SneakyThrows
    public Optional<Project> findById(Integer id) {
        String sql = "SELECT " + fields + " FROM " + table + " WHERE project_id = " + id;
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(sql);
        if(resultSet.next()){
            return resultSet.getObject("project_id", Optional.class);
        };
        return Optional.empty();
    }

    @Override
    public void update(Integer integer, Project project) {

    }

    @Override
    @SneakyThrows
    public void deleteById(Integer id) {
        if (id!=null) {
            Statement statement = connection.createStatement();
            String sql = "DELETE FROM " + table + " WHERE project_id=" + id;
            ResultSet resultSet = statement.executeQuery(sql);
        }
    }

    @Override
    public boolean existsById(Integer id) {
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

