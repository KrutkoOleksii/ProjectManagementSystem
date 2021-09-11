package ua.goit.repository.reports;

import lombok.SneakyThrows;

import java.sql.ResultSet;

public class DevelopersOfProject extends ReportImpl<Long> {

    @Override
    protected String getQuery(Long id) {
        //     * список разработчиков отдельного проекта
        return String.format("SELECT" +
                " developers.name" +
                " FROM hw2.developers" +
                " INNER JOIN hw2.developer_project" +
                " ON developer_project.developer_id = developers.id" +
                " INNER JOIN hw2.projects" +
                " ON developer_project.project_id = projects.id" +
                " WHERE projects.id=%s",id);
    }

    @SneakyThrows
    @Override
    protected String printResult(ResultSet resultSet, Long id) {
        String result = String.format("Developer of project with id = %s:\n",id);
        while (resultSet.next()){
            result = String.join("",result, resultSet.getString("developers.name"),"\n");
        }
        return result;
    }
}
