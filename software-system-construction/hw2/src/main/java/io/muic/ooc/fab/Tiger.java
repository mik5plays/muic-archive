package io.muic.ooc.fab;

import java.util.Iterator;
import java.util.List;

public class Tiger extends Animal {
    private int foodLevel;

    // The stats for the tiger in ActorType are arbitrary and I based it off fox and rabbit.
    public Tiger(boolean randomAge, Field field, Location location) {
        super(ActorType.TIGER, randomAge, field, location);
        foodLevel = RANDOM.nextInt(ActorType.FOX.getFoodValue()); // since fox is more nutritious, so base it off Fox.
    }

    public void hunt(List<Actor> newAnimals) {
        incrementAge();
        incrementHunger();
        if (alive) {
            giveBirth(newAnimals);
            // Move towards a source of food if found.
            Location newLocation = findFood();
            if (newLocation == null) {
                // No food found - try to move to a free location.
                newLocation = field.freeAdjacentLocation(location);
            }
            // See if it was possible to move.
            if (newLocation != null) {
                setLocation(newLocation);
            } else {
                // Overcrowding.
                setDead();
            }
        }
    }

    private void incrementHunger() {
        foodLevel--;
        if (foodLevel <= 0) {
            setDead();
        }
    }

    private Location findFood() {
        List<Location> adjacent = field.adjacentLocations(location);
        Iterator<Location> it = adjacent.iterator();
        while (it.hasNext()) {
            Location where = it.next();
            Actor entity = field.getObjectAt(where);
            if (entity instanceof Animal a) {
                if (a != null && a.getType().getIsEdible()) {
                    if (a.isAlive()) {
                        a.setDead();
                        foodLevel = a.getType().getFoodValue();
                        return where;
                    }
                }
            }
        }
        return null;
    }
}
