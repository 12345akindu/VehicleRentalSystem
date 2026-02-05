package lk.rental.model;

public class Bike extends Vehicle {
    private int engineCapacityCC;

    public Bike(String vehicleId, String brand, String model, double baseRatePerDay, boolean isAvailable, int engineCapacityCC) {
        super(vehicleId, brand, model, baseRatePerDay, isAvailable);
        this.engineCapacityCC = engineCapacityCC;
    }

    public int getEngineCapacityCC() { return engineCapacityCC; }
    public void setEngineCapacityCC(int engineCapacityCC) { this.engineCapacityCC = engineCapacityCC; }

    @Override
    public double calculateRentalCost(int days) {
        return getBaseRatePerDay() * days + (engineCapacityCC * 0.5 * days);
    }

    @Override
    public void displayDetails() {
        super.displayDetails();
        System.out.println("Type: BIKE | Engine CC: " + engineCapacityCC);
    }
}