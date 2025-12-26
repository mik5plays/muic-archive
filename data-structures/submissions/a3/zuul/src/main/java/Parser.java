import java.util.HashMap;
import java.util.Scanner;

/**
 * This class is part of the "World of Zuul" application. 
 * "World of Zuul" is a very simple, text based adventure game.  
 * 
 * This parser reads user input and tries to interpret it as an "Adventure"
 * command. Every time it is called it reads a line from the terminal and
 * tries to interpret the line as a two word command. It returns the command
 * as an object of class Command.
 *
 * The parser has a set of known command words. It checks user input against
 * the known commands, and if the input is not one of the known commands, it
 * returns a command object that is marked as an unknown command.
 * 
 * @author  Michael Kölling and David J. Barnes
 * @version 2016.02.29
 */
public class Parser 
{
    private CommandWords commands;  // holds all valid command words
    private Scanner reader;         // source of command input
    private static HashMap<String, Direction> validDirections; // holds all valid directions

    /**
     * Create a parser to read from the terminal window.
     */
    public Parser() 
    {
        commands = new CommandWords();
        reader = new Scanner(System.in);

        validDirections = new HashMap<String, Direction>();
        validDirections.put("north", Direction.NORTH);
        validDirections.put("south", Direction.SOUTH);
        validDirections.put("east", Direction.EAST);
        validDirections.put("west", Direction.WEST);
        validDirections.put("up", Direction.UP);
        validDirections.put("down", Direction.DOWN);
        validDirections.put("n", Direction.NORTH);
        validDirections.put("s", Direction.SOUTH);
        validDirections.put("e", Direction.EAST);
        validDirections.put("w", Direction.WEST);
        validDirections.put("u", Direction.UP);
        validDirections.put("d", Direction.DOWN);

    }

    /**
     * Helper function to determine the direction from some input, say user
     * @return A direction enum
     */

    public static Direction getDirection(String aString) {
        Direction word = validDirections.get(aString);
        if (word != null) {
            return word;
        } else {
            return null;
        }
    }
    /**
     * @return The HashMap containing valid directions
     */
    public HashMap<String, Direction> getValidDirections() {
        return validDirections;
    }
    /**
     * @return The next command from the user.
     */
    public Command getCommand() 
    {
        String inputLine;   // will hold the full input line
        String word1 = null;
        String word2 = null;

        System.out.print("> ");     // print prompt

        inputLine = reader.nextLine();

        // Find up to two words on the line.
        Scanner tokenizer = new Scanner(inputLine);
        if(tokenizer.hasNext()) {
            word1 = tokenizer.next();      // get first word
            if(tokenizer.hasNext()) {
                word2 = tokenizer.next();      // get second word
                // note: we just ignore the rest of the input line.
            }
        }

        // Now check whether this word is known. If so, create a command
        // with it. If not, create a "null" command (for unknown command).

        if(commands.isCommand(word1) != CommandWord.UNKNOWN) {
            return new Command(commands.isCommand(word1), word2);
        }
        else {
            return new Command(CommandWord.UNKNOWN, word2);
        }
    }
}
