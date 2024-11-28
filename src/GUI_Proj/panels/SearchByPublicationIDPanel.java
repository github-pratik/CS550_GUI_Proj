package GUI_Proj.panels;

import GUI_Proj.MainGUI;
import GUI_Proj.Queries;

import javax.swing.*;
import java.awt.*;

public class SearchByPublicationIDPanel {
    private JPanel panel;

    public SearchByPublicationIDPanel(MainGUI mainGUI) {
        // Root panel using BorderLayout
        panel = new JPanel(new BorderLayout());

        // Title Label
        JLabel titleLabel = new JLabel("Search by PUBLICATIONID", SwingConstants.CENTER);
        panel.add(titleLabel, BorderLayout.NORTH);

        // Center panel for search components
        JPanel centerPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        JLabel idLabel = new JLabel("Enter PUBLICATIONID:");
        JTextField idField = new JTextField(15);
        JButton searchButton = new JButton("Search");

        // Action listener for the search button
        searchButton.addActionListener(e -> {
            String publicationId = idField.getText().trim();
            if (publicationId.isEmpty()) {
                JOptionPane.showMessageDialog(panel, "Publication ID cannot be empty.");
                return;
            }

            try {
                Queries.searchByPublicationID(mainGUI.getConnection(), Integer.parseInt(publicationId));
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(panel, "Invalid Publication ID. Please enter a number.",
                        "Error", JOptionPane.ERROR_MESSAGE);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(panel, "Error retrieving data: " + ex.getMessage(),
                        "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        // Adding components to the center panel
        gbc.gridx = 0;
        gbc.gridy = 0;
        centerPanel.add(idLabel, gbc);

        gbc.gridx = 1;
        centerPanel.add(idField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 2;
        centerPanel.add(searchButton, gbc);

        // Add the center panel to the root panel
        panel.add(centerPanel, BorderLayout.CENTER);

        // Back button at the bottom
        JButton backButton = new JButton("Back");
        backButton.addActionListener(e -> mainGUI.showCard("MainMenu"));
        JPanel backButtonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        backButtonPanel.add(backButton);

        panel.add(backButtonPanel, BorderLayout.SOUTH);
    }

    public JPanel getPanel() {
        return panel;
    }
}
