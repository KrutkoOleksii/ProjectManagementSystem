package ua.goit.service;

import ua.goit.repository.DatabaseConnection;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Reporter {
    private final Connection connection;

    public Reporter() {
        this.connection = DatabaseConnection.getInstance().getConnection();
    }

    public void printReportSalaryOfProject(Integer id) {
        try(Statement statement = connection.createStatement()){
            ResultSet resultSet = statement.executeQuery(salaryOfProject(id));
            while (resultSet.next()){
                System.out.println("Salary of project "+resultSet.getString("project_name"));
                System.out.println(resultSet.getString("salary"));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void printReportDevelopersOfProject(Integer id) {
        try(Statement statement = connection.createStatement()){
            ResultSet resultSet = statement.executeQuery(developersOfProject(id));
            System.out.println("Developer of project with id = "+id);
            while (resultSet.next()){
                System.out.println(resultSet.getString("developer_name"));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void printReportDevelopersBySkill(String skill) {
        try(Statement statement = connection.createStatement()){
            ResultSet resultSet = statement.executeQuery(developersJava(skill));
            System.out.println("*** Skill - "+skill);
            while (resultSet.next()){
                System.out.println(resultSet.getString("developer_name"));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void printReportDevelopersByLevel(String level) {
        try(Statement statement = connection.createStatement()){
            ResultSet resultSet = statement.executeQuery(developersMiddle(level));
            System.out.println("*** Level - "+level);
            while (resultSet.next()){
                System.out.println(resultSet.getString("developer_name"));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void printReportProjectList() {
        try(Statement statement = connection.createStatement()){
            ResultSet resultSet = statement.executeQuery(projectList());
            System.out.println("*** All projects: (startDate - projectName - countOfDevs)");
            while (resultSet.next()){
                System.out.println(String.join(" - ",
                        resultSet.getDate("start_date").toString(),
                        resultSet.getString("project_name"),
                        resultSet.getString("count_of_devs")
                        ));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    private String salaryOfProject(Integer id) {
        //     * зарплату(сумму) всех разработчиков отдельного проекта
        return  String.format("SELECT project_name, sum(salary) as salary " +
                " FROM hw2.developers " +
                " INNER JOIN hw2.developer_project " +
                " ON developer_project.developer_id = developers.developer_id " +
                " INNER JOIN hw2.projects " +
                " ON developer_project.project_id = projects.project_id " +
                " WHERE projects.project_id=%s" +
                " GROUP BY project_name",id);
    }

    private String developersOfProject(Integer id) {
        //     * список разработчиков отдельного проекта
        return String.format("SELECT" +
                " developer_name" +
                " FROM hw2.developers" +
                " INNER JOIN hw2.developer_project" +
                " ON developer_project.developer_id = developers.developer_id" +
                " INNER JOIN hw2.projects" +
                " ON developer_project.project_id = projects.project_id" +
                " WHERE projects.project_id=%s",id);
    }

    private String developersJava(String skill) {
        //     * список всех Java разработчиков
        return String.format("SELECT" +
                " developer_name" +
                " FROM hw2.developers" +
                " INNER JOIN hw2.developer_skill" +
                " ON developer_skill.developer_id = developers.developer_id" +
                " INNER JOIN hw2.skills" +
                " ON developer_skill.skill_id = skills.skill_id" +
                " WHERE skills.skill_name='%s'",skill);
    }

    private String developersMiddle(String level) {
        //     * список всех Middle разработчиков
        return String.format("SELECT" +
                " developer_name" +
                " FROM hw2.developers" +
                " INNER JOIN hw2.developer_skill" +
                " ON developer_skill.developer_id = developers.developer_id" +
                " INNER JOIN hw2.skills" +
                " ON developer_skill.skill_id = skills.skill_id" +
                " WHERE skills.skill_level='%s'",level);
    }

    private String projectList() {
        //     * список проектов в следующем формате:
        //     дата создания - название проекта - количество разработчиков на этом проекте.
        return "SELECT" +
                " start_date," +
                " project_name," +
                " COUNT(developer_name) AS count_of_devs" +
                " FROM hw2.projects" +
                " INNER JOIN hw2.developer_project" +
                " ON developer_project.project_id = projects.project_id" +
                " INNER JOIN hw2.developers" +
                " ON developer_project.developer_id = developers.developer_id" +
                " GROUP BY project_name,start_date";
    }
}
