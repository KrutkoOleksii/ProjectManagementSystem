//package ua.goit.service.old;
//
//import lombok.SneakyThrows;
//import ua.goit.model.Skill;
//import ua.goit.repository.BaseRepository;
//import ua.goit.util.DatabaseConnection;
//
//import java.sql.*;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Optional;
//
//public class SkillRepository implements BaseRepository<Long, Skill> {
//    private final Connection connection;
//    private final String table = "hw2.skills";
//    private final String fields = "skill_id,skill_name,skill_level";
//
//    public SkillRepository() {
//        this.connection = DatabaseConnection.getInstance().getConnection();
//    }
//
//    @Override
//    public List<Skill> saveAll(Iterable<Skill> itrbl) {
//        return null;
//    }
//
//    @Override
//    public List<Skill> findAll() {
//        String sql = String.format("SELECT %s FROM %s",fields,table);
//        List<Skill> skills = new ArrayList<>();
//        try (Statement statement = connection.createStatement()){
//            ResultSet resultSet = statement.executeQuery(sql);
//            while (resultSet.next()){
//                Skill skill = Skill.builder()
//                        .id(resultSet.getLong("skill_id"))
//                        .name(resultSet.getString("skill_name"))
//                        .skillLevel(resultSet.getString("skill_level"))
//                        .build();
//                skills.add(skill);
//            }
//        } catch (SQLException throwables) {
//            throwables.printStackTrace();
//        }
//        return skills;
//    }
//
//    //@Override
//    public void deleteAll() {
//        String sql = "DELETE FROM " + table;
//        try (Statement statement = connection.createStatement();){
//            statement.executeUpdate(sql);
//        } catch (SQLException throwables) {
//            throwables.printStackTrace();
//        }
//    }
//
//    @Override
//    public Skill save(Skill skill) {
//        if (skill!=null) {
//            //String values = "20,React,Junior"; << example
//            String sql = String.format("INSERT INTO %s (%s) VALUES (?,?,?)",table,fields);
//            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)){
//                preparedStatement.setLong(1,skill.getId());
//                preparedStatement.setString(2,skill.getName());
//                preparedStatement.setString(3,skill.getSkillLevel());
//                preparedStatement.executeUpdate();
//            } catch (SQLException throwables) {
//                throwables.printStackTrace();
//            }
//        }
//        return skill;
//    }
//
//    @Override
//    public Skill getOne(Long id) {
//        return findById(id)
//                .map(e -> e)
//                .orElseThrow(()-> new RuntimeException("Entity with id " + id + " not found"));
//    }
//
//    @Override
//    public Optional<Skill> findById(Long id) {
//        String sql = String.format("SELECT %s FROM %s WHERE skill_id = %s",fields,table,id);
//        try(Statement statement = connection.createStatement()){
//            ResultSet resultSet = statement.executeQuery(sql);
//            if(resultSet.next()){
//                return Optional.of(new Skill(
//                        resultSet.getLong("skill_id"),
//                        resultSet.getString("skill_name"),
//                        resultSet.getString("skill_level")
//                ));
//            }
//        } catch (SQLException throwables) {
//            throwables.printStackTrace();
//        }
//        return Optional.empty();
//    }
//
//    //@Override
//    public void update(Long id, Skill skill) {
//        String fieldsAndValues = String.format("skill_id=%s,skill_name='%s',skill_level='%s'",
//                id,
//                skill.getName(),
//                skill.getSkillLevel());
//        String sql = String.format("UPDATE %s SET %s WHERE skill_id = %s",table,fieldsAndValues,id);
//        try(Statement statement = connection.createStatement()){
//            statement.executeUpdate(sql);
//        } catch (SQLException throwables) {
//            throwables.printStackTrace();
//        }
//    }
//
//    @Override
//    public void deleteById(Long id) {
//        if (id!=null) {
//            String sql = String.format("DELETE FROM %s WHERE skill_id = %s",table,id);
//            try(Statement statement = connection.createStatement();){
//                statement.executeUpdate(sql);
//            } catch (SQLException throwables) {
//                throwables.printStackTrace();
//            }
//        }
//    }
//    @SneakyThrows
//    @Override
//    public void close() {
//        connection.close();
//    }
//    //@Override
//    public boolean existsById(Long id) {
//        return false;
//    }
//
//    //@Override
//    public long count() {
//        String sql = "SELECT COUNT(*) FROM " + table;
//        try(Statement statement = connection.createStatement()){
//            ResultSet resultSet = statement.executeQuery(sql);
//            return resultSet.getInt("COUNT(*)");
//        } catch (SQLException throwables) {
//            throwables.printStackTrace();
//        }
//        return 0;
//    }
//
//}
//
