package ua.goit.controller;

import ua.goit.service.DeveloperService;

public class HandlerQueryDevelopersWithSkill extends HandlerMenu{

    public HandlerQueryDevelopersWithSkill(HandlerMenu handler) {
        super(handler);
    }

    @Override
    protected void apply(String[] command) {
        System.out.println(new DeveloperService().developersWithSkill(command[2]));
   }

    @Override
    protected boolean isApplicable(String[] command) {
        return command.length == 3 && "query".equalsIgnoreCase(command[0]) & "DevelopersWithSkill".equalsIgnoreCase(command[1]);
    }
}
