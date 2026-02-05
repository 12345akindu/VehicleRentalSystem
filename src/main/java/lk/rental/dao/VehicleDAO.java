package lk.rental.dao;

import lk.rental.db.DBConnection;
import lk.rental.model.Bike;
import lk.rental.model.Car;
import lk.rental.model.Van;
import lk.rental.model.Vehicle;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class VehicleDAO {

    public boolean existsById(String vehicleId) throws SQLException {
        String sql = "SELECT vehicle_id FROM vehicles WHERE vehicle_id=?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, vehicleId);
            ResultSet rs = ps.executeQuery();
            return rs.next();
        }
    }

    public void insertVehicle(Vehicle v) throws SQLException {
        String sql = "INSERT INTO vehicles(vehicle_id,type,brand,model,base_rate,is_available,seats,engine_cc,cargo_capacity) " +
                "VALUES (?,?,?,?,?,?,?,?,?)";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, v.getVehicleId());
            ps.setString(3, v.getBrand());
            ps.setString(4, v.getModel());
            ps.setDouble(5, v.getBaseRatePerDay());
            ps.setBoolean(6, v.isAvailable());

            if (v instanceof Car) {
                Car c = (Car) v;
                ps.setString(2, "CAR");
                ps.setInt(7, c.getNumberOfSeats());
                ps.setNull(8, Types.INTEGER);
                ps.setNull(9, Types.DOUBLE);

            } else if (v instanceof Bike) {
                Bike b = (Bike) v;
                ps.setString(2, "BIKE");
                ps.setNull(7, Types.INTEGER);
                ps.setInt(8, b.getEngineCapacityCC());
                ps.setNull(9, Types.DOUBLE);

            } else if (v instanceof Van) {
                Van vanObj = (Van) v;
                ps.setString(2, "VAN");
                ps.setNull(7, Types.INTEGER);
                ps.setNull(8, Types.INTEGER);
                ps.setDouble(9, vanObj.getCargoCapacityKg());

            } else {
                throw new IllegalArgumentException("Unknown Vehicle subclass");
            }

            ps.executeUpdate();
        }
    }

    public List<Vehicle> getAllVehicles() throws SQLException {
        List<Vehicle> list = new ArrayList<>();
        String sql = "SELECT * FROM vehicles";

        try (Connection con = DBConnection.getConnection();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                list.add(mapRowToVehicle(rs));
            }
        }
        return list;
    }

    public Vehicle findById(String vehicleId) throws SQLException {
        String sql = "SELECT * FROM vehicles WHERE vehicle_id=?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, vehicleId);
            ResultSet rs = ps.executeQuery();
            if (!rs.next()) return null;

            return mapRowToVehicle(rs);
        }
    }

    public void updateAvailability(String vehicleId, boolean isAvailable) throws SQLException {
        String sql = "UPDATE vehicles SET is_available=? WHERE vehicle_id=?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setBoolean(1, isAvailable);
            ps.setString(2, vehicleId);
            ps.executeUpdate();
        }
    }

    private Vehicle mapRowToVehicle(ResultSet rs) throws SQLException {
        String id = rs.getString("vehicle_id");
        String type = rs.getString("type");
        String brand = rs.getString("brand");
        String model = rs.getString("model");
        double baseRate = rs.getDouble("base_rate");
        boolean available = rs.getBoolean("is_available");

        if ("CAR".equalsIgnoreCase(type)) {
            int seats = rs.getInt("seats");
            return new Car(id, brand, model, baseRate, available, seats);

        } else if ("BIKE".equalsIgnoreCase(type)) {
            int cc = rs.getInt("engine_cc");
            return new Bike(id, brand, model, baseRate, available, cc);

        } else if ("VAN".equalsIgnoreCase(type)) {
            double cargo = rs.getDouble("cargo_capacity");
            return new Van(id, brand, model, baseRate, available, cargo);

        } else {
            throw new IllegalStateException("Invalid type in DB: " + type);
        }
    }
}
