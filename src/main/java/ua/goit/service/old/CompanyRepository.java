package ua.goit.service.old;

import ua.goit.model.Company;
import ua.goit.repository.BaseRepository;
import ua.goit.util.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CompanyRepository implements BaseRepository<Long, Company> {
    private final Connection connection;
    private final String table = "hw2.companies";
    private final String fields = "company_id,company_name,company_code";

    public CompanyRepository() {
        this.connection = DatabaseConnection.getInstance().getConnection();
    }

    @Override
    public List<Company> saveAll(Iterable<Company> itrbl) {
        return null;
    }

    @Override
    public List<Company> findAll() {
        String sql = String.format("SELECT %s FROM %s", fields, table);
        List<Company> companies = new ArrayList<>();
        try(Statement statement = connection.createStatement()){
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()){
                Company company = Company.builder()
                        .id(resultSet.getLong("company_id"))
                        .name(resultSet.getString("company_name"))
                        .code(resultSet.getString("company_code"))
                        .build();
                companies.add(company);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return companies;
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
    public Company save(Company company) {
        if (company!=null) {
            //String values = "10,OMEGA,12341234"; << example
            String sql = String.format("INSERT INTO %s (%s) VALUES (?,?,?)",table,fields);
            try(PreparedStatement preparedStatement = connection.prepareStatement(sql)){
                preparedStatement.setLong(1,company.getId());
                preparedStatement.setString(2,company.getName());
                preparedStatement.setString(3,company.getCode());
                preparedStatement.executeUpdate();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        return company;
    }

    @Override
    public Company getOne(Long id) {
        return findById(id)
                .map(e -> e)
                .orElseThrow(()-> new RuntimeException("Entity with id " + id + " not found"));
    }

    @Override
    public Optional<Company> findById(Long id) {
        String sql = String.format("SELECT %s FROM %s WHERE company_id = %s",fields,table,id);
        try(Statement statement = connection.createStatement()){
            ResultSet resultSet = statement.executeQuery(sql);
            if(resultSet.next()){
                return Optional.of(Company.builder()
                        .id(resultSet.getLong("company_id"))
                        .name(resultSet.getString("company_name"))
                        .code(resultSet.getString("company_code"))
                        .build());
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return Optional.empty();
    }

    //@Override
    public void update(Long id, Company company) {
        String fieldsAndValues = String.format("company_id=%s,company_name='%s',company_code='%s'",
                id,
                company.getName(),
                company.getCode());
        String sql = String.format("UPDATE %s SET %s WHERE company_id = %s",table,fieldsAndValues,id);
        try(Statement statement = connection.createStatement()){
            statement.executeUpdate(sql);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Override
    public void deleteById(Long id) {
        if (id!=null) {
            String sql = "DELETE FROM " + table + " WHERE company_id=" + id;
            try(Statement statement = connection.createStatement()){
                statement.executeUpdate(sql);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
    }

    //@Override
    public boolean existsById(Long id) {
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
