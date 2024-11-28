package GUI_Proj;

import javax.swing.*;
import java.awt.*;

public class mainGUI_2 {
    /*
    public static void main(String[] args) {
        // Create a new instance of the main GUI window
        MainGUI mainGUI = new MainGUI();
        mainGUI.setVisible(true);package GUI_Proj;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.sql.Connection;
import java.sql.SQLException;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;


public class MainGUI {
    private JFrame frame;
    private CardLayout cardLayout;
    private JPanel mainPanel;
    private Connection connection;

    public MainGUI() {
        frame = new JFrame("Publication Database System");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);

        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);

        // Add panels for each screen
        mainPanel.add(createDatabaseConnectionPanel(), "DatabaseConnection");
        mainPanel.add(createSQLFileUploadPanel(), "SQLFileUpload");
        mainPanel.add(createMainMenuPanel(), "MainMenu");

        frame.add(mainPanel);
        frame.setVisible(true);

        // Start with the database connection screen
        cardLayout.show(mainPanel, "DatabaseConnection");
    }

    private JPanel createDatabaseConnectionPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
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
                connection = DatabaseConnector.connect(username, password);
                JOptionPane.showMessageDialog(frame, "Connection successful!");
                cardLayout.show(mainPanel, "SQLFileUpload");
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(frame, "Connection failed: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
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

        return panel;
    }





    private JPanel createSQLFileUploadPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        JLabel instructionLabel = new JLabel("Please upload the SQL file to execute.", SwingConstants.CENTER);
        JButton uploadButton = new JButton("Upload SQL File");

        uploadButton.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            int returnValue = fileChooser.showOpenDialog(frame);
            if (returnValue == JFileChooser.APPROVE_OPTION) {
                File selectedFile = fileChooser.getSelectedFile();

                // Create a loading dialog
                JDialog loadingDialog = new JDialog(frame, "Executing SQL", true);
                loadingDialog.setSize(300, 150);
                loadingDialog.setLocationRelativeTo(frame);
                loadingDialog.setLayout(new BorderLayout());
                JLabel loadingLabel = new JLabel("Executing SQL file, please wait...", SwingConstants.CENTER);
                loadingDialog.add(loadingLabel, BorderLayout.CENTER);

                // Add an indeterminate progress bar
                JProgressBar progressBar = new JProgressBar();
                progressBar.setIndeterminate(true);
                loadingDialog.add(progressBar, BorderLayout.SOUTH);

                // Run SQL execution in a background thread
                SwingWorker<Void, Void> worker = new SwingWorker<>() {
                    @Override
                    protected Void doInBackground() throws Exception {
                        try {
                            // Execute the SQL file
                            SQLFileExecutor.executeSQLFile(connection, selectedFile.getAbsolutePath());
                        } catch (SQLException ex) {
                            throw ex; // Propagate exception to handle it later
                        }
                        return null;
                    }

                    @Override
                    protected void done() {
                        loadingDialog.dispose(); // Close the loading dialog
                        try {
                            get(); // Check for exceptions
                            JOptionPane.showMessageDialog(frame, "SQL File executed successfully!");
                            cardLayout.show(mainPanel, "MainMenu");
                        } catch (Exception ex) {
                            JOptionPane.showMessageDialog(frame, "SQL Execution failed: " + ex.getMessage(),
                                    "Error", JOptionPane.ERROR_MESSAGE);
                        }
                    }
                };

                // Show the loading dialog and execute the worker
                worker.execute();
                loadingDialog.setVisible(true);
            }
        });

        panel.add(instructionLabel, BorderLayout.CENTER);
        panel.add(uploadButton, BorderLayout.SOUTH);

        return panel;
    }


    private JPanel createMainMenuPanel() {
        JPanel panel = new JPanel(new GridLayout(4, 1, 10, 10));
        JButton viewTablesButton = new JButton("View Tables");
        JButton searchByIdButton = new JButton("Search by PUBLICATIONID");
        JButton searchByAttributesButton = new JButton("Search by Attributes");
        JButton exitButton = new JButton("Exit");

        viewTablesButton.addActionListener(e -> createSubmenu("View Tables"));
        searchByIdButton.addActionListener(e -> createSubmenu("Search by PUBLICATIONID"));
        searchByAttributesButton.addActionListener(e -> createSubmenu("Search by Attributes"));
        exitButton.addActionListener(e -> System.exit(0));

        panel.add(viewTablesButton);
        panel.add(searchByIdButton);
        panel.add(searchByAttributesButton);
        panel.add(exitButton);

        return panel;
    }

    private void createSubmenu(String option) {
        JPanel submenuPanel = new JPanel(new BorderLayout());
        JLabel label = new JLabel("You selected: " + option, SwingConstants.CENTER);
        submenuPanel.add(label, BorderLayout.NORTH);

        JPanel centerPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        switch (option) {
            case "View Tables":
                addViewTableOptions(centerPanel, gbc);
                break;
            case "Search by PUBLICATIONID":
                addSearchByIdOptions(centerPanel, gbc);
                break;
            case "Search by Attributes":
                addSearchByAttributesOptions(centerPanel, gbc);
                break;
        }

        submenuPanel.add(centerPanel, BorderLayout.CENTER);

        JButton backButton = new JButton("Back");
        backButton.addActionListener(e -> cardLayout.show(mainPanel, "MainMenu"));
        JPanel backButtonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        backButtonPanel.add(backButton);
        submenuPanel.add(backButtonPanel, BorderLayout.SOUTH);

        mainPanel.add(submenuPanel, "Submenu");
        cardLayout.show(mainPanel, "Submenu");
    }

    private void addViewTableOptions(JPanel panel, GridBagConstraints gbc) {
        JCheckBox publicationsBox = new JCheckBox("PUBLICATIONS");
        JCheckBox authorsBox = new JCheckBox("AUTHORS");
        JButton submitButton = new JButton("Submit");

        submitButton.addActionListener(e -> {
            if (!publicationsBox.isSelected() && !authorsBox.isSelected()) {
                JOptionPane.showMessageDialog(frame, "Please select at least one table.");
                return;
            }

            if (publicationsBox.isSelected()) {
                Queries.displayTableData(connection, "SELECT * FROM PUBLICATIONS", "PUBLICATIONS");
            }

            if (authorsBox.isSelected()) {
                Queries.displayTableData(connection, "SELECT * FROM AUTHORS", "AUTHORS");
            }
        });

        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(publicationsBox, gbc);

        gbc.gridy = 1;
        panel.add(authorsBox, gbc);

        gbc.gridy = 2;
        panel.add(submitButton, gbc);
    }



    private void addSearchByIdOptions(JPanel panel, GridBagConstraints gbc) {
        JLabel idLabel = new JLabel("Enter PUBLICATIONID:");
        JTextField idField = new JTextField(15);
        JButton searchButton = new JButton("Search");

        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(idLabel, gbc);

        gbc.gridx = 1;
        panel.add(idField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 2;
        panel.add(searchButton, gbc);

        searchButton.addActionListener(e -> {
            String publicationId = idField.getText().trim();
            if (publicationId.isEmpty()) {
                JOptionPane.showMessageDialog(frame, "Publication ID cannot be empty.");
                return;
            }

            try {
                Queries.searchByPublicationID(connection, Integer.parseInt(publicationId));
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(frame, "Invalid Publication ID. Please enter a number.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
    }
/*
    private void addSearchByAttributesOptions(JPanel panel, GridBagConstraints gbc) {
        JLabel authorLabel = new JLabel("Author:");
        JTextField authorField = new JTextField(15);
        JLabel titleLabel = new JLabel("Title:");
        JTextField titleField = new JTextField(15);
        JLabel yearLabel = new JLabel("Year:");
        JTextField yearField = new JTextField(15);
        JLabel typeLabel = new JLabel("Type:");
        JTextField typeField = new JTextField(15);

        JButton searchButton = new JButton("Search");

        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(authorLabel, gbc);
        gbc.gridx = 1;
        panel.add(authorField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(titleLabel, gbc);
        gbc.gridx = 1;
        panel.add(titleField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        panel.add(yearLabel, gbc);
        gbc.gridx = 1;
        panel.add(yearField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        panel.add(typeLabel, gbc);
        gbc.gridx = 1;
        panel.add(typeField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        panel.add(searchButton, gbc);

        searchButton.addActionListener(e -> {
            Queries.searchByAttributes(connection, authorField.getText(), titleField.getText(),
                    yearField.getText(), typeField.getText(), "TITLE", true, true, true, true, true, true);
        });
    }
 */
/*
    private void addSearchByAttributesOptions(JPanel panel, GridBagConstraints gbc) {
        JLabel authorLabel = new JLabel("AUTHOR:(Leave blank to ignore)");
        JTextField authorField = new JTextField(15);

        JLabel titleLabel = new JLabel("TITLE:(Leave blank to ignore)");
        JTextField titleField = new JTextField(15);

        JLabel yearLabel = new JLabel("YEAR:(Leave blank to ignore)");
        JTextField yearField = new JTextField(15);

        JLabel typeLabel = new JLabel("TYPE:(Leave blank to ignore)");
        JTextField typeField = new JTextField(15);

        JLabel sortByLabel = new JLabel("Sort by:");
        JComboBox<String> sortByComboBox = new JComboBox<>(new String[]{"PUBLICATIONID", "AUTHOR", "TITLE", "YEAR", "TYPE"});

        JCheckBox includePublicationId = new JCheckBox("PUBLICATIONID");
        JCheckBox includeAuthor = new JCheckBox("AUTHOR");
        JCheckBox includeTitle = new JCheckBox("TITLE");
        JCheckBox includeYear = new JCheckBox("YEAR");
        JCheckBox includeType = new JCheckBox("TYPE");
        JCheckBox includeSummary = new JCheckBox("SUMMARY");

        JButton searchButton = new JButton("Search");
        searchButton.addActionListener(e -> {
            String author = authorField.getText().trim();
            String title = titleField.getText().trim();
            String year = yearField.getText().trim();
            String type = typeField.getText().trim();
            String sortBy = (String) sortByComboBox.getSelectedItem();

            Queries.searchByAttributes(connection, author, title, year, type, sortBy,
                    includePublicationId.isSelected(), includeAuthor.isSelected(), includeTitle.isSelected(),
                    includeYear.isSelected(), includeType.isSelected(), includeSummary.isSelected());
        });

        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(authorLabel, gbc);
        gbc.gridx = 1;
        panel.add(authorField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(titleLabel, gbc);
        gbc.gridx = 1;
        panel.add(titleField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        panel.add(yearLabel, gbc);
        gbc.gridx = 1;
        panel.add(yearField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        panel.add(typeLabel, gbc);
        gbc.gridx = 1;
        panel.add(typeField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        panel.add(sortByLabel, gbc);
        gbc.gridx = 1;
        panel.add(sortByComboBox, gbc);

        gbc.gridx = 0;
        gbc.gridy = 5;
        panel.add(includePublicationId, gbc);
        gbc.gridx = 1;
        panel.add(includeAuthor, gbc);

        gbc.gridx = 0;
        gbc.gridy = 6;
        panel.add(includeTitle, gbc);
        gbc.gridx = 1;
        panel.add(includeYear, gbc);

        gbc.gridx = 0;
        gbc.gridy = 7;
        panel.add(includeType, gbc);
        gbc.gridx = 1;
        panel.add(includeSummary, gbc);

        gbc.gridx = 0;
        gbc.gridy = 8;
        gbc.gridwidth = 2;
        panel.add(searchButton, gbc);
    }


    public static void main(String[] args) {
        SwingUtilities.invokeLater(MainGUI::new);
    }
}

    */

}
