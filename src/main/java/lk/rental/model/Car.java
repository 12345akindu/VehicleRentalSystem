package lk.rental.model;

public class Car extends Vehicle {
    private int numberOfSeats;

    public Car(String vehicleId, String brand, String model, double baseRatePerDay, boolean isAvailable, int numberOfSeats) {
        super(vehicleId, brand, model, baseRatePerDay, isAvailable);
        this.numberOfSeats = numberOfSeats;
    }

    public int getNumberOfSeats() { return numberOfSeats; }
    public void setNumberOfSeats(int numberOfSeats) { this.numberOfSeats = numberOfSeats; }

    @Override
    public double calculateRentalCost(int days) {
        return getBaseRatePerDay() * days + (numberOfSeats * 200.0 * days);
    }

    @Override
    public void displayDetails() {
        super.displayDetails();
        System.out.println("Type: CAR | Seats: " + numberOfSeats);
    }
}