package commands;

import enums.CommandWords;

/*
Stores the query given by the player, for use in Parser and Game.
 */
public class CommandInput {
    private CommandWords command;
    private String arg;

    public CommandInput(CommandWords command, String arg) {
        this.command = command;
        this.arg = arg;
    }

    public CommandWords getCommand() { return command; }
    public String getArg() { return arg; }
}
