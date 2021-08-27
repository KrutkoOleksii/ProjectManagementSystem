package ua.goit.repository.reports;

import lombok.SneakyThrows;

import java.sql.ResultSet;

public class DevelopersWithSkill extends ReportImpl<String> {

    @Override
    protected String getQuery(String skill) {
        //     * список всех 'Java' разработчиков
        return String.format("SELECT" +
                " developer_name" +
                " FROM hw2.developers" +
                " INNER JOIN hw2.developer_skill" +
                " ON developer_skill.developer_id = developers.developer_id" +
                " INNER JOIN hw2.skills" +
                " ON developer_skill.skill_id = skills.skill_id" +
                " WHERE skills.skill_name='%s'", skill);
    }

    @SneakyThrows
    @Override
    protected String printResult(ResultSet resultSet, String skill) {
        String result = String.format("*** Developers with skill '%s':\n", skill);
        while (resultSet.next()){
            String.join("", result, resultSet.getString("developer_name"));
        }
        return result;
    }
}
