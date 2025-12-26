package objects;
import enums.ItemProperty;
import enums.Items;
import enums.PlayerProperty;
import org.example.*;

import java.io.Serializable;

abstract public class Item implements Serializable {
    private static final long serialVersionUID = 1L;
    /*
    Variables that all items hold
     */
    public String name;
    public String description;
    public Items type;
    public ItemProperty property; // some unique property that the item holds
    public PlayerProperty playerProperty;
    public Boolean isConsumable; // is the item consumable?
    public double factor; // how "strong" the property is

    public Item(String name, String description, Items type, ItemProperty property, PlayerProperty playerProperty, boolean isConsumable, double factor) {
        this.name = name;
        this.description = description;
        this.type = type;
        this.isConsumable = isConsumable;
        this.factor = factor;
        this.property = property;
        this.playerProperty = playerProperty;
    }

    abstract public void onConsume(Game game, Player player);
    // what happens if you can use the item, if consumable.
    // usually something with the factor gets affected.
    abstract public void onUpdate(Game game, Player player);
    // after every turn, something might happen so account for that.

}
