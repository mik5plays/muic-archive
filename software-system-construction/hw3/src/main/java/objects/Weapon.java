package objects;

import enums.ItemProperty;
import enums.Items;
import enums.PlayerProperty;

abstract public class Weapon extends Item {

    public Weapon(String name, String description, Items type, ItemProperty property, PlayerProperty playerProperty, boolean isConsumable, double factor) {
        super(name, description, type, property, playerProperty, isConsumable, factor);
    }
}
