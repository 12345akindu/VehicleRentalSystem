package lk.rental.db;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DBConnection {

    private static String url;
    private static String user;
    private static String password;

    static {
        try {
            Properties props = new Properties();
            InputStream is = DBConnection.class.getClassLoader()
                    .getResourceAsStream("application.properties");

            if (is == null) {
                throw new RuntimeException("application.properties not found in src/main/resources");
            }

            props.load(is);

            url = props.getProperty("db.url");
            user = props.getProperty("db.user");
            password = props.getProperty("db.password");

            if (url == null || user == null || password == null) {
                throw new RuntimeException("DB config missing (db.url/db.user/db.password)");
            }

        } catch (Exception e) {
            throw new RuntimeException("Failed to load DB configuration", e);
        }
    }

    private DBConnection() {}

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url, user, password);
    }
}