package monsters;
import enums.MonsterType;
import items.*;
import objects.*;

public class Vagrant extends Monster{
    public Vagrant() {
        super("Vagrant",
                "A wanderer lost in time. Moderately strong.",
                MonsterType.VAGRANT,
                new StrengthPotion(),
                0.6,
                50,
                50,
                7,
                1,
                0.1,
                1.2);
    }
}
