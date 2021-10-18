package ua.goit.controller;

import java.util.Scanner;

public class MenuConsoleController {

    private static final Scanner scanner = new Scanner(System.in);

    public static void printNainMenu() {
        String helpString =
                "print a formatted string for operations with tables (company, customer, developer, project, skill):\n" +
                        "    for CREATE:  create|{table}\n" +
                        "    for READ:    get|{table}|{id}\n" +
                        "    for UPDATE:  update|{table}|{id}\n" +
                        "    for DELETE:  delete|{table}|{id}\n" +
                "print a formatted string for showing report:\n" +
                        "    for 'Salary Of Project':      query|SalaryOfProject|{projectId}\n" +
                        "    for 'Developers Of Project':  query|DevelopersOfProject|{projectId}\n" +
                        "    for 'Developers With Skill':  query|DevelopersWithSkill|{skill}\n" +
                        "    for 'Developers With Level':  query|DevelopersWithLevel|{level}\n" +
                        "    for 'Project List':           query|ListOfProject\n" +
                "for HELP:    help\n" +
                "for EXIT:    exit";
        System.out.println(helpString);
        String response = scanner.next();
        HandlerMenu handlerMenu = HandlerMenu.of();
        while (!"exit".equals(response)) {
            if ("help".equalsIgnoreCase(response)){
                System.out.println(helpString);
                response = scanner.nextLine();
                continue;
            }
            String[] command = response.split("\\|");
            handlerMenu.handle(command);
            response = scanner.next();
        }
    }
}
