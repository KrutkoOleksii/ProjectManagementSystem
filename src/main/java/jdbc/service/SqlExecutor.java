package jdbc.service;

import jdbc.model.Developer;
import jdbc.util.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class SqlExecutor {
    public static void main(String[] args) throws Exception {
        try(DatabaseConnection databaseConnection = DatabaseConnection.getInstance()){
            Connection connection = databaseConnection.getConnection();
            System.out.println(connection);

            PreparedStatement prepareStatement =
                    connection.prepareStatement("INSERT INTO hw2.developers (developer_id,developer_name,age,sex,salary,company_id) " +
                    "VALUES (?,?,?,?,?,?)");
            prepareStatement.setInt(1, 7);
            prepareStatement.setString(2, "Amanda");
            prepareStatement.setInt(3, 26);
            prepareStatement.setString(4, "F");
            prepareStatement.setInt(5, 2600);
            prepareStatement.setInt(6, 2);
            //prepareStatement.executeUpdate();
            prepareStatement.close();

            Statement statement = connection.createStatement();
            String sql = "SELECT * FROM hw2.developers";
            ResultSet resultSet = statement.executeQuery(sql);


            List<Developer> developers = new ArrayList<>();
            while (resultSet.next()){
                Developer developer = Developer.builder()
                        .id(resultSet.getInt("developer_id"))
                        .name(resultSet.getString("developer_name"))
                        .age(resultSet.getInt("age"))
                        .sex(resultSet.getString("sex"))
                        .salary(resultSet.getInt("salary"))
                        .company_id(resultSet.getInt("company_id"))
                        .build();
                developers.add(developer);
            }
            System.out.println(developers);
            statement.close();
        }
    }
}
