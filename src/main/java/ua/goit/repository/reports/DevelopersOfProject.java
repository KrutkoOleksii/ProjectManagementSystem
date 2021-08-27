package ua.goit.repository.reports;

import lombok.SneakyThrows;

import java.sql.ResultSet;

public class DevelopersOfProject extends ReportImpl<Long> {

    @Override
    protected String getQuery(Long id) {
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

    @SneakyThrows
    @Override
    protected String printResult(ResultSet resultSet, Long id) {
        String result = String.format("Developer of project with id = %s:\n",id);
        while (resultSet.next()){
            String.join("",result, resultSet.getString("developer_name"));
        }
        return result;
    }
}
