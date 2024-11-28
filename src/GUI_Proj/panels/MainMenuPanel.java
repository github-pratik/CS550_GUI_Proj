package GUI_Proj.panels;

import GUI_Proj.MainGUI;

import javax.swing.*;
import java.awt.*;

public class MainMenuPanel {
    private JPanel panel;

    public MainMenuPanel(MainGUI mainGUI) {
        panel = new JPanel(new GridLayout(4, 1, 10, 10));
        JButton viewTablesButton = new JButton("View Tables");
        JButton searchByIdButton = new JButton("Search by PUBLICATIONID");
        JButton searchByAttributesButton = new JButton("Search by Attributes");
        JButton exitButton = new JButton("Exit");

        viewTablesButton.addActionListener(e -> mainGUI.showCard("ViewTables"));
        searchByIdButton.addActionListener(e -> mainGUI.showCard("SearchByPublicationID"));
        searchByAttributesButton.addActionListener(e -> mainGUI.showCard("SearchByAttributes"));
        exitButton.addActionListener(e -> System.exit(0));

        panel.add(viewTablesButton);
        panel.add(searchByIdButton);
        panel.add(searchByAttributesButton);
        panel.add(exitButton);
    }

    public JPanel getPanel() {
        return panel;
    }
}
