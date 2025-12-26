package io.muic.ooc.fab;

import java.util.List;
import java.util.Iterator;

public class Fox extends Animal {
    // Characteristics shared by all foxes (class variables).

    // The food value of a single rabbit. In effect, this is the
    // number of steps a fox can go before it has to eat again.
//    private static final int RABBIT_FOOD_VALUE = 9;

    // Individual characteristics (instance fields).
    // The fox's food level, which is increased by eating rabbits.
    private int foodLevel;

    /**
     * Create a fox. A fox can be created as a new born (age zero and not
     * hungry) or with a random age and food level.
     *
     * @param randomAge If true, the fox will have random age and hunger level.
     * @param field The field currently occupied.
     * @param location The location within the field.
     */
    public Fox(boolean randomAge, Field field, Location location) {
        super(ActorType.FOX, randomAge, field, location);
        foodLevel = RANDOM.nextInt(ActorType.RABBIT.getFoodValue()); // assuming a fox can only eat rabbit
    }

    /**
     * This is what the fox does most of the time: it hunts for rabbits. In the
     * process, it might breed, die of hunger, or die of old age.
     *
     * @param newAnimals A list to return newly born foxes.
     */
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

    /**
     * Make this fox more hungry. This could result in the fox's death.
     */
    private void incrementHunger() {
        foodLevel--;
        if (foodLevel <= 0) {
            setDead();
        }
    }

    /**
     * Look for rabbits adjacent to the current location. Only the first live
     * rabbit is eaten.
     *
     * @return Where food was found, or null if it wasn't.
     */
    private Location findFood() {
        List<Location> adjacent = field.adjacentLocations(location);
        Iterator<Location> it = adjacent.iterator();
        while (it.hasNext()) {
            Location where = it.next();
            Actor entity = field.getObjectAt(where);
            if (entity instanceof Animal a) {
                if (a.getType().getIsEdible()) {
                    if (a instanceof Fox || a instanceof Tiger) {
                        continue;
                    } // Foxes cannot eat each other, or Tigers
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
