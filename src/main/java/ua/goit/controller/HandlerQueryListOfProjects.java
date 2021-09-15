package ua.goit.controller;

import ua.goit.service.ProjectService;

public class HandlerQueryListOfProjects extends HandlerMenu{

    public HandlerQueryListOfProjects(HandlerMenu handler) {
        super(handler);
    }

    @Override
    protected void apply(String[] command) {
        System.out.println(new ProjectService().listOfProject());
    }

    @Override
    protected boolean isApplicable(String[] command) {
        return command.length == 2 && "query".equalsIgnoreCase(command[0]) & "ListOfProject".equalsIgnoreCase(command[1]);
    }
}
