package ua.goit.controller;

import ua.goit.model.Customer;
import ua.goit.service.CustomerService;

public class HandlerCreateCustomer extends HandlerMenu{
    public HandlerCreateCustomer(HandlerMenu handler) {
        super(handler);
    }

    @Override
    protected void apply(String[] command) {
        Customer newCustomer = getNewCustomer();
        Customer customer = new CustomerService().createEntity(Customer.class, newCustomer);
        System.out.println("new company: " + customer.toString());
    }

    @Override
    protected boolean isApplicable(String[] command) {
        return command.length == 2 && "create".equals(command[0]) & "customer".equals(command[1]);
    }

    protected Customer getNewCustomer() {
        System.out.println("enter the parameters of the new customer:\n" +
                "{name}|{code}\n" +
                "( e.g.  FinTech|12345678");
        String[] split = scanner.next().split("\\|");
        while (split.length < 2) {
            System.out.println("Parameters is not enough. Enter correct number of parameters - 2");
            split = scanner.next().split("\\|");
        }
        return Customer.builder()
                .name(split[0])
                .code(split[1])
                .build();
    }
}