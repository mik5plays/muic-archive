package monsters;

import enums.MonsterType;
import items.Chicken;
import objects.*;

public class Zombie extends Monster {
    public Zombie() {
        super("Zombie",
                "An undead foe that hits weak and is weak.",
                MonsterType.ZOMBIE,
                new Chicken(),
                0.8,
                20,
                20,
                3,
                1,
                0,
                1);
    }
}
