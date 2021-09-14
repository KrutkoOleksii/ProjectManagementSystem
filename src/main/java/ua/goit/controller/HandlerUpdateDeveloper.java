package ua.goit.controller;

import ua.goit.model.Developer;
import ua.goit.service.DeveloperService;

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
        Developer.builder()
                .id(Long.valueOf(command[2]))
                .name(split[0])
                .gender(split[1])
                .age(Integer.valueOf(split[2]))
                .salary(Integer.valueOf(split[3]))
                .companyId(Long.valueOf(split[4]))
                .build();
        Developer updateDeveloper = new DeveloperService().updateEntity(Developer.class, developer);
        System.out.println("updated developer: " + updateDeveloper.toString());
    }

    @Override
    protected boolean isApplicable(String[] command) {
        return command.length == 3 && "update".equals(command[0]) & "developer".equals(command[1]);
    }
}