package ua.goit.controller;

import ua.goit.model.Developer;
import ua.goit.service.DeveloperService;

public class HandlerReadDeveloper extends HandlerMenu{
    public HandlerReadDeveloper(HandlerMenu handler) {
        super(handler);
    }


    @Override
    protected void apply(String[] command) {
        Developer developer = new DeveloperService().readEntity(Developer.class, HandlerNumeric.getLong(command[2]));
        System.out.println(developer==null ? "there is no developer with id "+ command[2] : developer);
    }

    @Override
    protected boolean isApplicable(String[] command) {
        return command.length == 3 && "get".equals(command[0]) & "developer".equals(command[1]);
    }
}
