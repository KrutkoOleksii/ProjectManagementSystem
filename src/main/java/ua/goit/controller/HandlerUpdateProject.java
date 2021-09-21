package ua.goit.controller;

import ua.goit.model.Company;
import ua.goit.model.Customer;
import ua.goit.model.Project;
import ua.goit.service.CompanyService;
import ua.goit.service.CustomerService;
import ua.goit.service.ProjectService;

import java.util.Optional;

public class HandlerUpdateProject extends HandlerMenu{
    public HandlerUpdateProject(HandlerMenu handler) {
        super(handler);
    }

    @Override
    protected void apply(String[] command) {
        Project project = new ProjectService().readEntity(Project.class, Long.valueOf(command[2]));
        System.out.println("Project for update is:\n" + project.toString());
        System.out.println("enter the new parameters of the project:\n" +
                "{name}|{cost}|{startDate}|{companyId}|{customerId}");
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
        Project updatedProject = Project.builder()
                .id(Long.valueOf(command[2]))
                .name(split[0])
                .cost(Integer.valueOf(split[1]))
                .startDate(split[2])
                .companyId(Long.valueOf(companyId))
                .customerId(Long.valueOf(customerId))
                .build();
        System.out.println("updated project: " + new ProjectService().updateEntity(Project.class, updatedProject).toString());
    }

    @Override
    protected boolean isApplicable(String[] command) {
        return command.length == 3 && "update".equals(command[0]) & "project".equals(command[1]);
    }
}