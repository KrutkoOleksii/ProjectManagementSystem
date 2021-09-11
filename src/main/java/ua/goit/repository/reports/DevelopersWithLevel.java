package ua.goit.repository.reports;

import lombok.SneakyThrows;

import java.sql.ResultSet;

public class DevelopersWithLevel extends ReportImpl<String> {

    @Override
    protected String getQuery(String level) {
        //     * список всех Middle разработчиков
        return String.format("SELECT" +
                " developers.name" +
                " FROM hw2.developers" +
                " INNER JOIN hw2.developer_skill" +
                " ON developer_skill.developer_id = developers.id" +
                " INNER JOIN hw2.skills" +
                " ON developer_skill.skill_id = skills.id" +
                " WHERE skills.skill_level='%s'",level);
    }

    @SneakyThrows
    @Override
    protected String printResult(ResultSet resultSet, String level) {
        String result = String.format("*** Developers with level '%s':\n", level);
        while (resultSet.next()){
            result = String.join("", result, resultSet.getString("developers.name"),"\n");
        }
        return result;
    }
}
