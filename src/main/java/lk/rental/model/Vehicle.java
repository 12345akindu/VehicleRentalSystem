package lk.rental.model;

public abstract class Vehicle {
    private String vehicleId;
    private String brand;
    private String model;
    private double baseRatePerDay;
    private boolean isAvailable;

    public Vehicle(String vehicleId, String brand, String model, double baseRatePerDay, boolean isAvailable) {
        this.vehicleId = vehicleId;
        this.brand = brand;
        this.model = model;
        this.baseRatePerDay = baseRatePerDay;
        this.isAvailable = isAvailable;
    }

    public String getVehicleId() { return vehicleId; }
    public void setVehicleId(String vehicleId) { this.vehicleId = vehicleId; }

    public String getBrand() { return brand; }
    public void setBrand(String brand) { this.brand = brand; }

    public String getModel() { return model; }
    public void setModel(String model) { this.model = model; }

    public double getBaseRatePerDay() { return baseRatePerDay; }
    public void setBaseRatePerDay(double baseRatePerDay) { this.baseRatePerDay = baseRatePerDay; }

    public boolean isAvailable() { return isAvailable; }
    public void setAvailable(boolean available) { isAvailable = available; }

    public void displayDetails() {
        System.out.printf("ID: %s | %s %s | Base Rate/Day: %.2f | Available: %s%n",
                vehicleId, brand, model, baseRatePerDay, isAvailable ? "YES" : "NO");
    }

    public void rentVehicle() { this.isAvailable = false; }
    public void returnVehicle() { this.isAvailable = true; }

    public abstract double calculateRentalCost(int days);
}