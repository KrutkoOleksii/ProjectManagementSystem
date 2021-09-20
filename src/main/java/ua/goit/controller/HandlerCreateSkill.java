package ua.goit.controller;

import ua.goit.model.Skill;
import ua.goit.service.SkillService;

public class HandlerCreateSkill extends HandlerMenu{
    public HandlerCreateSkill(HandlerMenu handler) {
        super(handler);
    }

    @Override
    protected void apply(String[] command) {
        Skill newSkill = getNewSkill();
        Skill skill = new SkillService().createEntity(Skill.class, newSkill);
        System.out.println("new skill: " + skill.toString());
    }

    @Override
    protected boolean isApplicable(String[] command) {
        return command.length == 2 && "create".equals(command[0]) & "skill".equals(command[1]);
    }

    protected Skill getNewSkill() {
        System.out.println("enter the parameters of the new skill:\n" +
                "{name}|{level}\n" +
                "( e.g.  Pascal|Middle");
        String next = scanner.next();
        String[] split = next.split("\\|");
        return Skill.builder()
                .name(split[0])
                .skillLevel(split[1])
                .build();
    }
}