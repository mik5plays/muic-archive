package commands;

import org.example.*;

public class ExitCommand implements Command {
    public final String description = "Exits the entire game. Usage: exit";

    @Override
    public String description() { return description; }

    @Override
    public void execute(Game game, Player player, String arg) {
        System.out.println("Goodbye. Thank you for playing Zork.");
    }
}
