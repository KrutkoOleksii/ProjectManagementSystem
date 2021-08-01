package ua.goit.repository;

import lombok.SneakyThrows;
import ua.goit.model.Customer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

public class CustomerRepository implements BaseRepository<Integer, Customer>{
    private final Connection connection;
    private final String table = "hw2.customers";
    private final String fields = "customer_id,customer_name,customer_code";

    public CustomerRepository() {
        this.connection = DatabaseConnection.getInstance().getConnection();
    }

    @Override
    @SneakyThrows
    public List<Customer> saveAll(Iterable<Customer> itrbl) {
        return null;
    }

    @Override
    @SneakyThrows
    public Collection<Customer> findAll() {
        List<Customer> customers = new ArrayList<>();
        Statement statement = connection.createStatement();
        String sql = "SELECT " + fields + " FROM " + table;
        ResultSet resultSet = statement.executeQuery(sql);
        while (resultSet.next()){
            Customer customer = Customer.builder()
                    .id(resultSet.getInt("customer_id"))
                    .customer_name(resultSet.getString("customer_name"))
                    .customer_code(resultSet.getString("customer_code"))
                    .build();
            customers.add(customer);
        }
        statement.close();
        return customers;
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
    public void save(Customer customer) {
        if (customer!=null) {
            //String values = "10,OMEGA,12341234";
            String sql = "INSERT INTO "+table+" (" + fields + ") VALUES (?,?,?)";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1,customer.getId());
            preparedStatement.setString(2,customer.getCustomer_name());
            preparedStatement.setString(3,customer.getCustomer_code());

            ResultSet resultSet = preparedStatement.executeQuery();
        }
    }

    @Override
    @SneakyThrows
    public Customer getOne(Integer id) {
        return findById(id)
                .map(e -> e)
                .orElseThrow(()-> new RuntimeException("Entity with id " + id + " not found"));
    }

    @Override
    @SneakyThrows
    public Optional<Customer> findById(Integer id) {
        String sql = "SELECT " + fields + " FROM " + table + " WHERE customer_id = " + id;
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(sql);
        if(resultSet.next()){
            return resultSet.getObject("customer_id", Optional.class);
        };
        return Optional.empty();
    }

    @Override
    @SneakyThrows
    public void deleteById(Integer id) {
        if (id!=null) {
            Statement statement = connection.createStatement();
            String sql = "DELETE FROM " + table + " WHERE customer_id="+id;
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
