package GUI_Proj;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnector {

    public static Connection connect(String username, String password) throws SQLException {
        String url = "jdbc:oracle:thin:@artemis.vsnet.gmu.edu:1521/vse18c.vsnet.gmu.edu";
        DriverManager.registerDriver(new oracle.jdbc.OracleDriver());
        return DriverManager.getConnection(url, username, password);
    }
}
