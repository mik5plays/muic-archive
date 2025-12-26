package items;

import enums.Items;
import enums.PlayerProperty;
import objects.Item;
import enums.ItemProperty;
import org.example.*;

public class HealingPotion extends Item {
    public HealingPotion() {
        super("Healing Potion",
                "Consumable. Heals the player by 50HP.",
                Items.HEALINGPOTION,
                ItemProperty.PERMBUFF,
                PlayerProperty.HP,
                true, 50);
    }

    @Override
    public void onConsume(Game game, Player player) {
        player.changeStat(this.playerProperty, this.factor);
        player.inventory.remove(this);
    }

    @Override
    public void onUpdate(Game game, Player player) {
        ;
    }
}
