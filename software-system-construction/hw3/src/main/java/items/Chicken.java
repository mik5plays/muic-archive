package items;

import enums.Items;
import enums.PlayerProperty;
import objects.Item;
import enums.ItemProperty;
import org.example.*;

public class Chicken extends Item {
    public Chicken() {
        super("Chicken",
                "Consumable. Permanently increases crit rate by 5%.",
                Items.CHICKEN,
                ItemProperty.PERMBUFF,
                PlayerProperty.CRITRATE,
                true, 0.05);
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
