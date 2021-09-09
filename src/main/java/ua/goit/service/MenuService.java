package ua.goit.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import ua.goit.model.BaseEntity;
import ua.goit.repository.Factory;
import ua.goit.service.reports.*;

import javax.persistence.Column;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.stream.Collectors;

public class MenuService {

    private static final Scanner scanner = new Scanner(System.in);

    public static void printNainMenu() {
        System.out.println("Hello. Follow steps below, please. For exit tape 'exit'");
        String helpString = "Choose operation (input number) :\n 1. Operations with tables\n 2. Reports";
        System.out.println(helpString);
        String response = scanner.next();
        while       (!"exit".equals(response)) {
            if      ("1".equals(response)) {
                doingOperationsWithTables();
                break;
            }
            else if ("2".equals(response)) {
                showReports();
                break;
            }
            else response = scanner.next();
        }
    }

    @SneakyThrows
    public static void doingOperationsWithTables() {

        String helpString =
                "print a formatted string for operations with tables (company, customer, developer, project, skill):\n" +
                        "    for CREATE:  create|{table}\n" +
                        "    for READ:    get|{table}|{id}\n" +
                        "    for UPDATE:  update|{table}\n" +
                        "    for DELETE:  delete|{table}|{id}\n" +
                        "    for help:    help\n" +
                        "    for menu:    mainmenu\n" +
                        "    for exit:    exit";
        System.out.println(helpString);
        String response = scanner.next();

        while (!"exit".equals(response)){
            if ("help".equals(response)){
                System.out.println(helpString);
                response = scanner.next();
                continue;
            } else if ("mainmenu".equals(response)){
                printNainMenu();
                break;
            }
            String[] responseArray = response.split("\\|");
            if (responseArray.length < 2) {
                System.out.println("print correct string");
                response = scanner.next();
                continue;
            }
            String operation = responseArray[0];
            String className = Character.toUpperCase(responseArray[1].charAt(0)) + responseArray[1].substring(1);
            Class aClass = Class.forName("ua.goit.model."+className);
            Long id = 0L;
            if (("get".equals(operation) || "delete".equals(operation)) & responseArray.length < 3){
                System.out.println("print correct string (with ID in third position)");
                response = scanner.next();
                continue;
            } else if("get".equals(operation) || "delete".equals(operation)) id = Long.parseLong(responseArray[2]);

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
        return;
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
                        "    for menu:    mainmenu\n" +
                        "    for exit:    exit";
        System.out.println(helpString);
        String response = scanner.next();
        while (!"exit".equals(response)) {
            if ("help".equals(response)) {
                System.out.println(helpString);
                response = scanner.next();
                continue;
            } else if ("mainmenu".equals(response)){
                printNainMenu();
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
