package ua.goit.controller;

import ua.goit.model.Company;
import ua.goit.service.CompanyService;

public class HandlerUpdateCompany extends HandlerMenu{
    public HandlerUpdateCompany(HandlerMenu handler) {
        super(handler);
    }

    @Override
    protected void apply(String[] command) {
        Company company = new CompanyService().readEntity(Company.class, HandlerNumeric.getLong(command[2]));
        if (company==null) {
            System.out.println("there is no company with id "+command[2]);
            return;
        }
        System.out.println("Company for update is:\n" + company);
        System.out.println("enter the new parameters of the company:\n" +
                "{name}|{code}");
        String[] split = scanner.nextLine().split("\\|");
        while (split.length < 2) {
            System.out.println("Parameters is not enough. Enter correct number of parameters - 2");
            split = scanner.nextLine().split("\\|");
        }
        Company updatedCompany = Company.builder()
                .id(HandlerNumeric.getLong(command[2]))
                .name(split[0])
                .code(split[1])
                .build();
        System.out.println("updated company: " + new CompanyService().updateEntity(Company.class, updatedCompany));
    }

    @Override
    protected boolean isApplicable(String[] command) {
        return command.length == 3 && "update".equals(command[0]) & "company".equals(command[1]);
    }
}