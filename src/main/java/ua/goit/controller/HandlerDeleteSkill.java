package ua.goit.controller;

import ua.goit.model.Skill;
import ua.goit.service.SkillService;

public class HandlerDeleteSkill extends HandlerMenu{

    public HandlerDeleteSkill(HandlerMenu handler) {
        super(handler);
    }

    @Override
    protected void apply(String[] command) {
        Skill skill = new SkillService().deleteEntity(Skill.class, HandlerNumeric.getLong(command[2]));
        System.out.println(skill==null ? "there is no skill with id "+ command[2] : "deleted skill: " + skill);
    }

    @Override
    protected boolean isApplicable(String[] command) {
        return command.length == 3 && "delete".equals(command[0]) & "skill".equals(command[1]);
    }
}
