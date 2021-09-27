package ua.goit.controller;

import ua.goit.model.Project;
import ua.goit.service.ProjectService;

public class HandlerDeleteProject extends HandlerMenu{

    public HandlerDeleteProject(HandlerMenu handler) {
        super(handler);
    }

    @Override
    protected void apply(String[] command) {
        Project project = new ProjectService().deleteEntity(Project.class, Long.valueOf(command[2]));
        System.out.println("deleted project: " + project);
    }

    @Override
    protected boolean isApplicable(String[] command) {
        return command.length == 3 && "delete".equals(command[0]) & "project".equals(command[1]);

    }
}
