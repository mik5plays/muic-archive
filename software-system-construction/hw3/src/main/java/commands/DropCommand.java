package commands;
import enums.Items;
import objects.Item;
import org.example.*;

public class DropCommand implements Command {
    public final String description = "Drop an item from the inventory. Usage: drop <item>";

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

        Items itemToDrop = game.dictionary.itemDictionary.lookup(arg);

        boolean hasDropped = false;
        Item itemDropped = null;

        if (itemToDrop.equals(Items.UNKNOWN)) {
            System.out.println("Unknown item.");
            return;
        }

        for (Item item: player.inventory) {
            if (itemToDrop.equals(item.type)) {
                hasDropped = true;
                itemDropped = item;
                player.position.addItem(item);
                player.inventory.remove(item); // remove from inventory
                break;
            }
        }

        if (hasDropped) { System.out.printf("You have dropped: %s\n", itemDropped.name); }
        else { System.out.println("You do not have that, perhaps try again?"); }

    }
}
