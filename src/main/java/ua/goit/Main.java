package ua.goit;

import ua.goit.controller.MenuConsoleController;

import java.util.Scanner;

public class Main {
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {

        //ScriptExecutor.start();

        //MenuConsoleController.printNainMenu();

        //создать заготовки операций(закомментированные query) для создания новых проектов, разработчиков, клиентов.
        //! Не забывать о правильных связях между таблиц !
        /*
        projectService.create(new Project(20,"Solar", 1_500_000, companyService.read(3), customerService.read(3),"2020-09-01"));
        projectService.create(new Project(21,"Integration", 800_000, companyService.read(1), customerService.read(2),"2020-01-15"));
        projectService.create(new Project(22,"Trade 21", 650_000, companyService.read(2), customerService.read(1),"2021-07-21"));
        */
        /*
        developerService.create(new Developer(20,"Piter",32,"M",2900, companyService.read(1)));
        developerService.create(new Developer(21,"Dmitry",33,"M",4000, companyService.read(2)));
        developerService.create(new Developer(22,"Anna",25,"F",2500, companyService.read(3)));
        */
        /*
        customerService.create(new Customer(20,"Katamaran","43243212"));
        customerService.create(new Customer(21,"Squalo","77446655"));
        customerService.create(new Customer(22,"Owoce i warzywa","19283746"));
        * */
    }

}
