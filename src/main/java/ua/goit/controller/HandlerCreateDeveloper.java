package ua.goit.controller;

import ua.goit.model.Company;
import ua.goit.model.Developer;
import ua.goit.service.CompanyService;
import ua.goit.service.DeveloperService;

import java.util.Optional;

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
                "( e.g.  Alex|male|35|2000|3)");
        String next = scanner.next();
        String[] split = next.split("\\|");
        Long companyId = Long.valueOf(split[4]);
        while (Optional.empty().equals(new CompanyService().findById(Company.class, companyId))) {
            System.out.println("No one company with id = " + companyId + "\nenter another id company:");
            companyId = scanner.nextLong();
        }
        return Developer.builder()
                .name(split[0])
                .gender(split[1])
                .age(Integer.valueOf(split[2]))
                .salary(Integer.valueOf(split[3]))
                .companyId(companyId)
                .build();
    }
}