package commands;

import enums.Direction;
import enums.PlayerProperty;
import objects.Item;
import org.example.*;

public class GoCommand implements Command {
    public final String description = "Go some direction (north, south, east, west). Usage: go <direction>.";

    @Override
    public String description() { return description; }

    @Override
    public void execute(Game game, Player player, String arg) {
        if (game.map.isEmpty()) {
            System.out.println("Unable to play. No map initiated.");
            return;
        }
        if (player.getPosition() == null) {
            System.out.println("Unable to play. Player is not in a room.");
            return;
        }

        Direction directionToGo = game.dictionary.directionDictionary.lookup(arg);

        if (directionToGo == Direction.UNKNOWN){
            System.out.println("Invalid direction.");
        }
        else if (player.position.neighbours.get(directionToGo) == null) {
            System.out.println("There is nowhere to go!");
        } else {
            player.position = player.position.neighbours.get(directionToGo);
            if (!player.position.hasVisited) { player.changeStat(PlayerProperty.MAXHP, 10); player.position.hasVisited = true; }
            game.incrementTurn();
            game.displayRoomInfo();
            // update items in inventory as this counts as a turn
            for (Item _item: player.inventory) {
                _item.onUpdate(game, player);
            }
        }
    }
}
