/*
package GUI_Proj.panels;

import GUI_Proj.MainGUI;
import GUI_Proj.Queries;

import javax.swing.*;
import java.awt.*;

public class SearchByAttributesPanel {
    private JPanel panel;

    public SearchByAttributesPanel(MainGUI mainGUI) {
        // Root panel using BorderLayout
        panel = new JPanel(new BorderLayout());

        // Title Label
        JLabel titleLabel = new JLabel("Search by Attributes", SwingConstants.CENTER);
        panel.add(titleLabel, BorderLayout.NORTH);

        // Center panel for form components
        JPanel centerPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        // Input fields
        JLabel authorLabel = new JLabel("AUTHOR (Leave blank to ignore):");
        JTextField authorField = new JTextField(15);

        JLabel titleLabelField = new JLabel("TITLE (Leave blank to ignore):");
        JTextField titleField = new JTextField(15);

        JLabel yearLabel = new JLabel("YEAR (Leave blank to ignore):");
        JTextField yearField = new JTextField(15);

        JLabel typeLabel = new JLabel("TYPE (Leave blank to ignore):");
        JTextField typeField = new JTextField(15);

        // Sort By ComboBox
        JLabel sortByLabel = new JLabel("Sort by:");
        JComboBox<String> sortByComboBox = new JComboBox<>(new String[]{"PUBLICATIONID", "AUTHOR", "TITLE", "YEAR", "TYPE"});

        // Checkboxes for fields to include in results
        JCheckBox includePublicationId = new JCheckBox("PUBLICATIONID");
        JCheckBox includeAuthor = new JCheckBox("AUTHOR");
        JCheckBox includeTitle = new JCheckBox("TITLE");
        JCheckBox includeYear = new JCheckBox("YEAR");
        JCheckBox includeType = new JCheckBox("TYPE");
        JCheckBox includeSummary = new JCheckBox("SUMMARY");

        // Search Button
        JButton searchButton = new JButton("Search");
        searchButton.addActionListener(e -> {
            // Get input values
            String author = authorField.getText().trim();
            String title = titleField.getText().trim();
            String year = yearField.getText().trim();
            String type = typeField.getText().trim();
            String sortBy = (String) sortByComboBox.getSelectedItem();

            try {
                // Call the Queries.searchByAttributes method
                Queries.searchByAttributes(mainGUI.getConnection(), author, title, year, type, sortBy,
                        includePublicationId.isSelected(), includeAuthor.isSelected(), includeTitle.isSelected(),
                        includeYear.isSelected(), includeType.isSelected(), includeSummary.isSelected());
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(panel, "Error during search: " + ex.getMessage(),
                        "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        // Adding components to the center panel
        gbc.gridx = 0;
        gbc.gridy = 0;
        centerPanel.add(authorLabel, gbc);
        gbc.gridx = 1;
        centerPanel.add(authorField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        centerPanel.add(titleLabelField, gbc);
        gbc.gridx = 1;
        centerPanel.add(titleField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        centerPanel.add(yearLabel, gbc);
        gbc.gridx = 1;
        centerPanel.add(yearField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        centerPanel.add(typeLabel, gbc);
        gbc.gridx = 1;
        centerPanel.add(typeField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        centerPanel.add(sortByLabel, gbc);
        gbc.gridx = 1;
        centerPanel.add(sortByComboBox, gbc);

        gbc.gridx = 0;
        gbc.gridy = 5;
        centerPanel.add(includePublicationId, gbc);
        gbc.gridx = 1;
        centerPanel.add(includeAuthor, gbc);

        gbc.gridx = 0;
        gbc.gridy = 6;
        centerPanel.add(includeTitle, gbc);
        gbc.gridx = 1;
        centerPanel.add(includeYear, gbc);

        gbc.gridx = 0;
        gbc.gridy = 7;
        centerPanel.add(includeType, gbc);
        gbc.gridx = 1;
        centerPanel.add(includeSummary, gbc);

        gbc.gridx = 0;
        gbc.gridy = 8;
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

 */