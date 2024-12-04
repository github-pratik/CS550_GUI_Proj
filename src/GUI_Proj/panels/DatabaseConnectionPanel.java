package GUI_Proj.panels;

import GUI_Proj.DatabaseConnector;
import GUI_Proj.MainGUI;

import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.DatabaseMetaData;

public class DatabaseConnectionPanel {
    private JPanel panel;

    public DatabaseConnectionPanel(MainGUI mainGUI) {
        panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        JLabel usernameLabel = new JLabel("Username:");
        JTextField usernameField = new JTextField(20);
        JLabel passwordLabel = new JLabel("Password:");
        JPasswordField passwordField = new JPasswordField(20);
        JButton connectButton = new JButton("Connect");

        connectButton.addActionListener(e -> {
            String username = usernameField.getText();
            String password = new String(passwordField.getPassword());

            try {
                Connection connection = DatabaseConnector.connect(username, password);
                mainGUI.setConnection(connection);

                // Get and display database metadata
                DatabaseMetaData metaData = connection.getMetaData();
                String message = "Connected.\n"
                        + "Database Product Name: " + metaData.getDatabaseProductName() + "\n"
                        + "Database Product Version: " + metaData.getDatabaseProductVersion() + "\n"
                        + "Database Driver Name: " + metaData.getDriverName() + "\n"
                        + "Database Driver Version: " + metaData.getDriverVersion();
                JOptionPane.showMessageDialog(panel, message, "Database Connection Successful", JOptionPane.INFORMATION_MESSAGE);

                mainGUI.showCard("SQLFileUpload");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(panel, "Connection failed: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(usernameLabel, gbc);
        gbc.gridx = 1;
        panel.add(usernameField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(passwordLabel, gbc);
        gbc.gridx = 1;
        panel.add(passwordField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        panel.add(connectButton, gbc);
    }

    public JPanel getPanel() {
        return panel;
    }
}
