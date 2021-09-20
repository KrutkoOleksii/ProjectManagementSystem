package ua.goit.controller;

import ua.goit.model.Company;
import ua.goit.model.Developer;
import ua.goit.service.CompanyService;
import ua.goit.service.DeveloperService;

import java.util.Optional;

public class HandlerUpdateDeveloper extends HandlerMenu{
    public HandlerUpdateDeveloper(HandlerMenu handler) {
        super(handler);
    }

    @Override
    protected void apply(String[] command) {
        Developer developer = new DeveloperService().readEntity(Developer.class, Long.valueOf(command[2]));
        System.out.println("Developer for update is:\n" + developer.toString());
        System.out.println("enter the new parameters of the developer:\n" +
                "{name}|{gender}|{age}|{salary}|{companyId}");
        String next = scanner.next();
        String[] split = next.split("\\|");
        Long companyId = Long.valueOf(split[4]);
        while (Optional.empty().equals(new CompanyService().findById(Company.class, companyId))) {
            System.out.println("No one company with id = " + companyId + "\nenter another id company:");
            companyId = scanner.nextLong();
        }
        Developer.builder()
                .id(Long.valueOf(command[2]))
                .name(split[0])
                .gender(split[1])
                .age(Integer.valueOf(split[2]))
                .salary(Integer.valueOf(split[3]))
                .companyId(companyId)
                .build();
        Developer updateDeveloper = new DeveloperService().updateEntity(Developer.class, developer);
        System.out.println("updated developer: " + updateDeveloper.toString());
    }

    @Override
    protected boolean isApplicable(String[] command) {
        return command.length == 3 && "update".equals(command[0]) & "developer".equals(command[1]);
    }
}