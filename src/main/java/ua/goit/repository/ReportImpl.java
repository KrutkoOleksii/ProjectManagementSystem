package ua.goit.repository;

import ua.goit.util.DatabaseConnection;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public abstract class ReportImpl<K> implements Report<K> {
    private final Connection connection;

    public ReportImpl() {
        this.connection = DatabaseConnection.getInstance().getConnection();
    }

    @Override
    public String getReport(K k) {
        try (Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(getQuery(k));
            return printResult(resultSet, k);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    protected abstract String getQuery(K k);

    protected abstract String printResult(ResultSet resultSet, K k);

}
