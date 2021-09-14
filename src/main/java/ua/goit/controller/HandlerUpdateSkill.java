package ua.goit.controller;

import ua.goit.model.Skill;
import ua.goit.service.SkillService;

public class HandlerUpdateSkill extends HandlerMenu{
    public HandlerUpdateSkill(HandlerMenu handler) {
        super(handler);
    }

    @Override
    protected void apply(String[] command) {
        Skill skill = new SkillService().readEntity(Skill.class, Long.valueOf(command[2]));
        System.out.println("Skill for update is:\n" + skill.toString());
        System.out.println("enter the new parameters of the skill:\n" +
                "{name}|{level}");
        String next = scanner.next();
        String[] split = next.split("\\|");
        Skill.builder()
                .id(Long.valueOf(command[2]))
                .name(split[0])
                .skillLevel(split[1])
                .build();
        Skill updateSkill = new SkillService().updateEntity(Skill.class, skill);
        System.out.println("updated skill: " + updateSkill.toString());
    }

    @Override
    protected boolean isApplicable(String[] command) {
        return command.length == 3 && "update".equals(command[0]) & "skill".equals(command[1]);
    }
}