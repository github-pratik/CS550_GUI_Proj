package GUI_Proj.panels;

import GUI_Proj.MainGUI;
import GUI_Proj.SQLFileExecutor;

import javax.swing.*;
import java.awt.*;
import java.io.File;

public class SQLFileUploadPanel {
    private JPanel panel;

    public SQLFileUploadPanel(MainGUI mainGUI) {
        panel = createPanel(mainGUI);
    }

    private JPanel createPanel(MainGUI mainGUI) {
        JPanel panel = new JPanel(new BorderLayout());
        JLabel instructionLabel = new JLabel("Please upload the SQL file to execute.", SwingConstants.CENTER);
        JButton uploadButton = new JButton("Upload SQL File");

        uploadButton.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            int returnValue = fileChooser.showOpenDialog(panel);
            if (returnValue == JFileChooser.APPROVE_OPTION) {
                File selectedFile = fileChooser.getSelectedFile();

                // Show loading animation
                JDialog loadingDialog = createLoadingDialog("Executing SQL file, please wait...");
                SwingWorker<Void, Void> worker = new SwingWorker<>() {
                    @Override
                    protected Void doInBackground() throws Exception {
                        SQLFileExecutor.executeSQLFile(mainGUI.getConnection(), selectedFile.getAbsolutePath());
                        return null;
                    }

                    @Override
                    protected void done() {
                        loadingDialog.dispose(); // Close the loading dialog
                        try {
                            get(); // Check for exceptions
                            JOptionPane.showMessageDialog(panel, "SQL File executed successfully!");
                            mainGUI.showCard("MainMenu");
                        } catch (Exception ex) {
                            JOptionPane.showMessageDialog(panel, "SQL Execution failed: " + ex.getMessage(),
                                    "Error", JOptionPane.ERROR_MESSAGE);
                        }
                    }
                };

                worker.execute();
                loadingDialog.setVisible(true); // Show dialog while worker executes
            }
        });

        panel.add(instructionLabel, BorderLayout.CENTER);
        panel.add(uploadButton, BorderLayout.SOUTH);

        return panel;
    }

    private JDialog createLoadingDialog(String message) {
        JDialog dialog = new JDialog((Frame) null, "Loading", true);
        dialog.setSize(300, 150);
        dialog.setLocationRelativeTo(panel);

        JLabel loadingLabel = new JLabel(message, SwingConstants.CENTER);
        JProgressBar progressBar = new JProgressBar();
        progressBar.setIndeterminate(true);

        dialog.setLayout(new BorderLayout());
        dialog.add(loadingLabel, BorderLayout.CENTER);
        dialog.add(progressBar, BorderLayout.SOUTH);

        return dialog;
    }

    public JPanel getPanel() {
        return panel;
    }
}
