package ua.goit.controller;

import ua.goit.model.Skill;
import ua.goit.service.SkillService;

public class HandlerUpdateSkill extends HandlerMenu{
    public HandlerUpdateSkill(HandlerMenu handler) {
        super(handler);
    }

    @Override
    protected void apply(String[] command) {
        Skill skill = new SkillService().readEntity(Skill.class, HandlerNumeric.getLong(command[2]));
        if (skill==null) {
            System.out.println("there is no skill with id "+command[2]);
            return;
        }
        System.out.println("Skill for update is:\n" + skill);
        System.out.println("enter the new parameters of the skill:\n" +
                "{name}|{level}");
        String[] split = scanner.next().split("\\|");
        while (split.length < 2) {
            System.out.println("Parameters is not enough. Enter correct number of parameters - 2");
            split = scanner.next().split("\\|");
        }
        Skill updatedSkill = Skill.builder()
                .id(HandlerNumeric.getLong(command[2]))
                .name(split[0])
                .skillLevel(split[1])
                .build();
        System.out.println("updated skill: " + new SkillService().updateEntity(Skill.class, updatedSkill));
    }

    @Override
    protected boolean isApplicable(String[] command) {
        return command.length == 3 && "update".equals(command[0]) & "skill".equals(command[1]);
    }
}