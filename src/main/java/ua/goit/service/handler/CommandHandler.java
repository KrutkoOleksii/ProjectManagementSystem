package ua.goit.service.handler;

public abstract class CommandHandler {
    protected final CommandHandler handler;

    public CommandHandler(CommandHandler handler) {
        this.handler = handler;
    }

    protected abstract void apply(String string);

//    public final void handle(String string) {
//        if (true) apply();
//        else handler.handle();
//    }
}

