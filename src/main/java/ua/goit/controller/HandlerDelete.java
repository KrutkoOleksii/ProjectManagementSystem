package ua.goit.controller;

public class HandlerDelete extends HandlerMenu{

    public HandlerDelete(HandlerMenu handler) {
        super(handler);
    }

    @Override
    protected void apply(String[] command) {
        //TODO delete entity
        // in service
    }

    @Override
    protected boolean isApplicable(String[] command) {
        return "delete".equals(command[0]);
    }
}
