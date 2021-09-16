package ua.goit.repository;

import lombok.SneakyThrows;
import ua.goit.model.Developer;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class DeveloperRepository extends BaseRepositoryImpl<Long, Developer>{

    private final PreparedStatement developerOfProjectPreparedStatement;
    private final PreparedStatement developerWithLevelPreparedStatement;
    private final PreparedStatement developerWithSkillPreparedStatement;

    @SneakyThrows
    public DeveloperRepository(Class<Developer> modelClass) {
        super(modelClass);
        this.developerOfProjectPreparedStatement = connection.prepareStatement(
                "SELECT" +
                        " developers.name" +
                        " FROM "+databaseSchemaName+".developers" +
                        " INNER JOIN "+databaseSchemaName+".developer_project" +
                        " ON developer_project.developer_id = developers.id" +
                        " INNER JOIN "+databaseSchemaName+".projects" +
                        " ON developer_project.project_id = projects.id" +
                        " WHERE projects.id=?");
        this.developerWithLevelPreparedStatement = connection.prepareStatement(
                "SELECT" +
                        " developers.name" +
                        " FROM "+databaseSchemaName+".developers" +
                        " INNER JOIN "+databaseSchemaName+".developer_skill" +
                        " ON developer_skill.developer_id = developers.id" +
                        " INNER JOIN "+databaseSchemaName+".skills" +
                        " ON developer_skill.skill_id = skills.id" +
                        " WHERE skills.skill_level=?");
        this.developerWithSkillPreparedStatement = connection.prepareStatement(
                "SELECT" +
                        " developers.name" +
                        " FROM "+databaseSchemaName+".developers" +
                        " INNER JOIN "+databaseSchemaName+".developer_skill" +
                        " ON developer_skill.developer_id = developers.id" +
                        " INNER JOIN "+databaseSchemaName+".skills" +
                        " ON developer_skill.skill_id = skills.id" +
                        " WHERE skills.name=?");
    }

    @SneakyThrows
    public String developerOfProject(Long id){
        developerOfProjectPreparedStatement.setLong(1, id);
        return stringDeveloperOfProject(developerOfProjectPreparedStatement.executeQuery(), id);
    }

    @SneakyThrows
    public String developerWithLevel(String level){
        developerWithLevelPreparedStatement.setString(1, level);
        return stringDeveloperWithLevel(developerWithLevelPreparedStatement.executeQuery(), level);
    }

    @SneakyThrows
    public String developerWithSkill(String skill){
        developerWithSkillPreparedStatement.setString(1, skill);
        return stringDeveloperWithSkill(developerWithSkillPreparedStatement.executeQuery(), skill);
    }

    @SneakyThrows
    private String stringDeveloperOfProject(ResultSet resultSet, Long id) {
        String result = String.format("Developer of project with id = %s:\n",id);
        while (resultSet.next()){
            result = String.join("",result, resultSet.getString("developers.name"),"\n");
        }
        return result;
    }
    @SneakyThrows
    private String stringDeveloperWithLevel(ResultSet resultSet, String level) {
        String result = String.format("*** Developers with level '%s':\n", level);
        while (resultSet.next()){
            result = String.join("", result, resultSet.getString("developers.name"),"\n");
        }
        return result;
    }
    @SneakyThrows
    private String stringDeveloperWithSkill(ResultSet resultSet, String skill) {
        String result = String.format("*** Developers with skill '%s':\n", skill);
        while (resultSet.next()){
            result = String.join("", result, resultSet.getString("developers.name"),"\n");
        }
        return result;
    }
}
