package ua.goit.controller;

import ua.goit.model.Company;
import ua.goit.service.CompanyService;

public class HandlerUpdateCompany extends HandlerMenu{
    public HandlerUpdateCompany(HandlerMenu handler) {
        super(handler);
    }

    @Override
    protected void apply(String[] command) {
        Company company = new CompanyService().readEntity(Company.class, Long.valueOf(command[2]));
        System.out.println("Company for update is:\n" + company.toString());
        System.out.println("enter the new parameters of the company:\n" +
                "{name}|{code}");
        //String next = scanner.next();
        String[] split = scanner.next().split("\\|");
        Company updatedCompany = Company.builder()
                .id(Long.valueOf(command[2]))
                .name(split[0])
                .code(split[1])
                .build();
//        new CompanyService().updateEntity(Company.class, updatedCompany).toString();
        System.out.println("updated company: " + new CompanyService().updateEntity(Company.class, updatedCompany).toString());
    }

    @Override
    protected boolean isApplicable(String[] command) {
        return command.length == 3 && "update".equals(command[0]) & "company".equals(command[1]);
    }
}