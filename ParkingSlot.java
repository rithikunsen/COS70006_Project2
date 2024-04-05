
/**
 * Purpose of this work is to create a car park management system that manages
 * parking slots and cars.
 * 
 * @author Rithikun Sen 103800533
 * @version JDK version 20.0.2; Program version 1.0
 * @date Created on 16 Oct 2023
 */

/**
 * Represents a parking slot with a car and occupied status.
 */
public class ParkingSlot {
    private boolean occupied;
    private Car car;

    /**
     * Constructor for objects of class ParkingSlot
     */
    public ParkingSlot() {
        this.occupied = false;
        this.car = null;
    }

    /**
     * Get the occupied status of the parking slot
     * 
     * @return
     */
    public boolean isOccupied() {
        return occupied;
    }

    /**
     * Get the car in the parking slot
     * 
     * @return
     */
    public Car getCar() {
        return car;
    }

    /**
     * Park a car in the parking slot
     * 
     * @param car
     */
    public void parkCar(Car car) {
        this.car = car;
        this.occupied = true;
    }

    /**
     * Remove a car from the parking slot
     */
    public void removeCar() {
        this.car = null;
        this.occupied = false;
    }
}
