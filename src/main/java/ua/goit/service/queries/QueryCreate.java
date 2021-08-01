package ua.goit.service.queries;

public class QueryCreate {
    //String queryCreate = "INSERT INTO <table> (<fields>) VALUES (?,?,?,?,?,?)";
    public String getQueryText(String table, String fields, int countFields) {
        return String.join("","INSERT INTO ",table," (",fields,") VALUES(","?,".repeat(countFields-1),"?)");//
    }
}
