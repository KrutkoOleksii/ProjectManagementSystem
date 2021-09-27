package ua.goit.controller;

public class HandlerNumeric {

    protected static Long getLong(String string) {
        try {
            return Long.valueOf(string);
        }catch (NumberFormatException e) {
            return 0L;
        }
    }

    protected static Integer getInteger(String string) {
        try {
            return Integer.valueOf(string);
        }catch (NumberFormatException e) {
            return 0;
        }
    }
}
