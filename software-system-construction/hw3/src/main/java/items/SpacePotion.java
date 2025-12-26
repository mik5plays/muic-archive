package items;

import enums.Items;
import enums.PlayerProperty;
import objects.Item;
import enums.ItemProperty;
import org.example.*;

public class SpacePotion extends Item {
    public SpacePotion() {
        super("Space Potion",
                "Consumable. Increases inventory space by 3.",
                Items.SPACEPOTION,
                ItemProperty.PERMBUFF,
                PlayerProperty.INVENTORYSLOTS,
                true, 3);
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
