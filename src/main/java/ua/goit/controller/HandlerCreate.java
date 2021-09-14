package ua.goit.controller;

public class HandlerCreate extends HandlerMenu{
    public HandlerCreate(HandlerMenu handler) {
        super(handler);
    }

    @Override
    protected void apply(String[] command) {
        //TODO create entity
        // in service
    }

    @Override
    protected boolean isApplicable(String[] command) {
        return "create".equals(command[0]);
    }
}
