package dictionary;

public class CentralDictionary {
    public final CommandDictionary commandDictionary;
    public final DirectionDictionary directionDictionary;
    public final ItemDictionary itemDictionary;
    public final MonsterDictionary monsterDictionary;

    public CentralDictionary() {
        commandDictionary = new CommandDictionary();
        directionDictionary = new DirectionDictionary();
        itemDictionary = new ItemDictionary();
        monsterDictionary = new MonsterDictionary();
    }
}
