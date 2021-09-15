package ua.goit.service;

import ua.goit.model.Project;
import ua.goit.repository.ProjectRepository;

public class ProjectService extends BaseService<Long, Project>{

    public String salaryOfProject(Long id) {
        return new ProjectRepository(Project.class).salaryOfProject(id);
    }

    public String listOfProject() {
        return new ProjectRepository(Project.class).listOfProjects();
    }

}
