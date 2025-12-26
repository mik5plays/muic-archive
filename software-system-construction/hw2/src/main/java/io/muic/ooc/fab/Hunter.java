package io.muic.ooc.fab;

import java.util.Iterator;
import java.util.List;

/*
I assume there will be a fixed number of Hunters as otherwise I think it will be too unfair
for the animals. As such, they won't be able to breed. Might change later but I have set
breeding probability to 0 for now. They can be created, but only to some threshold.
 */
public class Hunter extends Actor {
    public Hunter(Field field, Location location) {
        super(ActorType.HUNTER, field, location);
    }

    public void hunt() {
        // Immortal, so
        Location newLocation = findPrey();
        if (newLocation == null) {
            // No food found - try to move to a free location.
            newLocation = field.freeAdjacentLocation(location);
        }
        // See if it was possible to move.
        if (newLocation != null) {
            setLocation(newLocation);
        }
    }

    private Location findPrey() {
        List<Location> adjacent = field.adjacentLocations(location);
        Iterator<Location> it = adjacent.iterator();
        while (it.hasNext()) {
            Location where = it.next();
            Actor entity = field.getObjectAt(where);
            if (entity instanceof Animal a) { // Hunter kills any Animal
                if (a.isAlive()) {
                    a.setDead();
                    return where;
                }
            }
        }
        return null;
    }
}
