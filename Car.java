/**
 * Purpose of this work is to create a car park management system that manages parking slots and cars.
 * Reference for regrex: https://www.w3schools.com/java/java_regex.asp
 * Reference for advanced features: https://www.javatpoint.com/java-get-current-date#:~:text=Get%20Current%20Date%20%26%20Time%3A%20java,the%20current%20date%20and%20time.
 * 
 * @author Rithikun Sen 103800533
 * @version JDK version 20.0.2; Program version 1.0
 * @date Created on 16 Oct 2023
 */

/**
 * Represents a car with a registration number, make, model, and year.
 */
public class Car {
    private String registrationNumber;
    private boolean isStaff;

    /**
     * Constructor for objects of class Car
     * 
     * @param registrationNumber
     * @param isStaff
     */
    public Car(String registrationNumber, boolean isStaff) {
        this.registrationNumber = registrationNumber;
        this.isStaff = isStaff;
    }

    /**
     * Get the registration number of the car
     * 
     * @return
     */
    public String getRegistrationNumber() {
        return registrationNumber;
    }

    /**
     * Get the staff status of the car
     * 
     * @return
     */
    public boolean isStaff() {
        return isStaff;
    }
}
