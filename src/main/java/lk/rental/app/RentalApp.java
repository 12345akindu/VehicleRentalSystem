package lk.rental.app;

import lk.rental.dao.IncomeDAO;
import lk.rental.dao.VehicleDAO;
import lk.rental.model.Bike;
import lk.rental.model.Car;
import lk.rental.model.Van;
import lk.rental.model.Vehicle;
import lk.rental.service.RentalService;
import lk.rental.util.InputUtil;

public class RentalApp {

    public static void main(String[] args) {
        InputUtil input = new InputUtil();
        VehicleDAO vehicleDAO = new VehicleDAO();
        IncomeDAO incomeDAO = new IncomeDAO();
        RentalService service = new RentalService(vehicleDAO, incomeDAO);

        try {
            service.loadFromDatabase();
            System.out.println("✅ Data loaded from database successfully.");
        } catch (Exception e) {
            System.out.println("❌ Failed to load from DB: " + e.getMessage());
            System.out.println("   Check DB is running + application.properties config.");
        }

        while (true) {
            System.out.println("\n===== Vehicle Rental Management System =====");
            System.out.println("1. Add a Vehicle");
            System.out.println("2. View All Vehicles");
            System.out.println("3. Rent a Vehicle");
            System.out.println("4. Return a Vehicle");
            System.out.println("5. Search Vehicle by ID");
            System.out.println("6. View Total Rental Income");
            System.out.println("7. Exit");

            int choice = input.readMenuChoice(1, 7);

            try {
                switch (choice) {
                    case 1:
                        addVehicleFlow(input, service);
                        break;

                    case 2:
                        viewAllVehicles(service);
                        break;

                    case 3:
                        rentVehicleFlow(input, service);
                        break;

                    case 4:
                        returnVehicleFlow(input, service);
                        break;

                    case 5:
                        searchVehicleFlow(input, service);
                        break;

                    case 6:
                        System.out.printf("💰 Total Rental Income: %.2f%n", service.getTotalIncome());
                        break;

                    case 7:
                        System.out.println("👋 Exiting... Bye!");
                        return;

                    default:
                        System.out.println("❌ Invalid option.");
                }
            } catch (Exception e) {
                System.out.println("❌ Error: " + e.getMessage());
            }
        }
    }

    private static void addVehicleFlow(InputUtil input, RentalService service) throws Exception {
        System.out.println("\n--- Add Vehicle ---");
        System.out.println("1) Car  2) Bike  3) Van");
        int typeChoice = input.readMenuChoice(1, 3);

        String id = input.readString("Vehicle ID: ");
        String brand = input.readString("Brand: ");
        String model = input.readString("Model: ");
        double baseRate = input.readDouble("Base rate per day: ");

        Vehicle v;
        if (typeChoice == 1) {
            int seats = input.readInt("Number of seats: ");
            v = new Car(id, brand, model, baseRate, true, seats);
        } else if (typeChoice == 2) {
            int cc = input.readInt("Engine capacity (CC): ");
            v = new Bike(id, brand, model, baseRate, true, cc);
        } else {
            double cargo = input.readDouble("Cargo capacity (Kg): ");
            v = new Van(id, brand, model, baseRate, true, cargo);
        }

        boolean added = service.addVehicle(v);
        if (added) System.out.println("✅ Vehicle added successfully!");
        else System.out.println("❌ Vehicle ID already exists. Use a unique ID.");
    }

    private static void viewAllVehicles(RentalService service) {
        System.out.println("\n--- All Vehicles ---");
        if (service.getVehicles().isEmpty()) {
            System.out.println("No vehicles found.");
            return;
        }
        for (Vehicle v : service.getVehicles()) {
            v.displayDetails();
            System.out.println("----------------------------------");
        }
    }

    private static void rentVehicleFlow(InputUtil input, RentalService service) throws Exception {
        System.out.println("\n--- Rent Vehicle ---");
        String id = input.readString("Enter Vehicle ID: ");
        int days = input.readInt("Number of days: ");

        double cost = service.rentVehicle(id, days);
        System.out.printf("✅ Vehicle rented. Total cost: %.2f%n", cost);
    }

    private static void returnVehicleFlow(InputUtil input, RentalService service) throws Exception {
        System.out.println("\n--- Return Vehicle ---");
        String id = input.readString("Enter Vehicle ID: ");
        service.returnVehicle(id);
        System.out.println("✅ Vehicle returned successfully!");
    }

    private static void searchVehicleFlow(InputUtil input, RentalService service) {
        System.out.println("\n--- Search Vehicle ---");
        String id = input.readString("Enter Vehicle ID: ");
        Vehicle v = service.searchById(id);

        if (v == null) {
            System.out.println("❌ Vehicle not found.");
        } else {
            v.displayDetails();
        }
    }
}