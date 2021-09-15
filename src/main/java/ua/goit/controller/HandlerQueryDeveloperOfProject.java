package ua.goit.controller;

import ua.goit.model.Developer;
import ua.goit.repository.DeveloperRepository;

public class HandlerQueryDeveloperOfProject extends HandlerMenu{

    public HandlerQueryDeveloperOfProject(HandlerMenu handler) {
        super(handler);
    }

    @Override
    protected void apply(String[] command) {
        DeveloperRepository developerRepository = new DeveloperRepository(Developer.class);
        System.out.println(developerRepository.developerOfProject(Long.parseLong(command[2])));
        //new CompanyService().deleteEntity(Company.class, Long.valueOf(command[2]));
    }

    @Override
    protected boolean isApplicable(String[] command) {
        return command.length == 3 && "query".equalsIgnoreCase(command[0]) & "developerofproject".equalsIgnoreCase(command[1]);
    }
}
