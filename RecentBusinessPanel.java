package areabusiness;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class RecentBusinessPanel extends JPanel {

    private BusinessManager manager;
    private JTable table;
    private DefaultTableModel model;

    public RecentBusinessPanel(BusinessManager manager) {

        this.manager = manager;

        setLayout(new BorderLayout());
        setBackground(Color.WHITE);

        JLabel title = new JLabel("Recent Businesses");
        title.setFont(new Font("Arial", Font.BOLD, 20));
        title.setForeground(new Color(30, 40, 60));

        JPanel titlePanel = new JPanel(
                new FlowLayout(FlowLayout.LEFT, 15, 10)
        );
        titlePanel.setBackground(Color.WHITE);
        titlePanel.add(title);

        add(titlePanel, BorderLayout.NORTH);

        String[] columns = {
            "ID",
            "Business Name",
            "Area",
            "Category",
            "Monthly Sales"
        };

        model = new DefaultTableModel(columns, 0);

        table = new JTable(model);
        table.setRowHeight(28);
        table.setFont(new Font("Arial", Font.PLAIN, 13));

        table.getTableHeader().setFont(
                new Font("Arial", Font.BOLD, 13)
        );

        table.getTableHeader().setBackground(
                new Color(235, 240, 248)
        );

        JScrollPane scrollPane =
                new JScrollPane(table);

        add(scrollPane, BorderLayout.CENTER);

        refreshTable();
    }

    public void refreshTable() {

        model.setRowCount(0);

        java.util.ArrayList<Business> businesses =
                manager.getAllBusinesses();

        int start =
                Math.max(0, businesses.size() - 5);

        for (int i = businesses.size() - 1;
             i >= start;
             i--) {

            Business business = businesses.get(i);

            model.addRow(new Object[]{
                business.getBusinessId(),
                business.getBusinessName(),
                business.getArea(),
                business.getCategory(),
                "₹" + String.format(
                        "%.2f",
                        business.getMonthlySales()
                )
            });
        }
    }
}