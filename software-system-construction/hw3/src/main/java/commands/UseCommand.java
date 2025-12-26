package commands;

import enums.Items;
import objects.Item;
import enums.ItemProperty;
import org.example.Game;
import org.example.Player;

public class UseCommand implements Command {

    public final String description = "Use an item in the inventory. Usage: use <item>";

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

        boolean hasUsed = false;
        Item itemUsed = null;
        Items itemToUse = game.dictionary.itemDictionary.lookup(arg);

        if (itemToUse.equals(Items.UNKNOWN)) {
            System.out.println("Unknown item.");
            return;
        }

        for (Item item: player.inventory) {
            if (itemToUse.equals(item.type)) {
                if (item.property.equals(ItemProperty.WEAPON)) {
                    System.out.printf("Use attack %s instead!\n", item.name);
                    return;
                }
                hasUsed = true;
                itemUsed = item;
                item.onConsume(game, player); // inventory is updated every turn in Game
                break;
            }
        }
        if (hasUsed) { System.out.printf("You have used: %s\n", itemUsed.name); }
        else { System.out.println("You do not have that, perhaps try again?"); }
    }
}
