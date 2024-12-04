package GUI_Proj;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnector {

    public static Connection connect(String username, String password) throws SQLException {
        String driverPrefixURL = "jdbc:oracle:thin:@";
        String jdbcUrl = "artemis.vsnet.gmu.edu:1521/vse18c.vsnet.gmu.edu";

        try {
            DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
        } catch (SQLException e) {
            throw new SQLException("Failed to load Oracle JDBC driver.");
        }

        Connection connection = DriverManager.getConnection(driverPrefixURL + jdbcUrl, username, password);

        // Print database information in console
        printDatabaseInfo(connection);

        return connection;
    }

    private static void printDatabaseInfo(Connection connection) {
        try {
            DatabaseMetaData metaData = connection.getMetaData();
            System.out.println("Connected.");
            System.out.println("Database Product Name: " + metaData.getDatabaseProductName());
            System.out.println("Database Product Version: " + metaData.getDatabaseProductVersion());
            System.out.println("Database Driver Name: " + metaData.getDriverName());
            System.out.println("Database Driver Version: " + metaData.getDriverVersion());
        } catch (SQLException e) {
            System.err.println("Failed to retrieve database metadata: " + e.getMessage());
        }
    }
}
