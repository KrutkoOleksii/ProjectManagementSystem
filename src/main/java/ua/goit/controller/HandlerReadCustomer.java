package ua.goit.controller;

import ua.goit.model.Customer;
import ua.goit.service.CustomerService;

public class HandlerReadCustomer extends HandlerMenu{
    public HandlerReadCustomer(HandlerMenu handler) {
        super(handler);
    }


    @Override
    protected void apply(String[] command) {
        Customer customer = new CustomerService().readEntity(Customer.class, HandlerNumeric.getLong(command[2]));
        System.out.println(customer==null ? "there is no customer with id "+ command[2] : customer);
    }

    @Override
    protected boolean isApplicable(String[] command) {
        return command.length == 3 && "get".equals(command[0]) & "customer".equals(command[1]);
    }
}
