package ua.goit.controller;

import ua.goit.model.Project;
import ua.goit.service.ProjectService;

public class HandlerUpdateProject extends HandlerMenu{
    public HandlerUpdateProject(HandlerMenu handler) {
        super(handler);
    }

    @Override
    protected void apply(String[] command) {
        Project project = new ProjectService().readEntity(Project.class, Long.valueOf(command[2]));
        System.out.println("Project for update is:\n" + project.toString());
        System.out.println("enter the new parameters of the project:\n" +
                "{name}|{cost}|{startDate}|{companyId}|{customerId}");
        String next = scanner.next();
        String[] split = next.split("\\|");
        Project.builder()
                .id(Long.valueOf(command[2]))
                .name(split[0])
                .cost(Integer.valueOf(split[1]))
                .startDate(split[2])
                .companyId(Long.valueOf(split[3]))
                .customerId(Long.valueOf(split[4]))
                .build();
        Project updateProject = new ProjectService().updateEntity(Project.class, project);
        System.out.println("updated project: " + updateProject.toString());
    }

    @Override
    protected boolean isApplicable(String[] command) {
        return command.length == 3 && "update".equals(command[0]) & "project".equals(command[1]);
    }
}