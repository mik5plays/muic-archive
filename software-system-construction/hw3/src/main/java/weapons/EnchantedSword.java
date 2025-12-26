package weapons;

import enums.ItemProperty;
import enums.Items;
import enums.PlayerProperty;
import objects.*;
import org.example.*;

public class EnchantedSword extends Weapon {
    private int durability; // This weapon has durability.

    public EnchantedSword() {
        super("Enchanted Sword",
                "A wooden sword imbued with powers allowing for higher strength. Breaks after 40 uses.",
                Items.ENCHANTEDSWORD,
                ItemProperty.WEAPON,
                PlayerProperty.ATTACKPOWER,
                false,
                20);
        this.durability = 40;
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
