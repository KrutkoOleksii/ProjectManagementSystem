package ua.goit.controller;

import ua.goit.model.Project;
import ua.goit.service.ProjectService;

public class HandlerReadProject extends HandlerMenu{

    public HandlerReadProject(HandlerMenu handler) {
        super(handler);
    }

    @Override
    protected void apply(String[] command) {
        Project project = new ProjectService().readEntity(Project.class, HandlerNumeric.getLong(command[2]));
        System.out.println(project==null ? "there is no project with id "+ command[2] : project);
    }

    @Override
    protected boolean isApplicable(String[] command) {
        return command.length == 3 && "get".equals(command[0]) & "project".equals(command[1]);
    }
}
