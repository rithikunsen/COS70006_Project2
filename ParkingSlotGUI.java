
/**
 * Purpose of this work is to create a car park management system that manages parking slots and cars.
 * 
 * @author Rithikun Sen 103800533
 * @version JDK version 20.0.2; Program version 1.0
 * @date Created on 16 Oct 2023
 */

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;

public class ParkingSlotGUI {
    private JFrame frame; // The main window
    private JPanel panel; // The main panel
    private Map<String, ParkingSlot> parkingSlots; // The parking slots
    private JTextArea outputArea; // The text area for displaying output
    private JTextField inputField; // The text field for input
    private JPanel slotsPanel; // The panel for displaying parking slots

    /**
     * Constructor for objects of class ParkingSlotGUI
     */
    public ParkingSlotGUI() {
        frame = new JFrame("Swinburne Car Parking System");
        panel = new JPanel();
        parkingSlots = new HashMap<>();
        outputArea = new JTextArea(15, 40);
        inputField = new JTextField(20);
        slotsPanel = new JPanel();

        initializeGUI();
    }

    /**
     * Initialize the GUI
     */
    private void initializeGUI() {
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);
        // add title center and set the font big and bold
        JLabel title = new JLabel("Swinburne Car Parking System");
        title.setFont(new Font("Arial", Font.BOLD, 20));
        title.setHorizontalAlignment(JLabel.CENTER);
        frame.add(title, BorderLayout.NORTH);

        frame.add(panel, BorderLayout.CENTER);
        frame.add(slotsPanel, BorderLayout.SOUTH);

