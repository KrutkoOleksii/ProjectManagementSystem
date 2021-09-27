package ua.goit.controller;

import ua.goit.model.Customer;
import ua.goit.service.CustomerService;

public class HandlerDeleteCustomer extends HandlerMenu{

    public HandlerDeleteCustomer(HandlerMenu handler) {
        super(handler);
    }

    @Override
    protected void apply(String[] command) {
        Customer customer = new CustomerService().deleteEntity(Customer.class, Long.valueOf(command[2]));
        System.out.println("deleted customer: " + customer);
    }

    @Override
    protected boolean isApplicable(String[] command) {
        return command.length == 3 && "delete".equals(command[0]) & "customer".equals(command[1]);
    }
}
