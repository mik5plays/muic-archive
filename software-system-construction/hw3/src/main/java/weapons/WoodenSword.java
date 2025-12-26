package weapons;

import enums.ItemProperty;
import enums.Items;
import enums.PlayerProperty;
import objects.*;
import org.example.*;

public class WoodenSword extends Weapon {
    private int durability; // This weapon has durability.

    public WoodenSword() {
        super("Wooden Sword",
                "A basic weapon. Better than using your fists, but breaks after 20 uses.",
                Items.WOODENSWORD,
                ItemProperty.WEAPON,
                PlayerProperty.ATTACKPOWER,
                false,
                10);
        this.durability = 20;
    }

    @Override
    public void onConsume(Game game, Player player) {
        if (durability > 0) {
            this.durability--;
        } else {
            System.out.printf("%s broke.", this.name);
            player.inventory.remove(this);
        }
    }

    @Override
    public void onUpdate(Game game, Player player) {
        ;
    }
}
