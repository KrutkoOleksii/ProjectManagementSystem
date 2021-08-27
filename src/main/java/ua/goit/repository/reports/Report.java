package ua.goit.repository.reports;

public interface Report<K>{
    String getReport(K k);
}
