package ua.goit.controller;

import ua.goit.model.Company;
import ua.goit.service.CompanyService;

public class HandlerDeleteCompany extends HandlerMenu{

    public HandlerDeleteCompany(HandlerMenu handler) {
        super(handler);
    }

    @Override
    protected void apply(String[] command) {
        Company company = new CompanyService().deleteEntity(Company.class, HandlerNumeric.getLong(command[2]));
        System.out.println(company==null ? "there is no company with id "+ command[2] : "deleted company: " + company);
    }

    @Override
    protected boolean isApplicable(String[] command) {
        return command.length == 3 && "delete".equals(command[0]) & "company".equals(command[1]);
    }
}
