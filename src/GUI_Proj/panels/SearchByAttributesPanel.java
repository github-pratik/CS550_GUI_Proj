package GUI_Proj.panels;

import GUI_Proj.MainGUI;
import GUI_Proj.Queries;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class SearchByAttributesPanel {
    private JPanel panel;

    public SearchByAttributesPanel(MainGUI mainGUI) {
        // Root panel using BorderLayout
        panel = new JPanel(new BorderLayout());

        // Title Label
        JLabel titleLabel = new JLabel("Search by Attributes", SwingConstants.CENTER);
        panel.add(titleLabel, BorderLayout.NORTH);

        // Center panel for input and output fields
        JPanel centerPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        // Input fields
        JLabel authorLabel = new JLabel("AUTHOR (leave blank to skip):");
        JTextField authorField = new JTextField(15);
        JLabel titleLabelInput = new JLabel("TITLE (leave blank to skip):");
        JTextField titleField = new JTextField(15);
        JLabel yearLabel = new JLabel("YEAR (leave blank to skip):");
        JTextField yearField = new JTextField(15);
        JLabel typeLabel = new JLabel("TYPE (leave blank to skip):");
        JTextField typeField = new JTextField(15);

        // Output field checkboxes
        JCheckBox outputPublicationId = new JCheckBox("PUBLICATIONID");
        JCheckBox outputAuthor = new JCheckBox("AUTHOR");
        JCheckBox outputTitle = new JCheckBox("TITLE");
        JCheckBox outputYear = new JCheckBox("YEAR");
        JCheckBox outputType = new JCheckBox("TYPE");
        JCheckBox outputSummary = new JCheckBox("SUMMARY");

        JButton searchButton = new JButton("Search");

        // Layout input fields
        gbc.gridx = 0;
        gbc.gridy = 0;
        centerPanel.add(authorLabel, gbc);
        gbc.gridx = 1;
        centerPanel.add(authorField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        centerPanel.add(titleLabelInput, gbc);
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

        // Layout output fields
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        centerPanel.add(new JLabel("Select Output Fields:"), gbc);

        gbc.gridwidth = 1;
        gbc.gridy = 5;
        centerPanel.add(outputPublicationId, gbc);
        gbc.gridx = 1;
        centerPanel.add(outputAuthor, gbc);

        gbc.gridx = 0;
        gbc.gridy = 6;
        centerPanel.add(outputTitle, gbc);
        gbc.gridx = 1;
        centerPanel.add(outputYear, gbc);

        gbc.gridx = 0;
        gbc.gridy = 7;
        centerPanel.add(outputType, gbc);
        gbc.gridx = 1;
        centerPanel.add(outputSummary, gbc);

        // Search button
        gbc.gridx = 0;
        gbc.gridy = 8;
        gbc.gridwidth = 2;
        centerPanel.add(searchButton, gbc);

        // Add center panel to the root panel
        panel.add(centerPanel, BorderLayout.CENTER);

        // Back button at the bottom
        JButton backButton = new JButton("Back");
        backButton.addActionListener(e -> mainGUI.showCard("MainMenu"));
        JPanel backButtonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        backButtonPanel.add(backButton);

        panel.add(backButtonPanel, BorderLayout.SOUTH);

        // Action listener for the Search button
        searchButton.addActionListener(e -> {
            // Collect input fields
            String author = authorField.getText().trim();
            String title = titleField.getText().trim();
            String year = yearField.getText().trim();
            String type = typeField.getText().trim();

            // Validate at least one input field
            if (author.isEmpty() && title.isEmpty() && year.isEmpty() && type.isEmpty()) {
                JOptionPane.showMessageDialog(panel, "You must provide at least one input field.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Collect selected output fields
            List<String> selectedOutputFields = new ArrayList<>();
            boolean includePublicationId = outputPublicationId.isSelected();
            boolean includeAuthor = outputAuthor.isSelected();
            boolean includeTitle = outputTitle.isSelected();
            boolean includeYear = outputYear.isSelected();
            boolean includeType = outputType.isSelected();
            boolean includeSummary = outputSummary.isSelected();

            if (includePublicationId) selectedOutputFields.add("PUBLICATIONID");
            if (includeAuthor) selectedOutputFields.add("AUTHOR");
            if (includeTitle) selectedOutputFields.add("TITLE");
            if (includeYear) selectedOutputFields.add("YEAR");
            if (includeType) selectedOutputFields.add("TYPE");
            if (includeSummary) selectedOutputFields.add("SUMMARY");

            // Validate at least one output field
            if (selectedOutputFields.isEmpty()) {
                JOptionPane.showMessageDialog(panel, "You must select at least one output field.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Sorting menu
            String[] sortingOptions = selectedOutputFields.toArray(new String[0]);
            String sortBy = (String) JOptionPane.showInputDialog(panel, "Choose a field to sort by:", "Sort By",
                    JOptionPane.QUESTION_MESSAGE, null, sortingOptions, sortingOptions[0]);

            if (sortBy == null) {
                JOptionPane.showMessageDialog(panel, "Sorting is mandatory. Please select a sorting option.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Execute the query
            try {
                Queries.searchByAttributes(
                        mainGUI.getConnection(),
                        author, title, year, type,
                        sortBy, includePublicationId, includeAuthor,
                        includeTitle, includeYear, includeType, includeSummary
                );
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(panel, "An error occurred: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
    }

    public JPanel getPanel() {
        return panel;
    }
}
