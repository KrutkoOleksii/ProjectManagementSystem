package ua.goit.service.queries;

public class QueryDelete {
    //String queryDelete = "DELETE FROM <table> WHERE <condition>";
    public String getQueryText(String table, String condition) {
        return String.format("DELETE FROM %s WHERE %s",table,condition);
    }
}
