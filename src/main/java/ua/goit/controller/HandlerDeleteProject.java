package ua.goit.controller;

import ua.goit.model.Project;
import ua.goit.service.ProjectService;

public class HandlerDeleteProject extends HandlerMenu{

    public HandlerDeleteProject(HandlerMenu handler) {
        super(handler);
    }

    @Override
    protected void apply(String[] command) {
        new ProjectService().deleteEntity(Project.class, Long.getLong(command[2]));
    }

    @Override
    protected boolean isApplicable(String[] command) {
        return "delete".equals(command[0]) & "project".equals(command[2]);
    }
}
