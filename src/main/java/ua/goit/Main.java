package ua.goit;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import ua.goit.model.BaseEntity;
import ua.goit.model.Company;
import ua.goit.repository.Factory;
import ua.goit.service.reports.*;
import ua.goit.util.ScriptExecutor;

import javax.persistence.Column;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class Main {
    private static final Scanner scanner = new Scanner(System.in);
//    private static final EntityService<Long, Company> companyService = new EntityServiceImpl<>(new CompanyRepository());
//    private static final EntityService<Long, Customer> customerService = new EntityServiceImpl<>(new CustomerRepository());
//    private static final EntityService<Long, Developer> developerService = new EntityServiceImpl<>(new DeveloperRepository());
//    private static final EntityService<Long, Project> projectService = new EntityServiceImpl<>(new ProjectRepository());
//    private static final EntityService<Long, Skill> skillService = new EntityServiceImpl<>(new SkillRepository());

    public static void main(String[] args) {

        ScriptExecutor.start();

        System.out.println("Hello. Follow steps below, please. For exit tape 'exit'");
        System.out.println("What do you want (input number)? :\n 1. Operations with tables\n 2. Reports");
        Integer response1 = scanner.nextInt();
        if (response1==1) doingOperationsWithTables();
        else if (response1==2) showReports();

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

    @SneakyThrows
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
            String className = Character.toUpperCase(responseArray[1].charAt(0)) + responseArray[1].substring(1);
            String operation = responseArray[0];
            Class aClass = Class.forName("ua.goit.model."+className);

            if        ("get".equals(operation)){
                BaseEntity baseEntity = Factory.of(aClass).getOne(id);
                System.out.println(baseEntity);
            } else if ("delete".equals(operation)) {
                Factory.of(aClass).deleteById(id);
            } else if ("create".equals(operation)) {
                Map<String,String> mapColumnField = Arrays.stream(aClass.getDeclaredFields())
                        .filter(modelField -> !Modifier.isStatic(modelField.getModifiers()))
                        .collect(Collectors.toMap(modelField -> getColumn(modelField), modelField -> modelField.getName()));
                BaseEntity entity = getEntity(mapColumnField, aClass);
                BaseEntity baseEntity = Factory.of(aClass).save(entity);
                System.out.println(baseEntity);
            } else if ("update".equals(operation)) {
                Map<String,String> mapColumnField = Arrays.stream(aClass.getDeclaredFields())
                        .filter(modelField -> !Modifier.isStatic(modelField.getModifiers()))
                        .collect(Collectors.toMap(modelField -> getColumn(modelField), modelField -> modelField.getName()));
                BaseEntity entity = getEntity(mapColumnField, aClass);
                BaseEntity baseEntity = Factory.of(aClass).save(entity);
                System.out.println(baseEntity);
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
            if ("1".equals(responseArray[0])){
                Report report = new SalaryOfProject();
                System.out.println(report.getReport(Long.parseLong(responseArray[1])));
            } else if ("2".equals(responseArray[0])) {
                Report report = new DevelopersOfProject();
                System.out.println(report.getReport(Long.parseLong(responseArray[1])));
            } else if ("3".equals(responseArray[0])) {
                Report report = new DevelopersWithSkill();
                System.out.println(report.getReport(responseArray[1]));
            } else if ("4".equals(responseArray[0])) {
                Report report = new DevelopersWithLevel();
                System.out.println(report.getReport(responseArray[1]));
            } else if ("5".equals(responseArray[0])) {
                Report report = new ListOfProjects();
                System.out.println(report.getReport(""));
            }
            response = scanner.next();
        }
    }

    private static String[] getArrayOfFields(String fields) {
        System.out.println(String.format("Well, please input parameters in comma separated string: %s",fields));
        String response = scanner.next();
        return response.split(",");
    }

    private static BaseEntity getEntity(Map<String,String> mapColumnField, Class modelClass){
        Map<String,String> mapEntity = new HashMap<>();
        for (Map.Entry<String,String> element : mapColumnField.entrySet()) {
            String key = element.getValue();
            System.out.println(String.format("input %s : ",key));
            String response = scanner.next();
            mapEntity.put(key,response);
        }
        ObjectMapper jacksonMapper = new ObjectMapper();
        BaseEntity convertValue = (BaseEntity) jacksonMapper.convertValue(mapEntity, modelClass);
        return convertValue;
    }

    private static String getColumn(Field modelField) {
        return modelField.getAnnotationsByType(Column.class) == null ? modelField.getName() : modelField.getAnnotation(Column.class).name();
    }
}
