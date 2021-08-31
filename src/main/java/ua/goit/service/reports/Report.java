package ua.goit.service.reports;

public interface Report<K>{
    String getReport(K k);
}
