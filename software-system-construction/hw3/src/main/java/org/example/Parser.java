package org.example;

import dictionary.CommandDictionary;
import commands.CommandInput;

import java.io.Serializable;
import java.util.Scanner;

/*
Parser that interprets user input when typing a command
 */
public class Parser implements Serializable {

    private static final long serialVersionUID = 1L;

    private Scanner reader;
    private Game game;

    public Parser(Game game) {
        this.game = game;
        reader = new Scanner(System.in);
    }

    public CommandInput get() {
        System.out.print("> ");

        String prefix = reader.next();
        String argument = reader.nextLine().trim();

        return new CommandInput(game.dictionary.commandDictionary.lookup(prefix), argument);
    }
}
