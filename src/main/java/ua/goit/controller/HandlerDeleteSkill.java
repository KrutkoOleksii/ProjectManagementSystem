package ua.goit.controller;

import ua.goit.model.Skill;
import ua.goit.service.SkillService;

public class HandlerDeleteSkill extends HandlerMenu{

    public HandlerDeleteSkill(HandlerMenu handler) {
        super(handler);
    }

    @Override
    protected void apply(String[] command) {
        Skill skill = new SkillService().deleteEntity(Skill.class, Long.valueOf(command[2]));
        System.out.println("deleted skill: " + skill);
    }

    @Override
    protected boolean isApplicable(String[] command) {
        return command.length == 3 && "delete".equals(command[0]) & "skill".equals(command[1]);
    }
}
