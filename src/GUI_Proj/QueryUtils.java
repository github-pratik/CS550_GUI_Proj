package GUI_Proj;

import javax.swing.*;
import java.awt.*;

public class QueryUtils {

    public static JDialog showLoadingDialog(String message, Component parent) {
        JDialog dialog = new JDialog((Frame) SwingUtilities.getWindowAncestor(parent), "Loading", true);
        dialog.setSize(300, 150);
        dialog.setLocationRelativeTo(parent);
        JLabel label = new JLabel(message, SwingConstants.CENTER);
        dialog.add(label);
        JProgressBar progressBar = new JProgressBar();
        progressBar.setIndeterminate(true);
        dialog.add(progressBar, BorderLayout.SOUTH);
        return dialog;
    }
}
