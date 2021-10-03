package ua.goit.controller;

import ua.goit.model.Customer;
import ua.goit.service.CustomerService;

public class HandlerUpdateCustomer extends HandlerMenu{
    public HandlerUpdateCustomer(HandlerMenu handler) {
        super(handler);
    }

    @Override
    protected void apply(String[] command) {
        Customer customer = new CustomerService().readEntity(Customer.class, HandlerNumeric.getLong(command[2]));
        if (customer==null) {
            System.out.println("there is no customer with id "+command[2]);
            return;
        }
        System.out.println("Customer for update is:\n" + customer);
        System.out.println("enter the new parameters of the customer:\n" +
                "{name}|{code}");
        String[] split = scanner.nextLine().split("\\|");
        while (split.length < 2) {
            System.out.println("Parameters is not enough. Enter correct number of parameters - 2");
            split = scanner.nextLine().split("\\|");
        }
        Customer updatedCustomer = Customer.builder()
                .id(HandlerNumeric.getLong(command[2]))
                .name(split[0])
                .code(split[1])
                .build();
        System.out.println("updated customer: " + new CustomerService().updateEntity(Customer.class, updatedCustomer));
    }

    @Override
    protected boolean isApplicable(String[] command) {
        return command.length == 3 && "update".equals(command[0]) & "customer".equals(command[1]);
    }
}