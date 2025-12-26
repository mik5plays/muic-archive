package io.muic.ooc.fab;


import io.muic.ooc.fab.view.SimulatorView;

import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.IOException;
import java.util.*;

import java.io.PrintWriter;
import java.io.FileWriter;

public class Simulator {

    // Constants representing configuration information for the simulation.
    // The default width for the grid.
    private static final int DEFAULT_WIDTH = 120;
    // The default depth of the grid.
    private static final int DEFAULT_DEPTH = 80;
//    // The probability that a fox will be created in any given grid position.
//    private static final double FOX_CREATION_PROBABILITY = 0.02;
//    // The probability that a rabbit will be created in any given position.
//    private static final double RABBIT_CREATION_PROBABILITY = 0.08;

    // Lists of entities in the field.
    private List<Actor> animals;
    // The current state of the field.
    private Field field;
    // The current step of the simulation.
    private int step;
    // A list of views that the Simulation relies on [Observer pattern].
    private List<SimulatorView> views;
    // Random generator
    private static final Random RANDOM = new Random();

    /**
     * Construct a simulation field with default size.
     */
    public Simulator() {
        this(DEFAULT_DEPTH, DEFAULT_WIDTH);
    }

    /**
     * Create a simulation field with the given size.
     *
     * @param depth Depth of the field. Must be greater than zero.
     * @param width Width of the field. Must be greater than zero.
     */
    public Simulator(int depth, int width) {
        if (width <= 0 || depth <= 0) {
            System.out.println("The dimensions must be >= zero.");
            System.out.println("Using default values.");
            depth = DEFAULT_DEPTH;
            width = DEFAULT_WIDTH;
        }

        animals = new ArrayList<>();
        views = new ArrayList<>();
        field = new Field(depth, width);

        // Create a view of the state of each location in the field.
        SimulatorView view = new SimulatorView(depth, width);
        for (ActorType type : ActorType.values()) {
            view.setColor(type);
        }
        view.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {
            }

            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }

            @Override
            public void windowClosed(WindowEvent e) {
            }

            @Override
            public void windowIconified(WindowEvent e) {
            }

            @Override
            public void windowDeiconified(WindowEvent e) {
            }

            @Override
            public void windowActivated(WindowEvent e) {
            }

            @Override
            public void windowDeactivated(WindowEvent e) {
            }
        });

        views.add(view);

        // Setup a valid starting point.
        reset();
    }

    // Notify observers to update
    private void notifyObservers() {
        for (Observer o : views) {
            o.update(step, field);
        }
    }
    /**
     * Run the simulation from its current state for a reasonably long period
     * (4000 steps).
     */
    public void runLongSimulation() {
        simulate(4000);
    }

    /**
     * Run the simulation for the given number of steps. Stop before the given
     * number of steps if it ceases to be viable.
     *
     * @param numSteps The number of steps to run for.
     */
    public void simulate(int numSteps) {
        for ( Observer view : views ) {
            if (view instanceof SimulatorView v) {
                for (int step = 1; step <= numSteps && v.isViable(field); step++) {
                    simulateOneStep();
                    // delay(60);   // uncomment this to run more slowly
                }
            }
        }
    }

    /**
     * Run the simulation from its current state for a single step. Iterate over
     * the whole field updating the state of each fox and rabbit.
     */
    public void simulateOneStep() {
        step++;

        // Provide space for newborn Animals.
        List<Actor> newAnimals = new ArrayList<>();
        // Let all Animals act.
        for (Iterator<Actor> it = animals.iterator(); it.hasNext();) {
            Actor entity = it.next();
            if (entity instanceof Rabbit r) {
                r.run(newAnimals);
            } else if (entity instanceof Fox f) {
                f.hunt(newAnimals);
            } else if (entity instanceof Tiger t) {
                t.hunt(newAnimals);
            } else if (entity instanceof Hunter h) {
                h.hunt();
            }
            if (entity instanceof Animal a) {
                if (!a.isAlive()) {
                    it.remove();
                }
            }
        }

        // Add the newly born animals to the main lists.
        animals.addAll(newAnimals);

        write("output.txt", step); // log number of animal in each simulation step

        notifyObservers();
    }

    /**
     * Reset the simulation to a starting position.
     */
    public void reset() {
        step = 0;
        animals.clear();
        populate();

        // Show the starting state in the view.
        notifyObservers();
    }
    /**
     * Writes information to file (includes Animal counts)
     */
    public void write(String name, int step) {
        // Do an up-to-date count of the field.
        FieldStats stats = new FieldStats();
        stats.generateCounts(field);
        Map<ActorType, Counter> counters = stats.getCounters();

        try (PrintWriter pw = new PrintWriter(new FileWriter(name, true))) {
            pw.printf("Simulation Population Counts [Step %d]%n", step);
            for (ActorType key : ActorType.values()) {
                Counter c = counters.getOrDefault(key, new Counter(key.name()));
                pw.printf("%-20s %7d%n", c.getName(), c.getCount());
            }
        } catch (IOException e) {
            e.printStackTrace(); // error handling
        }
    }

    /**
     * Randomly populate the field with animals.
     */
    private void populate() {
        field.clear();
        // Create hunters: fixed number across the whole simulation.
        int huntersOnField = 0;
        for (int row = 0; row < field.getDepth(); row++) {
            for (int col = 0; col < field.getWidth(); col++) {
                for (ActorType type : ActorType.values()) {
                    if (RANDOM.nextDouble() <= type.getCreationProbability()) {
                        Location location = new Location(row, col);
                        if (type.name().equals("HUNTER") && huntersOnField >= ActorType.HUNTER.getMaxLitterSize()) { continue; } // too many hunters
                        Actor animal = ActorFactory.createNew(type, true, field, location);
                        animals.add(animal);
                        if (type.name().equals("HUNTER")) { huntersOnField++; } // counts hunters on the field.
                    }
                }
                // else leave the location empty.
            }
        }
    }

    /**
     * Pause for a given time.
     *
     * @param millisec The time to pause for, in milliseconds
     */
    private void delay(int millisec) {
        try {
            Thread.sleep(millisec);
        } catch (InterruptedException ie) {
            // wake up
        }
    }
}
