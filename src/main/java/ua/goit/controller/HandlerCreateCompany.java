package ua.goit.controller;

import ua.goit.model.Company;
import ua.goit.service.CompanyService;

public class HandlerCreateCompany extends HandlerMenu{
    public HandlerCreateCompany(HandlerMenu handler) {
        super(handler);
    }

    @Override
    protected void apply(String[] command) {
        Company newCompany = getNewCompany();
        Company company = new CompanyService().createEntity(Company.class, newCompany);
        System.out.println("new company: " + company.toString());
    }

    @Override
    protected boolean isApplicable(String[] command) {
        return command.length == 2 && "create".equals(command[0]) & "company".equals(command[1]);
    }

    private Company getNewCompany() {
        System.out.println("enter the parameters of the new company:\n" +
                "{name}|{code}\n" +
                "( e.g.  FinTech|12345678");
        String next = scanner.next();
        String[] split = next.split("\\|");
        return Company.builder()
                .name(split[0])
                .code(split[1])
                .build();
    }
}