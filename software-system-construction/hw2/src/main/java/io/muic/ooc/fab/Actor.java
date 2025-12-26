package io.muic.ooc.fab;

import java.util.Random;

abstract public class Actor {
    protected static final Random RANDOM = new Random();

    protected Location location;
    protected Field field;
    protected final ActorType type;

    public Location getLocation() {
        return location;
    }
    public ActorType getType() { return type; }

    public Actor(ActorType type, Field field, Location location) {
        this.type = type;
        this.field = field;
        setLocation(location);
    }

    // Place the Actor at the new location in the given field.
    protected void setLocation(Location newLocation) {
        if (location != null) { field.clear(location); }
        location = newLocation;
        field.place(this, newLocation);
    }
}
