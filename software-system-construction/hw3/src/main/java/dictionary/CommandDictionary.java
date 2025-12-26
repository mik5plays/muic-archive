package dictionary;

import enums.CommandWords;

import java.util.*;
/*
Class used to recognize command words
 */
public class CommandDictionary implements Dictionary<CommandWords> {

    private Map<String, CommandWords> validCommands;

    public CommandDictionary() {
        validCommands = new HashMap<>();

        validCommands.put("play", CommandWords.PLAY);
        validCommands.put("load", CommandWords.LOAD);
        validCommands.put("save", CommandWords.SAVE);
        validCommands.put("exit", CommandWords.EXIT);
        validCommands.put("info", CommandWords.INFO);
        validCommands.put("take", CommandWords.TAKE);
        validCommands.put("use", CommandWords.USE);
        validCommands.put("drop", CommandWords.DROP);
        validCommands.put("attackwith", CommandWords.ATTACK);
        validCommands.put("attack", CommandWords.ATTACK);
        validCommands.put("inventory", CommandWords.INVENTORY);
        validCommands.put("inv", CommandWords.INVENTORY);
        validCommands.put("go", CommandWords.GO);
        validCommands.put("map", CommandWords.MAP);
        validCommands.put("help", CommandWords.HELP);
        validCommands.put("quit", CommandWords.QUIT);
    }

    public CommandWords lookup(String command) {
        CommandWords word = validCommands.get(command);
        return Objects.requireNonNullElse(word, CommandWords.UNKNOWN);
    }
}
