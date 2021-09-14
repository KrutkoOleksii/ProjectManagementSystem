package ua.goit.controller;

import ua.goit.model.Customer;
import ua.goit.service.CustomerService;

public class HandlerReadCustomer extends HandlerMenu{
    public HandlerReadCustomer(HandlerMenu handler) {
        super(handler);
    }


    @Override
    protected void apply(String[] command) {
        Customer customer = new CustomerService().readEntity(Customer.class, Long.getLong(command[2]));
        System.out.println(customer.toString());
    }

    @Override
    protected boolean isApplicable(String[] command) {
        return command.length == 3 && "get".equals(command[0]) & "customer".equals(command[1]);
    }
}
