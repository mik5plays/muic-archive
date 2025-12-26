import java.util.HashMap;

/**
 * This class is part of the "World of Zuul" application. 
 * "World of Zuul" is a very simple, text based adventure game.  
 * 
 * This class holds an enumeration of all command words known to the game.
 * It is used to recognise commands as they are typed in.
 *
 * @author  Michael Kölling and David J. Barnes
 * @version 2016.02.29
 */

public class CommandWords
{
//    // a constant array that holds all valid command words
//    private static final String[] validCommands = {
//        "go", "quit", "help"
//    };

    /**
     * Constructor - initialise the command words.
     */
    private HashMap<String, CommandWord> validCommands;

    public CommandWords()
    {
        validCommands = new HashMap<String, CommandWord>();
        validCommands.put("quit", CommandWord.QUIT);
        validCommands.put("help", CommandWord.HELP);
        validCommands.put("go", CommandWord.GO);
        validCommands.put("exit", CommandWord.QUIT); // Another valid instance of quit
        validCommands.put("look", CommandWord.LOOK);
        validCommands.put("whereami", CommandWord.LOOK);
        validCommands.put("back", CommandWord.BACK);
    }

    /**
     * Check whether a given String is a valid command word. 
     * @return true if a given string is a valid command,
     * false if it isn't.
     */
    public CommandWord isCommand(String aString) {
        CommandWord word = validCommands.get(aString);
        if (word != null) {
            return word;
        } else {
            return CommandWord.UNKNOWN;
        }
    }
}
