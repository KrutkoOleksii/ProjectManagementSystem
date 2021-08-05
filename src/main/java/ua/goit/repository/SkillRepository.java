package ua.goit.repository;

import lombok.SneakyThrows;
import ua.goit.model.Skill;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

public class SkillRepository implements BaseRepository<Integer, Skill>{
    private final Connection connection;
    private final String table = "hw2.skills";
    private final String fields = "skill_id,skill_name,skill_level";

    public SkillRepository() {
        this.connection = DatabaseConnection.getInstance().getConnection();
    }

    @Override
    public List<Skill> saveAll(Iterable<Skill> itrbl) {
        return null;
    }

    @Override
    @SneakyThrows
    public Collection<Skill> findAll() {
        List<Skill> skills = new ArrayList<>();
        Statement statement = connection.createStatement();
        String sql = String.format("SELECT %s FROM %s",fields,table);
        ResultSet resultSet = statement.executeQuery(sql);
        while (resultSet.next()){
            Skill skill = Skill.builder()
                    .id(resultSet.getInt("skill_id"))
                    .skill_name(resultSet.getString("skill_name"))
                    .skill_level(resultSet.getString("skill_level"))
                    .build();
            skills.add(skill);
        }
        statement.close();
        return skills;
    }

    @Override
    @SneakyThrows
    public void deleteAll() {
        Statement statement = connection.createStatement();
        String sql = "DELETE FROM " + table;
        ResultSet resultSet = statement.executeQuery(sql);
    }

    @Override
    @SneakyThrows
    public void save(Skill skill) {
        if (skill!=null) {
            //String values = "20,React,Junior"; << example
            String sql = String.format("INSERT INTO %s (%s) VALUES (?,?,?)",table,fields);
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1,skill.getId());
            preparedStatement.setString(2,skill.getSkill_name());
            preparedStatement.setString(3,skill.getSkill_level());
            preparedStatement.executeUpdate();
        }
    }

    @Override
    @SneakyThrows
    public Skill getOne(Integer id) {
        return findById(id)
                .map(e -> e)
                .orElseThrow(()-> new RuntimeException("Entity with id " + id + " not found"));
    }

    @Override
    @SneakyThrows
    public Optional<Skill> findById(Integer id) {
        String sql = String.format("SELECT %s FROM %s WHERE skill_id = %s",fields,table,id);
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(sql);
        if(resultSet.next()){
            return resultSet.getObject("skill_id", Optional.class);
        };
        return Optional.empty();
    }

    @Override
    public void update(Integer integer, Skill skill) {

    }

    @Override
    @SneakyThrows
    public void deleteById(Integer id) {
        if (id!=null) {
            Statement statement = connection.createStatement();
            String sql = String.format("DELETE FROM %s WHERE skill_id = %s",table,id);
            ResultSet resultSet = statement.executeQuery(sql);
        }
    }

    @Override
    public boolean existsById(Integer id) {
        return false;
    }

    @Override
    @SneakyThrows
    public long count() {
        Statement statement = connection.createStatement();
        String sql = "SELECT COUNT(*) FROM " + table;
        ResultSet resultSet = statement.executeQuery(sql);
        return resultSet.getInt("COUNT(*)");
    }
}

