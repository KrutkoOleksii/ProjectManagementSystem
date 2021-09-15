package ua.goit.controller;

import ua.goit.model.Developer;
import ua.goit.repository.DeveloperRepository;

public class HandlerQueryDeveloperWithLevel extends HandlerMenu{

    public HandlerQueryDeveloperWithLevel(HandlerMenu handler) {
        super(handler);
    }

    @Override
    protected void apply(String[] command) {
        DeveloperRepository developerRepository = new DeveloperRepository(Developer.class);
        System.out.println(developerRepository.developerWithLevel(command[2]));
    }

    @Override
    protected boolean isApplicable(String[] command) {
        return command.length == 3 && "query".equalsIgnoreCase(command[0]) & "developerwithlevel".equalsIgnoreCase(command[1]);
    }
}
