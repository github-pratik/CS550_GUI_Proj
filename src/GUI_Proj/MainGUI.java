
package GUI_Proj;


import GUI_Proj.panels.*;

import javax.swing.*;
import java.awt.*;
import java.sql.Connection;

public class MainGUI {
    private JFrame frame;
    private CardLayout cardLayout;
    private JPanel mainPanel;
    private Connection connection;

    public MainGUI() {
        frame = new JFrame("Publication Database System");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);

        // Initialize the CardLayout and main panel
        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);

        // Add panels for each screen
        mainPanel.add(new DatabaseConnectionPanel(this).getPanel(), "DatabaseConnection");
        mainPanel.add(new SQLFileUploadPanel(this).getPanel(), "SQLFileUpload");
        mainPanel.add(new MainMenuPanel(this).getPanel(), "MainMenu");
        mainPanel.add(new SearchByPublicationIDPanel(this).getPanel(), "SearchByPublicationID");
        //mainPanel.add(new SearchByAttributesPanel(this).getPanel(), "SearchByAttributes");
        mainPanel.add(new SearchByAttributesPanel(this).getPanel(), "SearchByAttributes");

        mainPanel.add(new ViewTablesPanel(this).getPanel(), "ViewTables");

        // Add the main panel to the frame
        frame.add(mainPanel);
        frame.setVisible(true);

        // Start with the Database Connection screen
        showCard("DatabaseConnection");
    }

    /**
     * Switch to a specific panel by card name.
     *
     * @param cardName The name of the card to show.
     */
    public void showCard(String cardName) {
        cardLayout.show(mainPanel, cardName);
    }

    /**
     * Set the database connection to be shared across panels.
     *
     * @param connection The active database connection.
     */
    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    /**
     * Get the shared database connection.
     *
     * @return The active database connection.
     */
    public Connection getConnection() {
        return connection;
    }

    public static void main(String[] args) {
        // Start the GUI application
        SwingUtilities.invokeLater(MainGUI::new);
    }
}
