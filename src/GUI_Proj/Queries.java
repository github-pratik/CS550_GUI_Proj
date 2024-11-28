package GUI_Proj;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.sql.*;

public class Queries {

    // Updated to accept a ResultSet
    public static void displayTableData(ResultSet resultSet, String title) throws SQLException {
        ResultSetMetaData metaData = resultSet.getMetaData();
        int columnCount = metaData.getColumnCount();

        DefaultTableModel model = new DefaultTableModel();
        for (int i = 1; i <= columnCount; i++) {
            model.addColumn(metaData.getColumnName(i));
        }

        while (resultSet.next()) {
            Object[] row = new Object[columnCount];
            for (int i = 1; i <= columnCount; i++) {
                row[i - 1] = resultSet.getString(i);
            }
            model.addRow(row);
        }

        JTable table = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(table);
        JFrame frame = new JFrame(title);
        frame.setSize(800, 600);
        frame.add(scrollPane);
        frame.setVisible(true);
    }

    public static void searchByPublicationID(Connection connection, int publicationId) {
        String query = "SELECT * FROM PUBLICATIONS WHERE PUBLICATIONID = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setInt(1, publicationId);
            ResultSet resultSet = pstmt.executeQuery();
            displayTableData(resultSet, "Publication ID: " + publicationId);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error fetching data: " + e.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void searchByAttributes(Connection connection, String author, String title, String year,
                                          String type, String sortBy, boolean includePublicationId, boolean includeAuthor,
                                          boolean includeTitle, boolean includeYear, boolean includeType,
                                          boolean includeSummary) {
        try {
            StringBuilder selectClause = new StringBuilder("SELECT ");
            if (includePublicationId) selectClause.append("PUBLICATIONS.PUBLICATIONID, ");
            if (includeAuthor) selectClause.append("AUTHORS.AUTHOR, ");
            if (includeTitle) selectClause.append("PUBLICATIONS.TITLE, ");
            if (includeYear) selectClause.append("PUBLICATIONS.YEAR, ");
            if (includeType) selectClause.append("PUBLICATIONS.TYPE, ");
            if (includeSummary) selectClause.append("PUBLICATIONS.SUMMARY, ");
            if (selectClause.toString().endsWith(", ")) {
                selectClause.setLength(selectClause.length() - 2); // Remove trailing comma
            }

            StringBuilder query = new StringBuilder(selectClause.toString());
            query.append(" FROM PUBLICATIONS LEFT JOIN AUTHORS ON PUBLICATIONS.PUBLICATIONID = AUTHORS.PUBLICATIONID WHERE 1=1");

            if (!author.isEmpty()) query.append(" AND AUTHORS.AUTHOR LIKE '%").append(author).append("%'");
            if (!title.isEmpty()) query.append(" AND PUBLICATIONS.TITLE LIKE '%").append(title).append("%'");
            if (!year.isEmpty()) query.append(" AND PUBLICATIONS.YEAR = '").append(year).append("'");
            if (!type.isEmpty()) query.append(" AND PUBLICATIONS.TYPE LIKE '%").append(type).append("%'");

            query.append(" ORDER BY ").append(sortBy).append(" ASC");

            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query.toString());

            if (!resultSet.isBeforeFirst()) {
                JOptionPane.showMessageDialog(null, "No results found for the given criteria.", "Information", JOptionPane.INFORMATION_MESSAGE);
                return;
            }

            displayTableData(resultSet, "Search Results");

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "An error occurred: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
