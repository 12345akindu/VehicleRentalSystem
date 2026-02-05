package lk.rental.dao;

import lk.rental.db.DBConnection;

import java.sql.*;

public class IncomeDAO {

    public double getTotalIncome() throws SQLException {
        String sql = "SELECT total_income FROM rental_income LIMIT 1";
        try (Connection con = DBConnection.getConnection();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            return rs.next() ? rs.getDouble(1) : 0.0;
        }
    }

    public void updateTotalIncome(double newTotal) throws SQLException {
        String sql = "UPDATE rental_income SET total_income=?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setDouble(1, newTotal);
            ps.executeUpdate();
        }
    }
}
