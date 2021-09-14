package ua.goit.controller;

import ua.goit.model.Customer;
import ua.goit.service.CustomerService;

public class HandlerDeleteCustomer extends HandlerMenu{

    public HandlerDeleteCustomer(HandlerMenu handler) {
        super(handler);
    }

    @Override
    protected void apply(String[] command) {
        new CustomerService().deleteEntity(Customer.class, Long.getLong(command[2]));
    }

    @Override
    protected boolean isApplicable(String[] command) {
        return "delete".equals(command[0]) & "customer".equals(command[2]);
    }
}
