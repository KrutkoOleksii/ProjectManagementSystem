package ua.goit.repository;

import lombok.SneakyThrows;

import java.sql.ResultSet;

public class ListOfProjects extends ReportImpl<Object> {

    @Override
    protected String getQuery(Object o) {
        //     * список проектов в следующем формате:
        //     дата создания - название проекта - количество разработчиков на этом проекте.
        return "SELECT" +
                " start_date," +
                " projects.name," +
                " COUNT(developers.name) AS count_of_devs" +
                " FROM hw2.projects" +
                " INNER JOIN hw2.developer_project" +
                " ON developer_project.project_id = projects.id" +
                " INNER JOIN hw2.developers" +
                " ON developer_project.developer_id = developers.id" +
                " GROUP BY projects.name,start_date";
    }

    @SneakyThrows
    @Override
    protected String printResult(ResultSet resultSet, Object o) {
        String result = ("*** All projects: (startDate - projectName - countOfDevs)\n");
        while (resultSet.next()) {
            result = String.join("",
                    result,
                    String.join(" - ",
                            resultSet.getDate("start_date").toString(),
                            resultSet.getString("projects.name"),
                            resultSet.getString("count_of_devs") + "\n"
                    ));
        }
        return result;
    }
}
