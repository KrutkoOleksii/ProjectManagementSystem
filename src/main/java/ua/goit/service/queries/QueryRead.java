package ua.goit.service.queries;

public class QueryRead {
    public String getQueryText(String table, String fields) {
        return String.join("","SELECT ",fields," FROM ",table);//
    }
}
