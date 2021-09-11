package ua.goit.repository;

import lombok.SneakyThrows;

import java.sql.ResultSet;

public class SalaryOfProject extends ReportImpl<Long> {

    @Override
    protected String getQuery(Long id) {
        //     * зарплату(сумму) всех разработчиков отдельного проекта
        return String.format("SELECT projects.name, sum(salary) as salary " +
                " FROM hw2.developers " +
                " INNER JOIN hw2.developer_project " +
                " ON developer_project.developer_id = developers.id " +
                " INNER JOIN hw2.projects " +
                " ON developer_project.project_id = projects.id " +
                " WHERE projects.id=%s" +
                " GROUP BY projects.name", id);
    }

    @SneakyThrows
    @Override
    protected String printResult(ResultSet resultSet, Long id) {
        while (resultSet.next()) {
            return String.format("Salary of project %s : %s",
                    resultSet.getString("projects.name"),
                    resultSet.getString("salary"));
        }
        return null;
    }
}
