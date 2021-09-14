package ua.goit.controller;

public class HandlerExit extends HandlerMenu{
    public HandlerExit(HandlerMenu handler) {
        super(handler);
    }

    @Override
    protected void apply(String[] command) {
        //TODO  exit
    }

    @Override
    protected boolean isApplicable(String[] command) {
        return "exit".equals(command[0]);
    }
}
