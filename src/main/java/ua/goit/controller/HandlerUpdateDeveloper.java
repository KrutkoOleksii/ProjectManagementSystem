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
        Developer developer = new DeveloperService().readEntity(Developer.class, HandlerNumeric.getLong(command[2]));
        if (developer==null) {
            System.out.println("there is no developer with id "+command[2]);
            return;
        }
        System.out.println("Developer for update is:\n" + developer);
        System.out.println("enter the new parameters of the developer:\n" +
                "{name}|{gender}|{age}|{salary}|{companyId}");
        String[] split = scanner.nextLine().split("\\|");
        while (split.length < 5) {
            System.out.println("Parameters is not enough. Enter correct number of parameters - 5");
            split = scanner.nextLine().split("\\|");
        }
        Long companyId = HandlerNumeric.getLong(split[4]);
        while (Optional.empty().equals(new CompanyService().findById(Company.class, companyId))) {
            System.out.println("No one company with id = " + companyId + "\nenter another id company:");
            companyId = scanner.nextLong();
        }
        Developer updatedDeveloper = Developer.builder()
                .id(HandlerNumeric.getLong(command[2]))
                .name(split[0])
                .gender(split[1])
                .age(HandlerNumeric.getInteger(split[2]))
                .salary(HandlerNumeric.getInteger(split[3]))
                .companyId(companyId)
                .build();
        System.out.println("updated developer: " + new DeveloperService().updateEntity(Developer.class, updatedDeveloper));
    }

    @Override
    protected boolean isApplicable(String[] command) {
        return command.length == 3 && "update".equals(command[0]) & "developer".equals(command[1]);
    }
}