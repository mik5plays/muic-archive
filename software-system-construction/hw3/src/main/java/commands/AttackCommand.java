package commands;

import enums.Items;
import objects.Item;
import objects.Weapon;
import org.example.*;

public class AttackCommand implements Command{

    public final String description = "Attacks with a weapon. Leave blank for fists. Usage: attack <weapon>";

    @Override
    public String description() { return description; }

    @Override
    public void execute(Game game, Player player, String arg) {
        // isPlayable check - will not bother to continue if these conditions are not met.
        if (game.map.isEmpty()) {
            System.out.println("Unable to play. No map initiated.");
            return;
        }
        if (player.getPosition() == null) {
            System.out.println("Unable to play. Player is not in a room.");
            return;
        }
        if (player.getPosition().monster == null) {
            System.out.printf("There is no monster in %s.\n", player.getPosition().name);
            return;
        }

        // weapon of choice
        Items weapon;
        if (arg.isBlank()) { weapon = Items.FISTS; }
        else { weapon = game.dictionary.itemDictionary.lookup(arg); }

        boolean hasAttacked = false;

        if (weapon.equals(Items.UNKNOWN)) {
            System.out.println("Unknown weapon.");
            return;
        }

        for (Item item: player.inventory) {
            if (weapon == item.type) {
                hasAttacked = true;
                player.position.monster.attacked(game, player, (Weapon) item);
                // update durability where applicable
                item.onConsume(game, player);
                // update items in inventory as this counts as a turn
                for (Item _item: player.inventory) {
                    _item.onUpdate(game, player);
                }
                game.incrementTurn();
                break;
            }
        }

        if (!hasAttacked) { System.out.println("Could not find sufficient weapon to attack with."); }

    }
}
