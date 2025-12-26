package commands;

import objects.Item;
import org.example.Game;
import org.example.Player;

public class InventoryCommand implements Command{
    public final String description = "Displays your inventory. Usage: inventory, inv";

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

        System.out.println("Your inventory:");
        for (Item item: player.getInventory()) {
            System.out.printf("%-20s%40s\n", item.name, item.description);
        }
        System.out.printf("====%.0f/%.0f====\n", player.getInventorySize(), player.getInventorySlots());
    }
}
