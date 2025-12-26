package commands;

import enums.CommandWords;
import org.example.Game;
import org.example.Player;

public class HelpCommand implements Command{

    public final String description = "Displays information about a command, or all commands if left blank. Usage: help <command>";

    @Override
    public String description() { return description; }

    @Override
    public void execute(Game game, Player player, String arg) {
        if (arg.isBlank()) {
            System.out.println("=== Core Commands ===");
            System.out.printf("%-20s %s\n", "play", game.remote.factory.get(CommandWords.PLAY).description());
            System.out.printf("%-20s %s\n", "load", game.remote.factory.get(CommandWords.LOAD).description());
            System.out.printf("%-20s %s\n", "save", game.remote.factory.get(CommandWords.SAVE).description());
            System.out.println("= Gameplay Commands =");
            System.out.printf("%-20s %s\n", "info", game.remote.factory.get(CommandWords.INFO).description());
            System.out.printf("%-20s %s\n", "drop", game.remote.factory.get(CommandWords.DROP).description());
            System.out.printf("%-20s %s\n", "take", game.remote.factory.get(CommandWords.TAKE).description());
            System.out.printf("%-20s %s\n", "use", game.remote.factory.get(CommandWords.USE).description());
            System.out.printf("%-20s %s\n", "attack(with)", game.remote.factory.get(CommandWords.ATTACK).description());
            System.out.printf("%-20s %s\n", "go", game.remote.factory.get(CommandWords.GO).description());
            System.out.printf("%-20s %s\n", "help", game.remote.factory.get(CommandWords.HELP).description());
            System.out.printf("%-20s %s\n", "map", game.remote.factory.get(CommandWords.MAP).description());
            System.out.printf("%-20s %s\n", "(inv)entory", game.remote.factory.get(CommandWords.INVENTORY).description());
            System.out.printf("%-20s %s\n", "exit", game.remote.factory.get(CommandWords.EXIT).description());
            System.out.printf("%-20s %s\n", "quit", game.remote.factory.get(CommandWords.QUIT).description());
            System.out.println("=====================");
        } else {
            switch (game.dictionary.commandDictionary.lookup(arg)) {
                case UNKNOWN -> System.out.println("Unknown command.");
                case PLAY -> System.out.printf("%-20s %50s\n", "play", game.remote.factory.get(CommandWords.PLAY).description());
                case LOAD -> System.out.printf("%-20s %50s\n", "load", game.remote.factory.get(CommandWords.LOAD).description());
                case SAVE -> System.out.printf("%-20s %50s\n", "save", game.remote.factory.get(CommandWords.SAVE).description());
                case INFO -> System.out.printf("%-20s %50s\n", "info", game.remote.factory.get(CommandWords.INFO).description());
                case DROP -> System.out.printf("%-20s %50s\n", "drop", game.remote.factory.get(CommandWords.DROP).description());
                case TAKE -> System.out.printf("%-20s %50s\n", "take", game.remote.factory.get(CommandWords.TAKE).description());
                case USE -> System.out.printf("%-20s %50s\n", "use", game.remote.factory.get(CommandWords.USE).description());
                case ATTACK -> System.out.printf("%-20s %50s\n", "attack(with)", game.remote.factory.get(CommandWords.ATTACK).description());
                case GO -> System.out.printf("%-20s %50s\n", "go", game.remote.factory.get(CommandWords.GO).description());
                case HELP -> System.out.printf("%-20s %50s\n", "help", game.remote.factory.get(CommandWords.HELP).description());
                case MAP -> System.out.printf("%-20s %50s\n", "map", game.remote.factory.get(CommandWords.MAP).description());
                case INVENTORY -> System.out.printf("%-20s %50s\n", "(inv)entory", game.remote.factory.get(CommandWords.INVENTORY).description());
                case EXIT -> System.out.printf("%-20s %50s\n", "exit", game.remote.factory.get(CommandWords.EXIT).description());
                case QUIT -> System.out.printf("%-20s %50s\n", "quit", game.remote.factory.get(CommandWords.QUIT).description());
            }
        }
    }
}
