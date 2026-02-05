package lk.rental.service;

import lk.rental.dao.IncomeDAO;
import lk.rental.dao.VehicleDAO;
import lk.rental.model.Vehicle;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RentalService {

    private final VehicleDAO vehicleDAO;
    private final IncomeDAO incomeDAO;

    private final ArrayList<Vehicle> vehicles = new ArrayList<>();
    private double totalIncome = 0.0;

    public RentalService(VehicleDAO vehicleDAO, IncomeDAO incomeDAO) {
        this.vehicleDAO = vehicleDAO;
        this.incomeDAO = incomeDAO;
    }

    public void loadFromDatabase() throws SQLException {
        vehicles.clear();
        List<Vehicle> dbVehicles = vehicleDAO.getAllVehicles();
        vehicles.addAll(dbVehicles);
        totalIncome = incomeDAO.getTotalIncome();
    }

    public ArrayList<Vehicle> getVehicles() {
        return vehicles;
    }

    public double getTotalIncome() {
        return totalIncome;
    }

    public boolean addVehicle(Vehicle v) throws SQLException {
        if (vehicleDAO.existsById(v.getVehicleId())) {
            return false; // duplicate ID
        }
        vehicleDAO.insertVehicle(v);
        vehicles.add(v);
        return true;
    }

    public Vehicle searchById(String id) {
        for (Vehicle v : vehicles) {
            if (v.getVehicleId().equalsIgnoreCase(id)) return v;
        }
        return null;
    }

    public double rentVehicle(String id, int days) throws SQLException {
        if (days <= 0) throw new IllegalArgumentException("Rental days must be greater than zero.");

        Vehicle v = searchById(id);
        if (v == null) throw new IllegalArgumentException("Vehicle not found.");

        if (!v.isAvailable()) throw new IllegalStateException("Vehicle is already rented.");

        double cost = v.calculateRentalCost(days); // polymorphism
        v.rentVehicle();

        vehicleDAO.updateAvailability(id, false);

        totalIncome += cost;
        incomeDAO.updateTotalIncome(totalIncome);

        return cost;
    }

    public void returnVehicle(String id) throws SQLException {
        Vehicle v = searchById(id);
        if (v == null) throw new IllegalArgumentException("Vehicle not found.");

        if (v.isAvailable()) throw new IllegalStateException("Vehicle is not currently rented.");

        v.returnVehicle();
        vehicleDAO.updateAvailability(id, true);
    }
}
