package io.muic.ooc.fab;

import java.util.List;

public class Rabbit extends Animal {
    // Characteristics shared by all rabbits (class variables).

    /**
     * Create a new rabbit. A rabbit may be created with age zero (a new born)
     * or with a random age.
     *
     * @param randomAge If true, the rabbit will have a random age.
     * @param field The field currently occupied.
     * @param location The location within the field.
     */
    public Rabbit(boolean randomAge, Field field, Location location) {
        super(ActorType.RABBIT, randomAge, field, location);
    }


    /**
     * This is what the rabbit does most of the time - it runs around. Sometimes
     * it will breed or die of old age.
     *
     * @param newAnimals A list to return newly born rabbits.
     */
    public void run(List<Actor> newAnimals) {
        incrementAge();
        if (alive) {
            giveBirth(newAnimals);
            // Try to move into a free location.
            Location newLocation = field.freeAdjacentLocation(location);
            if (newLocation != null) {
                setLocation(newLocation);
            } else {
                // Overcrowding.
                setDead();
            }
        }
    }
}
