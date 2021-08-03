package ua.goit.repository;

import lombok.SneakyThrows;
import ua.goit.model.Company;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

public class CompanyRepository implements BaseRepository<Integer, Company>{
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
    @SneakyThrows
    public Collection<Company> findAll() {
        List<Company> companies = new ArrayList<>();
        Statement statement = connection.createStatement();
        String sql = String.format("SELECT %s FROM %s", fields, table);
        ResultSet resultSet = statement.executeQuery(sql);
        while (resultSet.next()){
            Company company = Company.builder()
                    .id(resultSet.getInt("company_id"))
                    .company_name(resultSet.getString("company_name"))
                    .company_code(resultSet.getString("company_code"))
                    .build();
            companies.add(company);
        }
        statement.close();
        return companies;
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
    public void save(Company company) {
        if (company!=null) {
            //String values = "10,OMEGA,12341234"; << example
            String sql = "INSERT INTO "+table+" (" + fields + ") VALUES (?,?,?)";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1,company.getId());
            preparedStatement.setString(2,company.getCompany_name());
            preparedStatement.setString(3,company.getCompany_code());
            preparedStatement.executeUpdate();
        }
    }

    @Override
    @SneakyThrows
    public Company getOne(Integer id) {
        return findById(id)
                .map(e -> e)
                .orElseThrow(()-> new RuntimeException("Entity with id " + id + " not found"));
    }

    @Override
    @SneakyThrows
    public Optional<Company> findById(Integer id) {
        String sql = String.format("SELECT %s FROM %s WHERE company_id = %s",fields,table,id);
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(sql);
        if(resultSet.next()){

            //Company object = resultSet.getObject("company_id", Company.class);

            int company_id = resultSet.getInt("company_id");
            String company_name = resultSet.getString("company_name");
            String company_code = resultSet.getString("company_code");
            Company company = new Company(company_id, company_name, company_code);
            Optional<Company> companyOptional = Optional.ofNullable(company);
            return companyOptional;
        };
        return Optional.empty();
    }

    @Override
    public void update(Integer id, Company company) {

    }

    @Override
    @SneakyThrows
    public void deleteById(Integer id) {
        if (id!=null) {
            Statement statement = connection.createStatement();
            String sql = "DELETE FROM " + table + " WHERE company_id=" + id;
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
