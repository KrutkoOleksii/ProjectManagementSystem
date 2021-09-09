package ua.goit.repository;

import ua.goit.model.Project;

public class ProjectRepository extends BaseRepositoryImpl<Long, Project>{
    public ProjectRepository(Class<Project> modelClass) {
        super(modelClass);
    }
}
