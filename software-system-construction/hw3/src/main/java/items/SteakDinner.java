package items;

import enums.Items;
import enums.PlayerProperty;
import objects.Item;
import enums.ItemProperty;
import org.example.*;

public class SteakDinner extends Item {
    public SteakDinner() {
        super("Steak Dinner",
                "Consumable. Permanently increases crit damage by 10%.",
                Items.STEAKDINNER,
                ItemProperty.PERMBUFF,
                PlayerProperty.CRITDMG,
                true, 0.1);
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
