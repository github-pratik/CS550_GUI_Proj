package GUI_Proj.panels;

import GUI_Proj.DatabaseConnector;
import GUI_Proj.MainGUI;

import javax.swing.*;
import java.awt.*;
import java.sql.Connection;

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
                JOptionPane.showMessageDialog(panel, "Connection successful!");
                mainGUI.showCard("SQLFileUpload");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(panel, "Failed to connect: " + ex.getMessage(),
                        "Error", JOptionPane.ERROR_MESSAGE);
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
        gbc.gridy = 2;
        gbc.gridx = 0;
        gbc.gridwidth = 2;
        panel.add(connectButton, gbc);
    }

    public JPanel getPanel() {
        return panel;
    }
}
