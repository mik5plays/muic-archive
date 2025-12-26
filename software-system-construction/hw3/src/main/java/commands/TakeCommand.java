package commands;

import enums.Items;
import objects.Item;
import org.example.Game;
import org.example.Player;

import java.util.Iterator;

public class TakeCommand implements Command {

    public final String description = "Take an item or weapon in the room. Usage: take <item>";

    @Override
    public String description() { return description;
    }
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

        boolean hasTaken = false;
        Item itemTaken = null;
        Items itemToTake = game.dictionary.itemDictionary.lookup(arg);

        if (itemToTake.equals(Items.UNKNOWN)) {
            System.out.println("Unknown item.");
            return;
        }

        if (player.inventory.size() == player.getInventorySlots()) {
            System.out.println("Cannot pick up, inventory is full!");
            return;
        }

        Iterator<Item> it = player.position.items.iterator();
        while (it.hasNext()) {
            Item current = it.next();
            if (itemToTake.equals(current.type)) {
                hasTaken = true;
                itemTaken = current;
                player.position.playerTakeItem(player, current);
                break;
            }
        }

        if (hasTaken) { System.out.printf("You have picked up: %s\n", itemTaken.name); }
        else { System.out.println("That doesn't exist here."); }
    }
}
