package ua.goit.service.reports;

import lombok.SneakyThrows;

import java.sql.ResultSet;

public class DevelopersWithSkill extends ReportImpl<String> {

    @Override
    protected String getQuery(String skill) {
        //     * список всех 'Java' разработчиков
        return String.format("SELECT" +
                " developers.name" +
                " FROM hw2.developers" +
                " INNER JOIN hw2.developer_skill" +
                " ON developer_skill.developer_id = developers.id" +
                " INNER JOIN hw2.skills" +
                " ON developer_skill.skill_id = skills.id" +
                " WHERE skills.name='%s'", skill);
    }

    @SneakyThrows
    @Override
    protected String printResult(ResultSet resultSet, String skill) {
        String result = String.format("*** Developers with skill '%s':\n", skill);
        while (resultSet.next()){
            result = String.join("", result, resultSet.getString("developers.name"),"\n");
        }
        return result;
    }
}
