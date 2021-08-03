package ua.goit.service.queries;

public class QueryRead {
    public String getQueryText(String table, String fields) {
        return String.format("SELECT %s FROM %s",fields,table);
    }
}
