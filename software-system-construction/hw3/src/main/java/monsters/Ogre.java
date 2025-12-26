package monsters;

import enums.MonsterType;
import items.*;
import objects.*;

public class Ogre extends Monster{
    public Ogre() {
        super("Ogre",
                "A big and beefy foe. Strong, so proceed with caution.",
                MonsterType.OGRE,
                new StrengthPotion(),
                1,
                100,
                100,
                10,
                1,
                0.2,
                1.5);
    }
}
