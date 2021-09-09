package ua.goit.repository;

import ua.goit.model.Skill;

public class SkillRepository extends BaseRepositoryImpl<Long, Skill>{
    public SkillRepository(Class<Skill> modelClass) {
        super(modelClass);
    }
}
