package ua.goit.service;

import ua.goit.model.Developer;
import ua.goit.repository.DeveloperRepository;

public class DeveloperService extends BaseService<Long, Developer>{

    public String developersOfProject(Long id) {
        return new DeveloperRepository(Developer.class).developerOfProject(id);
    }
    public String developersWithLevel(String level) {
        return new DeveloperRepository(Developer.class).developerWithLevel(level);
    }
    public String developersWithSkill(String skill) {
        return new DeveloperRepository(Developer.class).developerWithSkill(skill);
    }
}
