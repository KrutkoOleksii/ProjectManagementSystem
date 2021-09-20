package ua.goit.controller;

import ua.goit.service.ProjectService;

public class HandlerQuerySalaryOfProject extends HandlerMenu{

    public HandlerQuerySalaryOfProject(HandlerMenu handler) {
        super(handler);
    }

    @Override
    protected void apply(String[] command) {
        System.out.println(new ProjectService().salaryOfProject(Long.parseLong(command[2])));
    }

    @Override
    protected boolean isApplicable(String[] command) {
        return command.length == 3 && "query".equalsIgnoreCase(command[0]) & "SalaryOfProject".equalsIgnoreCase(command[1]);
    }
}
