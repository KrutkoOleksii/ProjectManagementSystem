package ua.goit.controller;

import java.util.Scanner;

public abstract class HandlerMenu {

    protected final Scanner scanner = new Scanner(System.in);
    HandlerMenu handler;

    public HandlerMenu(HandlerMenu handler) {
        this.handler = handler;
    }

    protected abstract void apply(String[] command);

    protected abstract boolean isApplicable(String[] command);

    public final void handle(String[] command) {
        if (isApplicable(command)) apply(command);
        else handler.handle(command);
    }

    public static HandlerMenu of() {
        return new HandlerReadCompany(new HandlerReadCustomer(new HandlerReadDeveloper(new HandlerReadProject(new HandlerReadSkill(
                new HandlerDeleteCompany(new HandlerDeleteCustomer(new HandlerDeleteDeveloper(new HandlerDeleteProject(new HandlerDeleteSkill(
                new HandlerCreateCompany(new HandlerCreateCustomer(new HandlerCreateDeveloper(new HandlerCreateProject(new HandlerCreateSkill(
                new HandlerUpdateCompany(new HandlerUpdateCustomer(new HandlerUpdateDeveloper(new HandlerUpdateProject(new HandlerUpdateSkill(
                new HandlerQueryDeveloperOfProject(new HandlerQueryDeveloperWithSkill(new HandlerQueryDeveloperWithLevel(
                new HandlerQueryListOfProjects(new HandlerQuerySalaryOfProject(
                new HandlerException()
                )))))
                )))))
                )))))
                )))))
        )))));
    }
}
