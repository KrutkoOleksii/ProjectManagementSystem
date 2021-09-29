package ua.goit.controller;

import ua.goit.model.Developer;
import ua.goit.service.DeveloperService;

public class HandlerDeleteDeveloper extends HandlerMenu{

    public HandlerDeleteDeveloper(HandlerMenu handler) {
        super(handler);
    }

    @Override
    protected void apply(String[] command) {
        Developer developer = new DeveloperService().deleteEntity(Developer.class, HandlerNumeric.getLong(command[2]));
        System.out.println(developer==null ? "there is no developer with id "+ command[2] : "deleted developer: " + developer);
    }

    @Override
    protected boolean isApplicable(String[] command) {
        return command.length == 3 && "delete".equals(command[0]) & "developer".equals(command[1]);
    }
}
