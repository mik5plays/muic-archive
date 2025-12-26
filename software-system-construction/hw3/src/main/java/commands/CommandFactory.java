package commands;

import enums.CommandWords;

import java.util.*;

public class CommandFactory {

    private final Map<CommandWords, Command> commands = new HashMap<>();

    public CommandFactory() {
        // pre-game commands
        commands.put(CommandWords.PLAY, new PlayCommand());
        commands.put(CommandWords.LOAD, new LoadCommand());
        commands.put(CommandWords.SAVE, new SaveCommand());
        // during-game commands
        commands.put(CommandWords.INFO, new InfoCommand());
        commands.put(CommandWords.DROP, new DropCommand());
        commands.put(CommandWords.TAKE, new TakeCommand());
        commands.put(CommandWords.USE, new UseCommand());
        commands.put(CommandWords.ATTACK, new AttackCommand());
        commands.put(CommandWords.GO, new GoCommand());
        commands.put(CommandWords.HELP, new HelpCommand());
        commands.put(CommandWords.MAP, new MapCommand());
        commands.put(CommandWords.INVENTORY, new InventoryCommand());
        // to-leave-game commands
        commands.put(CommandWords.QUIT, new QuitCommand());
        commands.put(CommandWords.EXIT, new ExitCommand());
    }

    public Command get(CommandWords keyword) {
        return commands.get(keyword);
    }
}
