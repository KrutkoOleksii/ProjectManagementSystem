package ua.goit.service.queries;

public class QueryCreate {
    //String queryCreate = "INSERT INTO <table> (<fields>) VALUES (?,?,?,?,?,?)";
    public String getQueryText(String table, String fields, int countFields) {
        return String.format("INSERT INTO %s (%s) VALUES(%s?)", table, fields, "?,".repeat(countFields-1));
    }
}
