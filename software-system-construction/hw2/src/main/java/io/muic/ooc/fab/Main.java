package io.muic.ooc.fab;

import io.muzoo.ssc.assignment.tracker.SscAssignment;

public class Main extends SscAssignment {

    public static void main(String[] args) {
        var simulator = new Simulator();
        simulator.simulate(50);
    }
}
