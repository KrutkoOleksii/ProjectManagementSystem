package ua.goit.controller;

import ua.goit.model.Company;
import ua.goit.model.Customer;
import ua.goit.model.Project;
import ua.goit.service.CompanyService;
import ua.goit.service.CustomerService;
import ua.goit.service.ProjectService;

import java.util.Optional;

public class HandlerCreateProject extends HandlerMenu{
    public HandlerCreateProject(HandlerMenu handler) {
        super(handler);
    }

    @Override
    protected void apply(String[] command) {
        Project newProject = getNewProject();
        Project project = new ProjectService().createEntity(Project.class, newProject);
        System.out.println("new project: " + project.toString());
    }

    @Override
    protected boolean isApplicable(String[] command) {
        return command.length == 2 && "create".equals(command[0]) & "project".equals(command[1]);
    }

    protected Project getNewProject() {
        System.out.println("enter the parameters of the new project:\n" +
                "{name}|{cost}|{startDate}|{companyId}|{customerId}\n" +
                "( e.g. Alpha|1_000_000|2021-09-09|3|1)");
        String next = scanner.next();
        String[] split = next.split("\\|");
        Long companyId = Long.valueOf(split[3]);
        while (Optional.empty().equals(new CompanyService().findById(Company.class, companyId))) {
            System.out.println("No one company with id = " + companyId + "\nenter another id company:");
            companyId = scanner.nextLong();
        }
        Long customerId = Long.valueOf(split[4]);
        while (Optional.empty().equals(new CustomerService().findById(Customer.class, customerId))) {
            System.out.println("No one customer with id = " + customerId + "\nenter another id customer:");
            customerId = scanner.nextLong();
        }
        return Project.builder()
                .name(split[0])
                .cost(Integer.valueOf(split[1]))
                .startDate(split[2])
                .companyId(companyId)
                .customerId(customerId)
                .build();
    }
}