package commands;

import org.example.Game;
import org.example.Player;
import org.example.Save;

import java.io.*;

public class SaveCommand implements Command {

    public final String description = "Save the current game to a given file name. Usage: save <file_name>";

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
        } // can't save an unplayable game

        try (ObjectOutputStream output = new ObjectOutputStream(new FileOutputStream(arg))) {
            Save toSave = new Save(
                    game.asciiMap,
                    game.map,
                    player.getHealth(),
                    player.getMaxHealth(),
                    player.getAttackPower(),
                    player.getCritRate(),
                    player.getCritDamage(),
                    player.getInventory(),
                    player.getInventorySlots(),
                    player.getPosition()
                    );
            output.writeObject(toSave);
            System.out.printf("Saved current game progress to <%s>.\n", arg);
        } catch (IOException e) {
            System.out.printf("An error with saving occurred: %s\n", e.getMessage());
        }
    }
}
