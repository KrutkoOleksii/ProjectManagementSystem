package ua.goit.controller;

import ua.goit.model.Customer;
import ua.goit.service.CustomerService;

public class HandlerUpdateCustomer extends HandlerMenu{
    public HandlerUpdateCustomer(HandlerMenu handler) {
        super(handler);
    }

    @Override
    protected void apply(String[] command) {
        Customer customer = new CustomerService().readEntity(Customer.class, Long.valueOf(command[2]));
        System.out.println("Customer for update is:\n" + customer.toString());
        System.out.println("enter the new parameters of the customer:\n" +
                "{name}|{code}");
        String next = scanner.next();
        String[] split = next.split("\\|");
        Customer updatedCustomer = Customer.builder()
                .id(Long.valueOf(command[2]))
                .name(split[0])
                .code(split[1])
                .build();
        System.out.println("updated customer: " + new CustomerService().updateEntity(Customer.class, updatedCustomer).toString());
    }

    @Override
    protected boolean isApplicable(String[] command) {
        return command.length == 3 && "update".equals(command[0]) & "customer".equals(command[1]);
    }
}