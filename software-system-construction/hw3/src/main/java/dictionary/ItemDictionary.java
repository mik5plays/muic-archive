package dictionary;

import enums.Items;

import java.util.*;

public class ItemDictionary implements Dictionary<Items> {

    private Map<String, Items> validItemNames;

    public ItemDictionary() {
        validItemNames = new HashMap<>();

        validItemNames.put("chicken", Items.CHICKEN);
        validItemNames.put("healing potion", Items.HEALINGPOTION);
        validItemNames.put("healingpotion", Items.HEALINGPOTION);
        validItemNames.put("magic stew", Items.MAGICSTEW);
        validItemNames.put("magicstew", Items.MAGICSTEW);
        validItemNames.put("steak dinner", Items.STEAKDINNER);
        validItemNames.put("steakdinner", Items.STEAKDINNER);
        validItemNames.put("strength potion", Items.STRENGTHPOTION);
        validItemNames.put("strength", Items.STRENGTHPOTION);
        validItemNames.put("strengthpotion", Items.STRENGTHPOTION);
        validItemNames.put("fists", Items.FISTS);
        validItemNames.put("wooden sword", Items.WOODENSWORD);
        validItemNames.put("woodensword", Items.WOODENSWORD);
        validItemNames.put("enchanted sword", Items.ENCHANTEDSWORD);
        validItemNames.put("enchantedsword", Items.ENCHANTEDSWORD);
        validItemNames.put("space potion", Items.SPACEPOTION);
        validItemNames.put("spacepotion", Items.SPACEPOTION);
    }

    public Items lookup(String item) {
        Items word = validItemNames.get(item.toLowerCase());
        return Objects.requireNonNullElse(word, Items.UNKNOWN);
    }
}
