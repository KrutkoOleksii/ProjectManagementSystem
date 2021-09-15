package ua.goit.controller;

import ua.goit.model.Project;
import ua.goit.repository.ProjectRepository;

public class HandlerQuerySalaryOfProject extends HandlerMenu{

    public HandlerQuerySalaryOfProject(HandlerMenu handler) {
        super(handler);
    }

    @Override
    protected void apply(String[] command) {
        ProjectRepository projectRepository = new ProjectRepository(Project.class);
        System.out.println(projectRepository.salaryOfProject(Long.parseLong(command[2])));
        //new CompanyService().deleteEntity(Company.class, Long.valueOf(command[2]));
    }

    @Override
    protected boolean isApplicable(String[] command) {
        return command.length == 3 && "query".equalsIgnoreCase(command[0]) & "salaryofproject".equalsIgnoreCase(command[1]);
    }
}
