package lk.rental.model;

public class Van extends Vehicle {
    private double cargoCapacityKg;

    public Van(String vehicleId, String brand, String model, double baseRatePerDay, boolean isAvailable, double cargoCapacityKg) {
        super(vehicleId, brand, model, baseRatePerDay, isAvailable);
        this.cargoCapacityKg = cargoCapacityKg;
    }

    public double getCargoCapacityKg() { return cargoCapacityKg; }
    public void setCargoCapacityKg(double cargoCapacityKg) { this.cargoCapacityKg = cargoCapacityKg; }

    @Override
    public double calculateRentalCost(int days) {
        return getBaseRatePerDay() * days + (cargoCapacityKg * 0.2 * days);
    }

    @Override
    public void displayDetails() {
        super.displayDetails();
        System.out.println("Type: VAN | Cargo Capacity (kg): " + cargoCapacityKg);
    }
}