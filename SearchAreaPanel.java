

package areabusiness;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;
import java.util.ArrayList;
import java.util.Locale;

public class SearchAreaPanel extends JPanel {

    private BusinessManager manager;
    private JTextField areaField;
    private JTable table;
    private DefaultTableModel model;
    private JLabel countLabel;

    private final Color BACKGROUND = new Color(245, 247, 251);
    private final Color BLUE = new Color(48, 112, 220);
    private final Color TEXT = new Color(35, 47, 68);
    private final Color MUTED = new Color(110, 121, 140);
    private final Color BORDER = new Color(220, 227, 238);

    public SearchAreaPanel(BusinessManager manager) {

        this.manager = manager;

        setLayout(new BorderLayout(0, 16));
        setBackground(BACKGROUND);
        setBorder(BorderFactory.createEmptyBorder(22, 25, 22, 25));

        // HEADER
        JPanel header = new JPanel();
        header.setLayout(new BoxLayout(header, BoxLayout.Y_AXIS));
        header.setOpaque(false);

        JLabel title = new JLabel("Search by Area");
        title.setFont(new Font("SansSerif", Font.BOLD, 27));
        title.setForeground(TEXT);

        JLabel subtitle = new JLabel(
                "Find registered businesses in a particular location."
        );
        subtitle.setFont(new Font("SansSerif", Font.PLAIN, 13));
        subtitle.setForeground(MUTED);

        header.add(title);
        header.add(Box.createVerticalStrut(5));
        header.add(subtitle);

        add(header, BorderLayout.NORTH);

        // SEARCH CARD
        JPanel searchCard = new JPanel(new BorderLayout(10, 8));
        searchCard.setBackground(Color.WHITE);
        searchCard.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(BORDER),
                BorderFactory.createEmptyBorder(15, 16, 15, 16)
        ));

        JLabel areaLabel = new JLabel("Area / Location");
        areaLabel.setFont(new Font("SansSerif", Font.BOLD, 12));
        areaLabel.setForeground(TEXT);

        areaField = new JTextField();
        areaField.setFont(new Font("SansSerif", Font.PLAIN, 14));
        areaField.setPreferredSize(new Dimension(250, 38));
        areaField.setToolTipText("Enter an area name");
        areaField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(BORDER),
                BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));

        JButton searchButton = new JButton("Search Area");
        styleButton(searchButton, BLUE, Color.WHITE);

        JButton clearButton = new JButton("Clear");
        styleButton(clearButton, new Color(235, 239, 246), TEXT);

        JPanel inputRow = new JPanel(new BorderLayout(10, 0));
        inputRow.setOpaque(false);
        inputRow.add(areaField, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(
                new FlowLayout(FlowLayout.RIGHT, 8, 0)
        );
        buttonPanel.setOpaque(false);
        buttonPanel.add(clearButton);
        buttonPanel.add(searchButton);

        inputRow.add(buttonPanel, BorderLayout.EAST);

        searchCard.add(areaLabel, BorderLayout.NORTH);
        searchCard.add(inputRow, BorderLayout.CENTER);

        // TABLE
        String[] columns = {
            "ID", "Business Name", "Owner", "Type",
            "Area", "Contact", "Investment (₹)", "Monthly Sales (₹)"
        };

        model = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        table = new JTable(model);
        styleTable();

        JScrollPane tableScroll = new JScrollPane(table);
        tableScroll.setBorder(BorderFactory.createEmptyBorder());
        tableScroll.getViewport().setBackground(Color.WHITE);

        JLabel resultsTitle = new JLabel("Search Results");
        resultsTitle.setFont(new Font("SansSerif", Font.BOLD, 16));
        resultsTitle.setForeground(TEXT);

        countLabel = new JLabel("Enter an area to search.");
        countLabel.setFont(new Font("SansSerif", Font.PLAIN, 12));
        countLabel.setForeground(MUTED);

        JPanel tableHeader = new JPanel(new BorderLayout());
        tableHeader.setBackground(Color.WHITE);
        tableHeader.setBorder(
                BorderFactory.createEmptyBorder(13, 15, 13, 15)
        );
        tableHeader.add(resultsTitle, BorderLayout.WEST);
        tableHeader.add(countLabel, BorderLayout.EAST);

        JPanel tableCard = new JPanel(new BorderLayout());
        tableCard.setBackground(Color.WHITE);
        tableCard.setBorder(BorderFactory.createLineBorder(BORDER));
        tableCard.add(tableHeader, BorderLayout.NORTH);
        tableCard.add(tableScroll, BorderLayout.CENTER);

        JPanel center = new JPanel(new BorderLayout(0, 15));
        center.setOpaque(false);
        center.add(searchCard, BorderLayout.NORTH);
        center.add(tableCard, BorderLayout.CENTER);

        add(center, BorderLayout.CENTER);

        // ACTIONS
        searchButton.addActionListener(e -> searchArea());
        areaField.addActionListener(e -> searchArea());

        clearButton.addActionListener(e -> {
            areaField.setText("");
            model.setRowCount(0);
            countLabel.setText("Enter an area to search.");
            areaField.requestFocusInWindow();
        });
    }

    private void styleTable() {

        table.setFont(new Font("SansSerif", Font.PLAIN, 13));
        table.setRowHeight(32);
        table.setForeground(TEXT);
        table.setBackground(Color.WHITE);
        table.setSelectionBackground(new Color(222, 235, 255));
        table.setSelectionForeground(TEXT);
        table.setGridColor(new Color(235, 239, 245));
        table.setShowVerticalLines(false);
        table.setShowHorizontalLines(true);
        table.setFillsViewportHeight(true);
        table.setAutoCreateRowSorter(true);
        table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        JTableHeader header = table.getTableHeader();
        header.setFont(new Font("SansSerif", Font.BOLD, 12));
        header.setForeground(TEXT);
        header.setBackground(new Color(240, 244, 250));
        header.setPreferredSize(new Dimension(100, 38));
        header.setReorderingAllowed(false);

        int[] widths = {55, 165, 135, 120, 120, 125, 130, 145};

        for (int i = 0; i < widths.length; i++) {
            table.getColumnModel().getColumn(i)
                    .setPreferredWidth(widths[i]);
        }

        DefaultTableCellRenderer renderer =
                new DefaultTableCellRenderer() {

            @Override
            public Component getTableCellRendererComponent(
                    JTable tbl, Object value, boolean selected,
                    boolean focused, int row, int column) {

                Component component = super.getTableCellRendererComponent(
                        tbl, value, selected, focused, row, column
                );

                if (!selected) {
                    component.setBackground(
                            row % 2 == 0
                            ? Color.WHITE
                            : new Color(249, 251, 254)
                    );
                }

                setBorder(BorderFactory.createEmptyBorder(0, 8, 0, 8));
                return component;
            }
        };

        for (int i = 0; i < table.getColumnCount(); i++) {
            table.getColumnModel().getColumn(i)
                    .setCellRenderer(renderer);
        }
    }

    private void styleButton(
            JButton button, Color background, Color foreground) {

        button.setFont(new Font("SansSerif", Font.BOLD, 12));
        button.setBackground(background);
        button.setForeground(foreground);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setOpaque(true);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setPreferredSize(new Dimension(110, 38));
    }

    private void searchArea() {

        model.setRowCount(0);

        String area = areaField.getText().trim();

        if (area.isEmpty()) {
            countLabel.setText("Enter an area to search.");

            JOptionPane.showMessageDialog(
                    this,
                    "Please enter an area.",
                    "Missing Area",
                    JOptionPane.WARNING_MESSAGE
            );
            return;
        }

        ArrayList<Business> results = manager.searchByArea(area);

        if (results.isEmpty()) {
            countLabel.setText("No businesses found.");

            JOptionPane.showMessageDialog(
                    this,
                    "No businesses found in this area.",
                    "No Results",
                    JOptionPane.INFORMATION_MESSAGE
            );
            return;
        }

        for (Business business : results) {
            model.addRow(new Object[]{
                business.getBusinessId(),
                business.getBusinessName(),
                business.getOwnerName(),
                business.getBusinessType(),
                business.getArea(),
                business.getContact(),
                String.format(Locale.US, "%,.2f",
                        business.getInvestment()),
                String.format(Locale.US, "%,.2f",
                        business.getMonthlySales())
            });
        }

        countLabel.setText("Businesses found: " + results.size());
    }
}