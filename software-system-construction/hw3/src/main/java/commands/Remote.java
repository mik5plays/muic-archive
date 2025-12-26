package commands;

// Invoker class for Commands
import enums.CommandWords;
import org.example.*;

public class Remote {
    public CommandFactory factory;

    public Remote(CommandFactory f) {
        this.factory = f;
    }

    public boolean process(Game game, Player player, CommandInput input) {
        boolean wantToExit = false;

        if (input.getCommand() == CommandWords.UNKNOWN) { System.out.println("Invalid command. Try again."); }
        else {
            Command command = factory.get(input.getCommand());
            command.execute(game, player, input.getArg());
            if (input.getCommand() == CommandWords.EXIT) { wantToExit = true; } // extra addition for wantToExit
        }

        return wantToExit;
    }

}
