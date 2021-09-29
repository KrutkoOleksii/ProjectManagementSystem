package ua.goit.controller;

import ua.goit.model.Company;
import ua.goit.service.CompanyService;

public class HandlerReadCompany extends HandlerMenu{

    public HandlerReadCompany(HandlerMenu handler) {
        super(handler);
    }

    @Override
    protected void apply(String[] command) {
        Company company = new CompanyService().readEntity(Company.class, HandlerNumeric.getLong(command[2]));
        System.out.println(company==null ? "there is no company with id "+ command[2] : company);
    }

    @Override
    protected boolean isApplicable(String[] command) {
        return command.length == 3 && "get".equals(command[0]) & "company".equals(command[1]);
    }
}
