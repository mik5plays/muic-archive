package io.muic.ooc.fab;

import java.awt.Color;

public enum ActorType {
    FOX(15, 150, 0.08, 2, 0.02, true, 15, Color.BLUE),
    RABBIT(5, 40, 0.12, 4, 0.08, true, 9, Color.ORANGE),
    TIGER(10, 150, 0.05, 2, 0.01, false, 0, Color.RED), // Tiger is apex predator so cannot be eaten for now.
    HUNTER(0, 2147483647, 0, 10, 0.2, false, 0, Color.GREEN);

    private final int BREEDING_AGE;
    private final int MAX_AGE;
    private final double BREEDING_PROBABILITY;
    private final int MAX_LITTER_SIZE;
    private final double CREATION_PROBABILITY;
    private final boolean IS_EDIBLE; // Just means it can be killed by something, Hunter is the only one that can't be killed.
    private final int FOOD_VALUE;
    private final Color COLOR;

    ActorType(int BREEDING_AGE, int MAX_AGE, double BREEDING_PROBABILITY, int MAX_LITTER_SIZE, double CREATION_PROBABILITY, boolean IS_EDIBLE, int FOOD_VALUE, Color COLOR) {
        this.BREEDING_AGE = BREEDING_AGE;
        this.MAX_AGE = MAX_AGE;
        this.BREEDING_PROBABILITY = BREEDING_PROBABILITY;
        this.MAX_LITTER_SIZE = MAX_LITTER_SIZE;
        this.CREATION_PROBABILITY = CREATION_PROBABILITY;
        this.IS_EDIBLE = IS_EDIBLE;
        this.FOOD_VALUE = FOOD_VALUE;
        this.COLOR = COLOR;
    }

    // getters
    public int getBreedingAge() { return BREEDING_AGE; };
    public int getMaxAge() { return MAX_AGE; };
    public double getBreedingProbability() { return BREEDING_PROBABILITY; };
    public int getMaxLitterSize() { return MAX_LITTER_SIZE; };
    public double getCreationProbability() { return CREATION_PROBABILITY; };
    public boolean getIsEdible() { return IS_EDIBLE; }
    public int getFoodValue() { return FOOD_VALUE; }
    public Color getColor() { return COLOR; }

    // factory method - moved to ActorFactory instead
    // public abstract Animal createNew(boolean b, Field field, Location loc);
}
