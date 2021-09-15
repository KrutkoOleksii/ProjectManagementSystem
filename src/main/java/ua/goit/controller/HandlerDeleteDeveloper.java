package ua.goit.controller;

import ua.goit.model.Developer;
import ua.goit.service.DeveloperService;

public class HandlerDeleteDeveloper extends HandlerMenu{

    public HandlerDeleteDeveloper(HandlerMenu handler) {
        super(handler);
    }

    @Override
    protected void apply(String[] command) {
        new DeveloperService().deleteEntity(Developer.class, Long.valueOf(command[2]));
    }

    @Override
    protected boolean isApplicable(String[] command) {
        return command.length == 3 && "delete".equals(command[0]) & "developer".equals(command[1]);
    }
}
