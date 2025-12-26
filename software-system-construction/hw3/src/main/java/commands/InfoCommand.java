package commands;

import org.example.Game;
import org.example.Player;

public class InfoCommand implements Command{

    public final String description = "Displays player and room information. Usage: info";

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
        System.out.println("===== Player Stats =====");
        System.out.printf("%-20s %20.1f/%.1f\n", "HP:", player.getHealth(), player.getMaxHealth());
        System.out.printf("%-20s %20.1f\n", "Attack Power (%):", player.getAttackPower() * 100);
        System.out.printf("%-20s %20.1f\n", "Crit Rate (%):", player.getCritRate() * 100);
        System.out.printf("%-20s %20.1f\n", "Crit Damage (%):", player.getCritDamage() * 100);
        System.out.printf("%-20s %20.0f/%.0f\n", "Inventory:", player.getInventorySize(), player.getInventorySlots());
        System.out.println("========================");
        game.displayRoomInfo();
        System.out.println("========================");
    }

}
