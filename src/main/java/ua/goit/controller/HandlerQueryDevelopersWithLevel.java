package ua.goit.controller;

import ua.goit.service.DeveloperService;

public class HandlerQueryDevelopersWithLevel extends HandlerMenu{

    public HandlerQueryDevelopersWithLevel(HandlerMenu handler) {
        super(handler);
    }

    @Override
    protected void apply(String[] command) {
        System.out.println(new DeveloperService().developersWithLevel(command[2]));
    }

    @Override
    protected boolean isApplicable(String[] command) {
        return command.length == 3 && "query".equalsIgnoreCase(command[0]) & "DevelopersWithLevel".equalsIgnoreCase(command[1]);
    }
}
