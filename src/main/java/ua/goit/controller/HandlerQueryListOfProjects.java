package ua.goit.controller;

import ua.goit.model.Project;
import ua.goit.repository.ProjectRepository;

public class HandlerQueryListOfProjects extends HandlerMenu{

    public HandlerQueryListOfProjects(HandlerMenu handler) {
        super(handler);
    }

    @Override
    protected void apply(String[] command) {
        ProjectRepository projectRepository = new ProjectRepository(Project.class);
        System.out.println(projectRepository.listOfProjects());
    }

    @Override
    protected boolean isApplicable(String[] command) {
        return command.length == 2 && "query".equalsIgnoreCase(command[0]) & "listofprojects".equalsIgnoreCase(command[1]);
    }
}
