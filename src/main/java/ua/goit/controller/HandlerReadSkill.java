package ua.goit.controller;

import ua.goit.model.Company;
import ua.goit.model.Skill;
import ua.goit.service.CompanyService;
import ua.goit.service.SkillService;

public class HandlerReadSkill extends HandlerMenu{

    public HandlerReadSkill(HandlerMenu handler) {
        super(handler);
    }

    @Override
    protected void apply(String[] command) {
        Skill skill = new SkillService().readEntity(Skill.class, Long.getLong(command[2]));
        System.out.println(skill.toString());
    }

    @Override
    protected boolean isApplicable(String[] command) {
        return command.length == 3 && "get".equals(command[0]) & "skill".equals(command[1]);
    }
}
