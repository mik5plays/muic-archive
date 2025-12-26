package items;

import enums.Items;
import enums.PlayerProperty;
import objects.Item;
import enums.ItemProperty;
import org.example.*;

public class StrengthPotion extends Item {
    private int turnWhenConsumed;
    public boolean hasConsumed;

    public StrengthPotion() {
        super("Strength Potion",
                "Consumable. Increases attack power by 50% for 5 turns.",
                Items.STRENGTHPOTION,
                ItemProperty.TEMPBUFF,
                PlayerProperty.ATTACKPOWER,
                true, 0.5);
        hasConsumed = false;
        turnWhenConsumed = Integer.MIN_VALUE;
    }

    @Override
    public void onConsume(Game game, Player player) {
        if (!hasConsumed) {
            hasConsumed = true;
            turnWhenConsumed = game.getTurn();
            player.changeStat(this.playerProperty, this.factor);
        }
    }

    @Override
    public void onUpdate(Game game, Player player) {
        if (Math.abs(game.getTurn() - turnWhenConsumed) == 5) { // remove after 5 turns
            player.changeStat(this.playerProperty, -this.factor); // revert back to before
            player.inventory.remove(this);
        }
    }
}
