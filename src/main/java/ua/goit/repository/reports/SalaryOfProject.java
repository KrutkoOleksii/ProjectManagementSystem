package ua.goit.repository.reports;

import lombok.SneakyThrows;

import java.sql.ResultSet;

public class SalaryOfProject extends ReportImpl<Long>{

    @Override
    protected String getQuery(Long id) {
        //     * зарплату(сумму) всех разработчиков отдельного проекта
        return  String.format("SELECT project_name, sum(salary) as salary " +
                " FROM hw2.developers " +
                " INNER JOIN hw2.developer_project " +
                " ON developer_project.developer_id = developers.developer_id " +
                " INNER JOIN hw2.projects " +
                " ON developer_project.project_id = projects.project_id " +
                " WHERE projects.project_id=%s" +
                " GROUP BY project_name", id);
    }

    @SneakyThrows
    @Override
    protected String printResult(ResultSet resultSet, Long id) {
        while (resultSet.next()){
            return String.format("Salary of project %s : %s",
                    resultSet.getString("project_name"),
                    resultSet.getString("salary"));
        }
        return null;
    }
}
