package ua.goit.controller;

import ua.goit.model.Company;
import ua.goit.service.CompanyService;

public class HandlerDeleteCompany extends HandlerMenu{

    public HandlerDeleteCompany(HandlerMenu handler) {
        super(handler);
    }

    @Override
    protected void apply(String[] command) {
        new CompanyService().deleteEntity(Company.class, Long.getLong(command[2]));
    }

    @Override
    protected boolean isApplicable(String[] command) {
        return "delete".equals(command[0]) & "company".equals(command[2]);
    }
}
