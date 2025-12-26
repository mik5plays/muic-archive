package items;

import java.util.*;

import enums.Items;
import enums.PlayerProperty;
import objects.Item;
import enums.ItemProperty;
import org.example.*;

public class MagicStew extends Item {
    private Random random = new Random();

    public MagicStew() {
        super("Magic Stew",
                "Consumable. Permanently alters one of your stats. Use if you're feeling lucky!",
                Items.MAGICSTEW,
                ItemProperty.PERMBUFF,  // some random default for now
                PlayerProperty.HP,
                true, 0); // some arbitrary default for now
    }

    @Override
    public void onConsume(Game game, Player player) {
        playerProperty = PlayerProperty.values()[random.nextInt(PlayerProperty.values().length)];
        factor = switch (playerProperty) {
            case HP -> random.nextDouble() * 40 - 20;
            case MAXHP -> random.nextDouble() * 60 - 30;
            case ATTACKPOWER -> random.nextDouble() * 0.2 - 0.1;
            case CRITRATE -> random.nextDouble() * 0.4 - 0.2;
            case CRITDMG -> random.nextDouble() * 2 - 1;
            case INVENTORYSLOTS -> random.nextInt(5) - 2;
        };
        player.changeStat(playerProperty, factor);
        player.inventory.remove(this);
        System.out.printf("%s adjusted by %f\n", playerProperty.name(), factor);

        if (player.getHealth() < 0) {
            System.out.println("You were killed by the magic stew. Unlucky!");
            game.onFinish();
        }
    }

    @Override
    public void onUpdate(Game game, Player player) {
        ;
    }
}
