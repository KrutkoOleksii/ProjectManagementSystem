package ua.goit.controller;

import ua.goit.service.DeveloperService;

public class HandlerQueryDevelopersOfProject extends HandlerMenu{

    public HandlerQueryDevelopersOfProject(HandlerMenu handler) {
        super(handler);
    }

    @Override
    protected void apply(String[] command) {
        System.out.println(new DeveloperService().developersOfProject(Long.parseLong(command[2])));
    }

    @Override
    protected boolean isApplicable(String[] command) {
        return command.length == 3 && "query".equalsIgnoreCase(command[0]) & "DevelopersOfProject".equalsIgnoreCase(command[1]);
    }
}
