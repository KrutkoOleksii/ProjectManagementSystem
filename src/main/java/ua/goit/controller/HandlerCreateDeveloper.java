package ua.goit.controller;

import ua.goit.model.Company;
import ua.goit.model.Developer;
import ua.goit.service.CompanyService;
import ua.goit.service.DeveloperService;

public class HandlerCreateDeveloper extends HandlerMenu{
    public HandlerCreateDeveloper(HandlerMenu handler) {
        super(handler);
    }

    @Override
    protected void apply(String[] command) {
        Developer newDeveloper = getNewDeveloper();
        Developer developer = new DeveloperService().createEntity(Developer.class, newDeveloper);
        System.out.println("new company: " + developer.toString());
    }

    @Override
    protected boolean isApplicable(String[] command) {
        return command.length == 2 && "create".equals(command[0]) & "developer".equals(command[1]);
    }

    protected Developer getNewDeveloper() {
        System.out.println("enter the parameters of the new developer:\n" +
                "{name}|{gender}|{age}|{salary}|{companyId}\n" +
                "( e.g.  Alex|male|35|2_000|3)");
        String next = scanner.next();
        String[] split = next.split("\\|");
        Company readEntity = new CompanyService().readEntity(Company.class, Long.valueOf(split[4]));
        if (readEntity == null) System.out.println("No one company with id = "+split[4]);
        return Developer.builder()
                .name(split[0])
                .gender(split[1])
                .age(Integer.valueOf(split[2]))
                .salary(Integer.valueOf(split[3]))
                .companyId(Long.valueOf(split[4]))
                .build();
    }
}