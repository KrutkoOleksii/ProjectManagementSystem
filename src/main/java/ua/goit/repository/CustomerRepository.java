package ua.goit.repository;

import ua.goit.model.Customer;

import java.sql.*;
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
    public List<Customer> saveAll(Iterable<Customer> itrbl) {
        return null;
    }

    @Override
    public Collection<Customer> findAll() {
        String sql = String.format("SELECT %s FROM %s",fields,table);
        List<Customer> customers = new ArrayList<>();
        try(Statement statement = connection.createStatement()){
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()){
                Customer customer = Customer.builder()
                        .id(resultSet.getInt("customer_id"))
                        .customer_name(resultSet.getString("customer_name"))
                        .customer_code(resultSet.getString("customer_code"))
                        .build();
                customers.add(customer);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return customers;
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
    public void save(Customer customer) {
        if (customer!=null) {
            //String values = "10,OMEGA,12341234";
            String sql = String.format("INSERT INTO %s (%s) VALUES (?,?,?)",table,fields);
            try(PreparedStatement preparedStatement = connection.prepareStatement(sql)){
                preparedStatement.setInt(1,customer.getId());
                preparedStatement.setString(2,customer.getCustomer_name());
                preparedStatement.setString(3,customer.getCustomer_code());
                preparedStatement.executeUpdate();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
    }

    @Override
    public Customer getOne(Integer id) {
        return findById(id)
                .map(e -> e)
                .orElseThrow(()-> new RuntimeException("Entity with id " + id + " not found"));
    }

    @Override
    public Optional<Customer> findById(Integer id) {
        String sql = String.format("SELECT %s FROM %s WHERE customer_id = %s",fields,table,id.toString());
        try(Statement statement = connection.createStatement()){
            ResultSet resultSet = statement.executeQuery(sql);
            if(resultSet.next()){
                return Optional.of(new Customer(
                        resultSet.getInt("customer_id"),
                        resultSet.getString("customer_name"),
                        resultSet.getString("customer_code")
                ));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    public void update(Integer id, Customer customer) {
        String fieldsAndValues = String.format("customer_id=%s,customer_name='%s',customer_code='%s'",
                id,
                customer.getCustomer_name(),
                customer.getCustomer_code());
        String sql = String.format("UPDATE %s SET %s WHERE customer_id = %s",table,fieldsAndValues,id);
        try(Statement statement = connection.createStatement()){
            statement.executeUpdate(sql);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Override
    public void deleteById(Integer id) {
        if (id!=null) {
            String sql = String.format("DELETE FROM %s WHERE customer_id=%s",table,id.toString());
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
