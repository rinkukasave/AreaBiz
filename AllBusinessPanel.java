
package areabusiness;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.util.Locale;

public class AllBusinessPanel extends JPanel {

    private BusinessManager manager;
    private JTable table;
    private DefaultTableModel model;
    private JTextField searchField;
    private JLabel countLabel;

    private final Color BACKGROUND = new Color(245, 247, 251);
    private final Color BLUE = new Color(48, 112, 220);
    private final Color TEXT = new Color(35, 47, 68);
    private final Color MUTED = new Color(110, 121, 140);
    private final Color BORDER = new Color(220, 227, 238);

    public AllBusinessPanel(BusinessManager manager) {

        this.manager = manager;

        setLayout(new BorderLayout(0, 15));
        setBackground(BACKGROUND);
        setBorder(BorderFactory.createEmptyBorder(22, 25, 22, 25));

        // HEADER
        JPanel header = new JPanel();
        header.setLayout(new BoxLayout(header, BoxLayout.Y_AXIS));
        header.setOpaque(false);

        JLabel title = new JLabel("All Businesses");
        title.setFont(new Font("SansSerif", Font.BOLD, 27));
        title.setForeground(TEXT);

        JLabel subtitle = new JLabel(
                "View and search all registered businesses."
        );
        subtitle.setFont(new Font("SansSerif", Font.PLAIN, 13));
        subtitle.setForeground(MUTED);

        header.add(title);
        header.add(Box.createVerticalStrut(5));
        header.add(subtitle);

        add(header, BorderLayout.NORTH);

        // TABLE COLUMNS
        String[] columns = {
            "ID",
            "Business Name",
            "Owner",
            "Type",
            "Area",
            "Contact",
            "Investment (₹)",
            "Monthly Sales (₹)",
            "Area Type",
            "Category"
        };

        model = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        table = new JTable(model);
        styleTable();

        // SEARCH BAR
        JPanel toolbar = new JPanel(new BorderLayout(12, 0));
        toolbar.setBackground(Color.WHITE);
        toolbar.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(BORDER),
                BorderFactory.createEmptyBorder(12, 14, 12, 14)
        ));

        JPanel searchPanel = new JPanel(new BorderLayout(8, 0));
        searchPanel.setOpaque(false);

        searchField = new JTextField();
        searchField.setFont(new Font("SansSerif", Font.PLAIN, 14));
        searchField.setPreferredSize(new Dimension(250, 38));
        searchField.setToolTipText(
                "Search by name, owner, area, type or category"
        );
        searchField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(BORDER),
                BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));

        JButton searchButton = new JButton("Search");
        styleButton(searchButton, BLUE, Color.WHITE);

        searchButton.addActionListener(e -> filterTable());
        searchField.addActionListener(e -> filterTable());

        searchPanel.add(searchField, BorderLayout.CENTER);
        searchPanel.add(searchButton, BorderLayout.EAST);

        JButton refreshButton = new JButton("Refresh");
        styleButton(refreshButton, new Color(235, 239, 246), TEXT);
        refreshButton.addActionListener(e -> {
            searchField.setText("");
            refreshTable();
        });

        toolbar.add(searchPanel, BorderLayout.CENTER);
        toolbar.add(refreshButton, BorderLayout.EAST);

        countLabel = new JLabel("Total businesses: 0");
        countLabel.setFont(new Font("SansSerif", Font.BOLD, 12));
        countLabel.setForeground(MUTED);

        JPanel tableHeading = new JPanel(new BorderLayout());
        tableHeading.setBackground(Color.WHITE);
        tableHeading.setBorder(
                BorderFactory.createEmptyBorder(12, 15, 12, 15)
        );
        tableHeading.add(new JLabel("Business Records"), BorderLayout.WEST);
        tableHeading.add(countLabel, BorderLayout.EAST);

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        scrollPane.getViewport().setBackground(Color.WHITE);

        JPanel tableCard = new JPanel(new BorderLayout());
        tableCard.setBackground(Color.WHITE);
        tableCard.setBorder(BorderFactory.createLineBorder(BORDER));
        tableCard.add(tableHeading, BorderLayout.NORTH);
        tableCard.add(scrollPane, BorderLayout.CENTER);

        JPanel center = new JPanel(new BorderLayout(0, 12));
        center.setOpaque(false);
        center.add(toolbar, BorderLayout.NORTH);
        center.add(tableCard, BorderLayout.CENTER);

        add(center, BorderLayout.CENTER);

        refreshTable();
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

        int[] widths = {
            55, 160, 130, 120, 120,
            125, 125, 140, 130, 120
        };

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
        button.setPreferredSize(new Dimension(105, 38));
    }

    // Load all businesses
    public void refreshTable() {

        model.setRowCount(0);

        for (Business business : manager.getAllBusinesses()) {
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
                        business.getMonthlySales()),
                business.getAreaType(),
                business.getCategory()
            });
        }

        countLabel.setText(
                "Total businesses: " + model.getRowCount()
        );
    }

    // Search across multiple columns
    private void filterTable() {

        String query = searchField.getText()
                .trim().toLowerCase(Locale.ROOT);

        model.setRowCount(0);

        for (Business business : manager.getAllBusinesses()) {

            boolean matches = query.isEmpty()
                    || String.valueOf(business.getBusinessId())
                            .contains(query)
                    || safe(business.getBusinessName()).contains(query)
                    || safe(business.getOwnerName()).contains(query)
                    || safe(business.getBusinessType()).contains(query)
                    || safe(business.getArea()).contains(query)
                    || safe(business.getContact()).contains(query)
                    || safe(business.getAreaType()).contains(query)
                    || safe(business.getCategory()).contains(query);

            if (matches) {
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
                            business.getMonthlySales()),
                    business.getAreaType(),
                    business.getCategory()
                });
            }
        }

        countLabel.setText(
                "Showing " + model.getRowCount() + " businesses"
        );
    }

    private String safe(String value) {
        return value == null
                ? ""
                : value.toLowerCase(Locale.ROOT);
    }
}