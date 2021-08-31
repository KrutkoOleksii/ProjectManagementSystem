//package ua.goit.service.old;
//
//import lombok.SneakyThrows;
//import ua.goit.model.Company;
//import ua.goit.model.Customer;
//import ua.goit.model.Project;
//import ua.goit.repository.BaseRepository;
//import ua.goit.service.EntityServiceImpl;
//import ua.goit.util.DatabaseConnection;
//
//import java.sql.*;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Optional;
//
//public class ProjectRepository implements BaseRepository<Long, Project> {
//    private final Connection connection;
//    private final String table = "hw2.projects";
//    private final String fields = "project_id,project_name,cost,company_id,customer_id,start_date";
//
//    public ProjectRepository() {
//        this.connection = DatabaseConnection.getInstance().getConnection();
//    }
//
//    @Override
//    public List<Project> saveAll(Iterable<Project> itrbl) {
//        return null;
//    }
//
//    @Override
//    public List<Project> findAll() {
//        String sql = String.format("SELECT %s FROM %s",fields,table);
//        List<Project> projects = new ArrayList<>();
//        try(Statement statement = connection.createStatement()){
//            ResultSet resultSet = statement.executeQuery(sql);
//            while (resultSet.next()){
//                Project project = Project.builder()
//                        .id(resultSet.getLong("project_id"))
//                        .name(resultSet.getString("project_name"))
//                        .cost(resultSet.getInt("cost"))
//                        .companyId((Company) resultSet.getObject("company_id"))
//                        .customerId((Customer) resultSet.getObject("customer_id"))
//                        .startDate(resultSet.getDate("start_date").toString())
//                        .build();
//                projects.add(project);
//            }
//        } catch (SQLException throwables) {
//            throwables.printStackTrace();
//        }
//        return projects;
//    }
//
//    //@Override
//    public void deleteAll() {
//        String sql = "DELETE FROM " + table;
//        try(Statement statement = connection.createStatement()){
//            statement.executeUpdate(sql);
//        } catch (SQLException throwables) {
//            throwables.printStackTrace();
//        }
//    }
//
//    @Override
//    public Project save(Project project) {
//        if (project!=null) {
//            //String values = "10,NewAccounting,12341234";
//            String sql = String.format("INSERT INTO %s (%s) VALUES (?,?,?,?,?,?)",table,fields);
//            try(PreparedStatement preparedStatement = connection.prepareStatement(sql)){
//                preparedStatement.setLong(1,project.getId());
//                preparedStatement.setString(2,project.getName());
//                preparedStatement.setInt(3,project.getCost());
//                preparedStatement.setLong(4,project.getCompanyId().getId());
//                preparedStatement.setLong(5,project.getCustomerId().getId());
//                preparedStatement.setString(6,project.getStartDate());
//                preparedStatement.executeUpdate();
//            } catch (SQLException throwables) {
//                throwables.printStackTrace();
//            }
//        }
//        return project;
//    }
//
//    @Override
//    public Project getOne(Long id) {
//        return findById(id)
//                .map(e -> e)
//                .orElseThrow(()-> new RuntimeException("Entity with id " + id + " not found"));
//    }
//
//    @Override
//    public Optional<Project> findById(Long id) {
//        String sql = String.format("SELECT %s FROM %s WHERE project_id = %s",fields,table,id);
//        try(Statement statement = connection.createStatement()){
//            ResultSet resultSet = statement.executeQuery(sql);
//            if(resultSet.next()){
//                return Optional.of(new Project(
//                        resultSet.getLong("project_id"),
//                        resultSet.getString("project_name"),
//                        resultSet.getInt("cost"),
//                        (new EntityServiceImpl<>(new CompanyRepository())).read(resultSet.getLong("company_id")),
//                        (new EntityServiceImpl<>(new CustomerRepository())).read(resultSet.getLong("customer_id")),
//                        resultSet.getString("start_date")
//                ));
//            }
//        } catch (SQLException throwables) {
//            throwables.printStackTrace();
//        }
//        return Optional.empty();
//    }
//
//    //@Override
//    public void update(Long id, Project project) {
//        String fieldsAndValues = String.format("project_id=%s,project_name='%s',cost=%s,company_id=%s,customer_id=%s,start_date='%s'",
//                id,
//                project.getName(),
//                project.getCost(),
//                project.getCompanyId().getId(),
//                project.getCustomerId().getId(),
//                project.getStartDate()
//                );
//        String sql = String.format("UPDATE %s SET %s WHERE project_id=%s",table,fieldsAndValues,id);
//        try(Statement statement = connection.createStatement()){
//            statement.executeUpdate(sql);
//        } catch (SQLException throwables) {
//            throwables.printStackTrace();
//        }
//    }
//
//    @Override
//    public void deleteById(Long id) {
//        if (id!=null) {
//            String sql = String.format("DELETE FROM %s WHERE project_id=%s",table,id);
//            try(Statement statement = connection.createStatement()){
//                statement.executeUpdate(sql);
//            } catch (SQLException throwables) {
//                throwables.printStackTrace();
//            }
//        }
//    }
//    @SneakyThrows
//    @Override
//    public void close() {
//        connection.close();
//    }
//    //@Override
//    public boolean existsById(Long id) {
//        return false;
//    }
//
//    //@Override
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
//}
//