        addButton("List all slots", Color.BLUE, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                listAllSlots();
            }
        });

        addButton("Park a car", Color.GREEN, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String input = JOptionPane.showInputDialog("Enter a value (e.g., S001 T12345 or V001 T67890):");
                parkCar(input);
            }
        });

        addButton("Find a car by registration", Color.RED, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String input = JOptionPane.showInputDialog("Enter a value (e.g., T12345):");
                findCarByRegistration(input);
            }
        });

        addButton("Add a parking slot", Color.YELLOW, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String input = JOptionPane.showInputDialog("Enter a value (e.g., S001 or V001):");
                addParkingSlot(input);
            }
        });

        addButton("Delete a parking slot", Color.ORANGE, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String input = JOptionPane.showInputDialog("Enter a value (e.g., S001 or V001):");
                deleteParkingSlot(input);
            }
        });

        addButton("Remove a car", Color.PINK, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String input = JOptionPane.showInputDialog("Enter a value (e.g., T12345):");
                removeCar(input);
            }
        });

        addButton("Exit", Color.GRAY, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        // panel.add(new JLabel("Input:"));
        // panel.add(inputField);
        panel.add(new JScrollPane(outputArea));

        frame.setVisible(true);
    }

    /**
     * Add a button to the panel
     * 
     * @param label    The label of the button
     * @param color    The background color of the button
     * @param listener The action listener for the button
     */
    private void addButton(String label, Color color, ActionListener listener) {
        JButton button = new JButton(label);
        button.setBackground(color);
        button.addActionListener(listener);
        panel.add(button);
    }

    /**
     * List all parking slots
     */
    private void listAllSlots() {
        slotsPanel.removeAll();

        for (String slotId : parkingSlots.keySet()) {
            ParkingSlot slot = parkingSlots.get(slotId);
            String status = slot.isOccupied() ? "Occupied" : "Vacant";
            String ownerType = (slot.isOccupied() && slot.getCar().isStaff()) ? "Staff" : "Visitor";

            JButton slotButton = new JButton();
            slotButton.setText("<html>" + slotId + "<br/>" + ownerType + "<br/>" + status + "<br/>" +
                    (slot.isOccupied() ? "Car: " + slot.getCar().getRegistrationNumber() : "N/A") + "</html");

            // Set the color based on ownerType
            if (ownerType.equals("Staff")) {
                slotButton.setBackground(Color.YELLOW);
            } else {
                slotButton.setBackground(Color.GREEN);
            }
            slotsPanel.add(slotButton);

            if (slot.isOccupied()) {
                final String slotIdCopy = slotId;
                slotButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        // Handle button click for detailed information
                        displaySlotDetails(slotIdCopy);
                    }
                });
            }
        }

        slotsPanel.revalidate();
        slotsPanel.repaint();
    }

    /**
     * Display detailed information of a parking slot
     * 
     * @param slotId The ID of the parking slot
     */
    private void displaySlotDetails(String slotId) {
        // Retrieve the ParkingSlot with the given slotId and display details
        ParkingSlot slot = parkingSlots.get(slotId);
        outputArea.setText("Slot ID: " + slotId + "\nOwner: " + (slot.getCar().isStaff() ? "Staff" : "Visitor") +
                "\nStatus: " + (slot.isOccupied() ? "Occupied" : "Vacant"));
        if (slot.isOccupied()) {
            outputArea.append("\nCar Registration: " + slot.getCar().getRegistrationNumber());
        }
    }

    /**
     * Park a car in a parking slot
     * 
     * @param input The input string containing the slot ID and car registration
     */
    private void parkCar(String input) {
        String[] parts = input.split(" ");
        if (parts.length != 2) {
            outputArea.setText(
                    "Invalid input for parking a car. \nPlease provide slot ID and car registration \n(e.g., S001 T12345 or V001 T67890).");
            return;
        }
        String slotId = parts[0];
        String registrationNumber = parts[1];

        if (!parkingSlots.containsKey(slotId)) {
            outputArea.setText("Slot not found: " + slotId);
            return;
        }

        ParkingSlot slot = parkingSlots.get(slotId);
        if (slot.isOccupied()) {
            outputArea.setText("Slot " + slotId + " is already occupied.");
            return;
        }

        boolean isStaff = slotId.startsWith("S");

        slot.parkCar(new Car(registrationNumber, isStaff));
        outputArea.setText("Car parked successfully in slot " + slotId);
    }

    /**
     * Find a car by registration number
     * 
     * @param registration The registration number of the car
     */
    private void findCarByRegistration(String registration) {
        for (String slotId : parkingSlots.keySet()) {
            ParkingSlot slot = parkingSlots.get(slotId);
            if (slot.isOccupied() && slot.getCar().getRegistrationNumber().equals(registration)) {
                outputArea.setText(
                        "Car found in slot " + slotId + ": Owner: " + (slot.getCar().isStaff() ? "Staff" : "Visitor"));
                return;
            }
        }
        outputArea.setText("Car with registration " + registration + " not found in any slot.");
    }

    /**
     * Add a parking slot
     * 
     * @param slotDetails The slot ID of the parking slot
     */
    private void addParkingSlot(String slotDetails) {
        if (!slotDetails.matches("[SV]\\d{3}")) {
            outputArea.setText("Invalid input for adding a parking slot. \nSlot format should be S001 or V001.");
            return;
        }

        String slotId = slotDetails;
        if (parkingSlots.containsKey(slotId)) {
            outputArea.setText("Slot " + slotId + " already exists.");
            return;
        }

        parkingSlots.put(slotId, new ParkingSlot());
        outputArea.setText("Slot " + slotId + " added successfully.");
    }

    /**
     * Delete a parking slot
     * 
     * @param slotId The ID of the parking slot
     */
    private void deleteParkingSlot(String slotId) {
        if (!slotId.matches("[SV]\\d{3}")) {
            outputArea.setText(
                    "Invalid input for deleting a parking slot. \nPlease provide a valid slot ID (e.g., S001 or V001).");
            return;
        }

        if (!parkingSlots.containsKey(slotId)) {
            outputArea.setText("Slot " + slotId + " does not exist.");
            return;
        }

        ParkingSlot slot = parkingSlots.get(slotId);
        if (slot.isOccupied()) {
            outputArea.setText("Slot " + slotId + " is occupied and cannot be deleted.");
        } else {
            parkingSlots.remove(slotId);
            outputArea.setText("Slot " + slotId + " deleted successfully.");
        }
    }

    /**
     * Remove a car from a parking slot
     * 
     * @param registration The registration number of the car
     */
    private void removeCar(String registration) {
        if (!registration.matches("T\\d{5}")) {
            outputArea.setText(
                    "Invalid input for removing a car. Please provide a valid car registration (e.g., T12345).");
            return;
        }

        for (String slotId : parkingSlots.keySet()) {
            ParkingSlot slot = parkingSlots.get(slotId);
            if (slot.isOccupied() && slot.getCar().getRegistrationNumber().equals(registration)) {
                slot.removeCar();
                outputArea.setText("Car with registration " + registration + " removed from slot " + slotId);
                return;
            }
        }
        outputArea.setText("Car with registration " + registration + " not found in any slot.");
    }

    /**
     * The main method
     * 
     * @param args The command-line arguments
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new ParkingSlotGUI());
    }
}