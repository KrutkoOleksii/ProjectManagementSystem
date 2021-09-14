package ua.goit.controller;

public class HandlerUpdate extends HandlerMenu{
    public HandlerUpdate(HandlerMenu handler) {
        super(handler);
    }

    @Override
    protected void apply(String[] command) {
        //TODO update entity
        // in service
    }

    @Override
    protected boolean isApplicable(String[] command) {
        return "update".equals(command[0]);
    }
}
