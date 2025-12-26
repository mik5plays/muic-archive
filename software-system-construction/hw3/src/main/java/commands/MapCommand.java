package commands;

import org.example.Game;
import org.example.Player;

public class MapCommand implements Command{

    public final String description = "Shows the current map: Usage: map";

    @Override
    public String description() { return description; }

    @Override
    public void execute(Game game, Player player, String arg) {
        System.out.println("=====");
        System.out.printf(game.asciiMap);
        System.out.println("=====");
    }


}
