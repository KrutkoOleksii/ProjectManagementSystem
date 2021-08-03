package ua.goit.service.queries;

public class QueryUpdate {
    //String queryUpdate = "UPDATE <table> SET <fieldsAndValues> WHERE <condition>";
    public String getQueryText(String table, String fieldsAndValues,String condition) {
        return String.format("UPDATE %s SET %s WHERE %s",table,fieldsAndValues,condition);//
    }
}
