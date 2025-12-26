// This should go into Vehicle.java
public class Vehicle {
    int passengers; // number of passengers
    int fuelCap;    // fuel capacity in gallons
    int mpg;        // fuel consumption in miles per gallon

    public void range() {
        System.out.println("Estimated range is " + this.mpg * this.fuelCap);
    }
}

