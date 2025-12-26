package commands;

import org.example.Game;
import org.example.Player;

public class QuitCommand implements Command{

    public final String description = "Quit the current game, returning to startup. Usage: quit";

    @Override
    public String description() { return description; }

    @Override
    public void execute(Game game, Player player, String arg) {
        player.reset();
        game.map.clear();
        game.displayStartupPrompt();
    }
}
