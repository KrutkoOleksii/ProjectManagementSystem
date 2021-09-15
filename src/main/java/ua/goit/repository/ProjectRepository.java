package ua.goit.repository;

import lombok.SneakyThrows;
import ua.goit.model.Project;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class ProjectRepository extends BaseRepositoryImpl<Long, Project>{

    private final PreparedStatement listOfProjectPreparedStatement;
    private final PreparedStatement salaryOfProjectPreparedStatement;

    @SneakyThrows
    public ProjectRepository(Class<Project> modelClass) {
        super(modelClass);
        this.listOfProjectPreparedStatement = connection.prepareStatement(
                "SELECT" +
                        " start_date," +
                        " projects.name," +
                        " COUNT(developers.name) AS count_of_devs" +
                        " FROM hw2.projects" +
                        " INNER JOIN hw2.developer_project" +
                        " ON developer_project.project_id = projects.id" +
                        " INNER JOIN hw2.developers" +
                        " ON developer_project.developer_id = developers.id" +
                        " GROUP BY projects.name,start_date"
        );
        this.salaryOfProjectPreparedStatement = connection.prepareStatement(
                "SELECT projects.name, sum(salary) as salary " +
                        " FROM hw2.developers " +
                        " INNER JOIN hw2.developer_project " +
                        " ON developer_project.developer_id = developers.id " +
                        " INNER JOIN hw2.projects " +
                        " ON developer_project.project_id = projects.id " +
                        " WHERE projects.id=?" +
                        " GROUP BY projects.name"
        );
    }

    @SneakyThrows
    public String listOfProjects(){
        ResultSet resultSet = listOfProjectPreparedStatement.executeQuery();
        return printListOfProject(resultSet);
    }
    @SneakyThrows
    public String salaryOfProject(Long id){
        salaryOfProjectPreparedStatement.setLong(1, id);
        ResultSet resultSet = salaryOfProjectPreparedStatement.executeQuery();
        return printSalaryOfProject(resultSet, id);
    }

    @SneakyThrows
    protected String printListOfProject(ResultSet resultSet) {
        String result = ("*** All projects: (startDate - projectName - countOfDevs)\n");
        while (resultSet.next()){
            result = String.join("",
                    result,
                    String.join(" - ",
                            resultSet.getDate("start_date").toString(),
                            resultSet.getString("projects.name"),
                            resultSet.getString("count_of_devs")+"\n"
                    ));
        }
        return result;
    }
    @SneakyThrows
    protected String printSalaryOfProject(ResultSet resultSet, Long id) {
        while (resultSet.next()){
            return String.format("Salary of project %s : %s",
                    resultSet.getString("projects.name"),
                    resultSet.getString("salary"));
        }
        return null;
    }
}
