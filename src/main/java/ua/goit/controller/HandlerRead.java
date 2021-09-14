package ua.goit.controller;

public class HandlerRead extends HandlerMenu{
    public HandlerRead(HandlerMenu handler) {
        super(handler);
    }

    @Override
    protected void apply(String[] command) {
        //TODO read entity
        // in service
    }

    @Override
    protected boolean isApplicable(String[] command) {
        return "read".equals(command[0]);
    }
}
