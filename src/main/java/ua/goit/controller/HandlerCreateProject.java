package ua.goit.controller;

import ua.goit.model.Project;
import ua.goit.service.ProjectService;

public class HandlerCreateProject extends HandlerMenu{
    public HandlerCreateProject(HandlerMenu handler) {
        super(handler);
    }

    @Override
    protected void apply(String[] command) {
        Project newProject = getNewProject();
        Project project = new ProjectService().createEntity(Project.class, newProject);
        System.out.println("new project: " + project.toString());
    }

    @Override
    protected boolean isApplicable(String[] command) {
        return command.length == 2 && "create".equals(command[0]) & "company".equals(command[1]);
    }

    protected Project getNewProject() {
        System.out.println("enter the parameters of the new project:\n" +
                "{name}|{cost}|{startDate}|{companyId}|{customerId}\n" +
                "( e.g. Alpha|1_000_000|2021-09-09|3|1)");
        String next = scanner.next();
        String[] split = next.split("\\|");
        return Project.builder()
                .name(split[0])
                .cost(Integer.valueOf(split[1]))
                .startDate(split[2])
                .companyId(Long.valueOf(split[3]))
                .companyId(Long.valueOf(split[4]))
                .build();
    }
}