package dictionary;

import enums.MonsterType;

import java.util.*;

public class MonsterDictionary implements Dictionary<MonsterType> {

    private Map<String, MonsterType> monsterMap;

    public MonsterDictionary() {
        monsterMap = new HashMap<>();

        monsterMap.put("zombie", MonsterType.ZOMBIE);
        monsterMap.put("vagrant", MonsterType.VAGRANT);
        monsterMap.put("ogre", MonsterType.OGRE);
        monsterMap.put("duke", MonsterType.DUKE);

    }

    public MonsterType lookup(String monster) {
        MonsterType word = monsterMap.get(monster.toLowerCase());
        return Objects.requireNonNullElse(word, MonsterType.UNKNOWN);
    }
}
