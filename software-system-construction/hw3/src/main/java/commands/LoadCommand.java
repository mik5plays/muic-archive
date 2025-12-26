package commands;

import org.example.Game;
import org.example.Player;
import org.example.Save;

import java.io.*;

public class LoadCommand implements Command {

    public final String description = "Load a save from a file. Usage: load <file_name>";

    @Override
    public String description() { return description; }

    @Override
    public void execute(Game game, Player player, String arg) {
        try (ObjectInputStream input = new ObjectInputStream(new FileInputStream(arg))) {
            Save loadedSave = (Save) input.readObject();

            game.map.clear();
            game.map.addAll(loadedSave.map);

            player.setValuesFromLoad(
                    loadedSave.health,
                    loadedSave.maxHealth,
                    loadedSave.attackPower,
                    loadedSave.critRate,
                    loadedSave.critDamage,
                    loadedSave.inventory,
                    loadedSave.inventorySlots,
                    loadedSave.position
            );

            System.out.printf("Loaded from save <%s>.\n", arg);

        } catch (IOException | ClassNotFoundException e) {
            System.out.printf("An error with loading occurred: %s\n", e.getMessage());
        }
    }
}
