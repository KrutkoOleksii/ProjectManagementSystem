package ua.goit.service;

import com.sun.source.tree.WhileLoopTree;
import lombok.SneakyThrows;
import ua.goit.repository.DatabaseConnection;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class Reporter {
    private final Connection connection;

    public Reporter() {
        this.connection = DatabaseConnection.getInstance().getConnection();
    }

    @SneakyThrows
    public void printReport(Integer id) {
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(salaryOfProject(id));
        while (resultSet.next()){
            System.out.println(resultSet.getString("project_name"));
            System.out.println(resultSet.getString("salary"));
        }
    }

    private String salaryOfProject(Integer id) {
        //     * зарплату(сумму) всех разработчиков отдельного проекта
        return  "SELECT project_name, sum(salary) as salary " +
                " FROM hw2.developers " +
                " INNER JOIN hw2.developer_project " +
                " ON developer_project.developer_id = developers.developer_id " +
                " INNER JOIN hw2.projects " +
                " ON developer_project.project_id = projects.project_id " +
                " WHERE projects.project_id = " + id +
                " GROUP BY project_name";
    }

    private String developersOfProject(Integer id) {
        //     * список разработчиков отдельного проекта
        return "SELECT" +
                " developer_name" +
                " FROM developers" +
                " INNER JOIN developer_project" +
                " ON developer_project.developer_id = developers.developer_id" +
                " INNER JOIN projects" +
                " ON developer_project.project_id = projects.project_id" +
                " WHERE projects.project_id = " + id;
    }

    private String developersJava(String skill) {
        //     * список всех Java разработчиков
        return "SELECT" +
                " developer_name" +
                " FROM developers" +
                " INNER JOIN developer_skill" +
                " ON developer_skill.developer_id = developers.developer_id" +
                " INNER JOIN skills" +
                " ON developer_skill.skill_id = skills.skill_id" +
                " WHERE skills.skill_name = " + skill; // if skill = 'Java'
    }

    private String developersMiddle(String level) {
        //     * список всех Middle разработчиков
        return "SELECT" +
                " developer_name" +
                " FROM developers" +
                " INNER JOIN developer_skill" +
                " ON developer_skill.developer_id = developers.developer_id" +
                " INNER JOIN skills" +
                " ON developer_skill.skill_id = skills.skill_id" +
                " WHERE skills.skill_level = " + level; // if level = 'Middle'
    }

    // список проектов в следующем формате:
    // дата создания - название проекта - количество разработчиков на этом проекте.

    private String projectList(String level) {
        //     * список всех Middle разработчиков
        return "SELECT" +
                " project_name" +
                " FROM developers" +
                " INNER JOIN developer_project" +
                " ON developer_project.developer_id = developers.developer_id" +
                " INNER JOIN projects" +
                " ON developer_project.project_id = projects.project_id";
    }
}
