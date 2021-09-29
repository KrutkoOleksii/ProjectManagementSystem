package ua.goit;

import ua.goit.controller.MenuConsoleController;
import ua.goit.util.ScriptExecutor;

import java.util.Scanner;

public class Main {
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        // use for database initialization
        //ScriptExecutor.start();
        MenuConsoleController.printNainMenu();
    }
}
