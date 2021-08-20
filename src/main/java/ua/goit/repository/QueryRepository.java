package ua.goit.repository;

import ua.goit.model.BaseEntity;

import java.util.List;

public interface QueryRepository <ID, T extends BaseEntity<ID>>{

    Long getSalaryOfProject(Long id);

    List<T> getDevelopersOfProject(Long id);

    List<T> getDevelopersBySkill(String skill);

    List<T> getDevelopersByLevel(String level);

    List<T> getProjectList();
}
