package io.muic.ooc.fab;

public class ActorFactory {
    public static Actor createNew(ActorType type, boolean randomAge, Field field, Location location) {
        switch (type.name()) {
            case "RABBIT": return new Rabbit(randomAge, field, location);
            case "FOX": return new Fox(randomAge, field, location);
            case "TIGER": return new Tiger(randomAge, field, location);
            case "HUNTER": return new Hunter(field, location);
            default: throw new IllegalArgumentException("Animal type not found.");
        }

    }
}
