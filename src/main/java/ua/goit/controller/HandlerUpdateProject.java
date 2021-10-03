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
        Project project = new ProjectService().readEntity(Project.class, HandlerNumeric.getLong(command[2]));
        if (project==null) {
            System.out.println("there is no project with id "+command[2]);
            return;
        }
        System.out.println("Project for update is:\n" + project);
        System.out.println("enter the new parameters of the project:\n" +
                "{name}|{cost}|{startDate}|{companyId}|{customerId}");
        String[] split = scanner.nextLine().split("\\|");
        while (split.length < 5) {
            System.out.println("Parameters is not enough. Enter correct number of parameters - 5");
            split = scanner.nextLine().split("\\|");
        }
        Long companyId = HandlerNumeric.getLong(split[3]);
        while (Optional.empty().equals(new CompanyService().findById(Company.class, companyId))) {
            System.out.println("No one company with id = " + companyId + "\nenter another id company:");
            companyId = scanner.nextLong();
        }
        Long customerId = HandlerNumeric.getLong(split[4]);
        while (Optional.empty().equals(new CustomerService().findById(Customer.class, customerId))) {
            System.out.println("No one customer with id = " + customerId + "\nenter another id customer:");
            customerId = scanner.nextLong();
        }
        Project updatedProject = Project.builder()
                .id(HandlerNumeric.getLong(command[2]))
                .name(split[0])
                .cost(HandlerNumeric.getInteger(split[1]))
                .startDate(split[2])
                .companyId(companyId)
                .customerId(customerId)
                .build();
        System.out.println("updated project: " + new ProjectService().updateEntity(Project.class, updatedProject));
    }

    @Override
    protected boolean isApplicable(String[] command) {
        return command.length == 3 && "update".equals(command[0]) & "project".equals(command[1]);
    }
}