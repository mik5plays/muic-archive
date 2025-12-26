package io.muic.ooc.fab;

import java.util.List;
import java.util.Random;

abstract public class Animal extends Actor {

    // individual fields shared by all Animals
    protected int age;
    protected boolean alive;

    // super Constructor
    public Animal(ActorType type, boolean randomAge, Field field, Location location) {
        super(type, field, location); // in Actor class
        age = 0;
        alive = true;
        if (randomAge) { age = RANDOM.nextInt(type.getMaxAge()); }
    }

    // Concrete methods
    public boolean isAlive() { return alive; }
    protected boolean canBreed() {
        return age >= type.getBreedingAge();
    }

    // Increase the age. This could result in the Animal's death.
    protected void incrementAge() {
        age++;
        if (age > type.getMaxAge()) {
            setDead();
        }
    }

    // Indicate that the Animal is no longer alive. It is removed from the field.
    protected void setDead() {
        alive = false;
        if (location != null) {
            field.clear(location);
            location = null;
            field = null;
        }
    }

    // Giving birth and breeding methods
    protected void giveBirth(List<Actor> newAnimals) {
        List<Location> free = field.getFreeAdjacentLocations(location);
        int births = breed();
        for (int b = 0; b < births && free.size() > 0; b++) {
            Location loc = free.remove(0);
            Actor young = ActorFactory.createNew(type, false, field, loc);
            newAnimals.add(young);
        }
    }

    protected int breed() {
        int births = 0;
        if (canBreed() && RANDOM.nextDouble() <= type.getBreedingProbability()) {
            births = RANDOM.nextInt(type.getMaxLitterSize()) + 1;
        }
        return births;
    }
}
