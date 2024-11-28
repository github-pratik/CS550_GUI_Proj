package GUI_Proj;

import java.io.*;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class SQLFileExecutor {

    public static void executeSQLFile(Connection connection, String filePath) throws SQLException {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            StringBuilder sqlBuilder = new StringBuilder();
            String line;
            Statement statement = connection.createStatement();

            System.out.println("Uploading and executing SQL script...");
            int statementCount = 0; // Count SQL statements executed

            while ((line = reader.readLine()) != null) {
                // Skip comments or empty lines
                line = line.trim();
                if (line.isEmpty() || line.startsWith("--")) {
                    continue;
                }

                // Add the line to the current SQL command
                sqlBuilder.append(line).append(" ");

                // If the line ends with a semicolon, execute the SQL command
                if (line.endsWith(";")) {
                    String sql = sqlBuilder.toString().replace(";", "").trim(); // Remove semicolon
                    try {
                        statement.execute(sql);
                        System.out.println("Executed: " + sql); // Log successful execution
                    } catch (SQLException e) {
                        // Handle specific SQL errors (e.g., table does not exist)
                        System.err.println("Error executing SQL: " + sql);
                        System.err.println("Skipping this statement due to: " + e.getMessage());
                    }
                    sqlBuilder.setLength(0); // Reset for the next command
                }
            }

            System.out.println("SQL script executed successfully. Total statements executed: " + statementCount);
        } catch (IOException e) {
            throw new SQLException("Error reading SQL file: " + e.getMessage(), e);
        }
    }
}
