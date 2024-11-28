package GUI_Proj.panels;

import GUI_Proj.MainGUI;
import GUI_Proj.Queries;

import javax.swing.*;
import java.awt.*;
import java.sql.ResultSet;
import java.sql.Statement;

public class ViewTablesPanel {
    private JPanel panel;

    public ViewTablesPanel(MainGUI mainGUI) {
        // Use BorderLayout for the root panel
        panel = new JPanel(new BorderLayout());

        // Title label
        JLabel titleLabel = new JLabel("Select tables to view:", SwingConstants.CENTER);
        panel.add(titleLabel, BorderLayout.NORTH);

        // Center panel for checkboxes
        JPanel centerPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        JCheckBox publicationsBox = new JCheckBox("PUBLICATIONS");
        JCheckBox authorsBox = new JCheckBox("AUTHORS");
        JButton submitButton = new JButton("Submit");

        submitButton.addActionListener(e -> {
            if (!publicationsBox.isSelected() && !authorsBox.isSelected()) {
                JOptionPane.showMessageDialog(panel, "Please select at least one table.");
                return;
            }

            try {
                if (publicationsBox.isSelected()) {
                    String query = "SELECT * FROM PUBLICATIONS";
                    Statement stmt = mainGUI.getConnection().createStatement();
                    ResultSet resultSet = stmt.executeQuery(query);
                    Queries.displayTableData(resultSet, "PUBLICATIONS");
                }
                if (authorsBox.isSelected()) {
                    String query = "SELECT * FROM AUTHORS";
                    Statement stmt = mainGUI.getConnection().createStatement();
                    ResultSet resultSet = stmt.executeQuery(query);
                    Queries.displayTableData(resultSet, "AUTHORS");
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(panel, "Error retrieving table data: " + ex.getMessage(),
                        "Error", JOptionPane.ERROR_MESSAGE);
            }

        });

        // Add components to the center panel
        gbc.gridx = 0;
        gbc.gridy = 0;
        centerPanel.add(publicationsBox, gbc);

        gbc.gridy = 1;
        centerPanel.add(authorsBox, gbc);

        gbc.gridy = 2;
        centerPanel.add(submitButton, gbc);

        // Add the center panel to the root panel
        panel.add(centerPanel, BorderLayout.CENTER);

        // Back button
        JButton backButton = new JButton("Back");
        backButton.addActionListener(e -> mainGUI.showCard("MainMenu"));
        JPanel backButtonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        backButtonPanel.add(backButton);

        // Add the back button panel to the root panel
        panel.add(backButtonPanel, BorderLayout.SOUTH);
    }

    public JPanel getPanel() {
        return panel;
    }
}
