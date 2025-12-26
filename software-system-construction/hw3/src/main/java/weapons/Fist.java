package weapons;

import enums.ItemProperty;
import enums.Items;
import enums.PlayerProperty;
import objects.*;
import org.example.*;

public class Fist extends Weapon {
    public Fist() {
        super("Fist",
                "Just your bare fists. The starter weapon.",
                Items.FISTS,
                ItemProperty.WEAPON,
                PlayerProperty.ATTACKPOWER,
                false,
                5);
    }

    @Override
    public void onConsume(Game game, Player player) {
        ;
    }

    @Override
    public void onUpdate(Game game, Player player) {
        ;
    }
}
