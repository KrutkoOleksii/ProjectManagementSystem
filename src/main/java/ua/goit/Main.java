package ua.goit;

import ua.goit.model.*;
import ua.goit.repository.BaseRepository;
import ua.goit.repository.Factory;
import ua.goit.service.*;
import ua.goit.service.old.*;

import java.util.List;
import java.util.Scanner;

public class Main {
    private static final Scanner scanner = new Scanner(System.in);
    private static final EntityService<Long, Company> companyService = new EntityServiceImpl<>(new CompanyRepository());
    private static final EntityService<Long,Customer> customerService = new EntityServiceImpl<>(new CustomerRepository());
    private static final EntityService<Long,Developer> developerService = new EntityServiceImpl<>(new DeveloperRepository());
    private static final EntityService<Long,Project> projectService = new EntityServiceImpl<>(new ProjectRepository());
    private static final EntityService<Long,Skill> skillService = new EntityServiceImpl<>(new SkillRepository());

    public static void main(String[] args) {

        BaseRepository<Long, Company> companyRepository = Factory.of(Company.class);
        List<Company> companyList = companyRepository.findAll();
        System.out.println(companyList);

        companyRepository.close();


//        System.out.println("Hello. Follow steps below, please. For exit tape 'exit'");
//        System.out.println("What do you want (input number)? :\n 1. Operations with tables\n 2. Reports");
//        Integer response1 = scanner.nextInt();
//        if (response1==1) doingOperationsWithTables();
//        else if (response1==2) showReports();

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

    public static void doingOperationsWithTables() {
        String helpString =
                "print a formatted string for operations with tables (company, customer, developer, project, skill):\n" +
                        "    for CREATE:  create|{table}|{id}\n" +
                        "    for READ:    get|{table}|{id}\n" +
                        "    for UPDATE:  update|{table}|{id}\n" +
                        "    for DELETE:  delete|{table}|{id}\n" +
                        "    for help:    help\n" +
                        "    for exit:    exit";
        System.out.println(helpString);
        String response = scanner.next();
        while (!"exit".equals(response)){
            if ("help".equals(response)){
                System.out.println(helpString);
                response = scanner.next();
                continue;
            }
            String[] responseArray = response.split("\\|");
            if (responseArray.length < 3) {
                System.out.println("print correct string");
                response = scanner.next();
                continue;
            }
            Long id =  Long.parseLong(responseArray[2]);
//            String command = responseArray[0];
//            String table = responseArray[1];

            if        (response.contains("get|company")){
                System.out.println(companyService.read(id));
            } else if (response.contains("get|customer")){
                System.out.println(customerService.read(id));
            } else if (response.contains("get|developer")){
                System.out.println(developerService.read(id));
            } else if (response.contains("get|project")){
                System.out.println(projectService.read(id));
            } else if (response.contains("get|skill")) {
                System.out.println(skillService.read(id));

            } else if (response.contains("delete|company")) {
                companyService.delete(id);
            } else if (response.contains("delete|customer")) {
                customerService.delete(id);
            } else if (response.contains("delete|developer")) {
                developerService.delete(id);
            } else if (response.contains("delete|project")) {
                projectService.delete(id);
            } else if (response.contains("delete|skill")) {
                skillService.delete(id);

            } else if (response.contains("create|company")) {
                String[] values = getArrayOfFields("{id},{name},{code}");
                companyService.create(new Company(Long.parseLong(values[0]),values[1],values[2]));
            } else if (response.contains("create|customer")) {
                String[] values = getArrayOfFields("{id},{name},{code}");
                customerService.create(new Customer(Long.parseLong(values[0]),values[1],values[2]));
            } else if (response.contains("create|developer")) {
                String[] values = getArrayOfFields("{id},{name},{age},{sex},{salary},{companyId}");
                developerService.create(new Developer(Long.parseLong(values[0]),values[1],Integer.parseInt(values[2]),values[3],
                        Integer.parseInt(values[4]),companyService.read(Long.parseLong(values[5]))));
            } else if (response.contains("create|project")) {
                String[] values = getArrayOfFields("{id},{name},{cost},{companyId},{customerId},{startDate}");
                projectService.create(new Project(Long.parseLong(values[0]),values[1],Integer.parseInt(values[2]),
                        companyService.read(Long.parseLong(values[4])),customerService.read(Long.parseLong(values[5])),
                        values[6]));
            } else if (response.contains("create|skill")) {
                String[] values = getArrayOfFields("{id},{name},{level}");
                companyService.create(new Company(Long.parseLong(values[0]),values[1],values[2]));

            } else if (response.contains("update|company")) {
                String[] values = getArrayOfFields("{id},{name},{code}");
                companyService.update(Long.parseLong(values[0]),new Company(Long.parseLong(values[0]),values[1],values[2]));
            } else if (response.contains("update|customer")) {
                String[] values = getArrayOfFields("{id},{name},{code}");
                customerService.update(Long.parseLong(values[0]),new Customer(Long.parseLong(values[0]),values[1],values[2]));
            } else if (response.contains("update|developer")) {
                String[] values = getArrayOfFields("{id},{name},{age},{sex},{salary},{companyId}");
                developerService.update(Long.parseLong(values[0]),new Developer(Long.parseLong(values[0]),values[1],Integer.parseInt(values[2]),values[3],
                        Integer.parseInt(values[4]),companyService.read(Long.parseLong(values[5]))));
            } else if (response.contains("update|project")) {
                String[] values = getArrayOfFields("{id},{name},{cost},{companyId},{customerId},{startDate}");
                projectService.update(Long.parseLong(values[0]),new Project(Long.parseLong(values[0]),values[1],Integer.parseInt(values[2]),
                        companyService.read(Long.parseLong(values[4])),customerService.read(Long.parseLong(values[5])),
                        values[6]));
            } else if (response.contains("update|skill")) {
                String[] values = getArrayOfFields("{id},{name},{level}");
                companyService.update(Long.parseLong(values[0]),new Company(Long.parseLong(values[0]),values[1],values[2]));
            }

            response = scanner.next();
        }
    }

    public static void showReports() {
        String helpString =
                "print a formatted string for showing report:\n" +
                        "    for 'Salary Of Project':     1|{projectId}\n" +
                        "    for 'Developers Of Project': 2|{projectId}\n" +
                        "    for 'Developers By Skill':   3|{skill}\n" +
                        "    for 'Developers By Level':   4|{level}\n" +
                        "    for 'Project List':          5\n" +
                        "    for help:    help\n" +
                        "    for exit:    exit";
        System.out.println(helpString);
        String response = scanner.next();
        while (!"exit".equals(response)) {
            if ("help".equals(response)) {
                System.out.println(helpString);
                response = scanner.next();
                continue;
            }
            String[] responseArray = response.split("\\|");
            if (responseArray.length < 2 & "1234".contains(responseArray[0])) {
                System.out.println("print correct string");
                response = scanner.next();
                continue;
            }
            Reporter reporter = new Reporter();
            if ("1".equals(responseArray[0])){
                reporter.printReportSalaryOfProject(Long.parseLong(responseArray[1]));
            } else if ("2".equals(responseArray[0])) {
                reporter.printReportDevelopersOfProject(Long.parseLong(responseArray[1]));
            } else if ("3".equals(responseArray[0])) {
                reporter.printReportDevelopersBySkill(responseArray[1]);
            } else if ("4".equals(responseArray[0])) {
                reporter.printReportDevelopersByLevel(responseArray[1]);
            } else if ("5".equals(responseArray[0])) {
                reporter.printReportProjectList();
            }
            response = scanner.next();
        }
    }

    private static String[] getArrayOfFields(String fields) {
        System.out.println(String.format("Well, please input comma separated string: %s",fields));
        String response = scanner.next();
        return response.split(",");
    }
}
