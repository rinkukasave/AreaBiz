package areabusiness;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class ReportsPanel extends JPanel {

    private BusinessManager manager;

    public ReportsPanel(BusinessManager manager) {

        this.manager = manager;

        setLayout(new BorderLayout());
        setBackground(new Color(245, 247, 250));

        // Title
        JLabel title = new JLabel("Business Reports & Analysis");
        title.setFont(new Font("Arial", Font.BOLD, 28));
        title.setForeground(new Color(30, 40, 60));

        JPanel titlePanel = new JPanel(
                new FlowLayout(FlowLayout.LEFT, 25, 20)
        );

        titlePanel.setBackground(
                new Color(245, 247, 250)
        );

        titlePanel.add(title);
        add(titlePanel, BorderLayout.NORTH);

        // Report cards
        JPanel cardsPanel = new JPanel(
                new GridLayout(2, 2, 20, 20)
        );

        cardsPanel.setBackground(
                new Color(245, 247, 250)
        );

        cardsPanel.setBorder(
                BorderFactory.createEmptyBorder(
                        25, 30, 25, 30
                )
        );

        int totalBusinesses =
                manager.getBusinessCount();

        int totalAreas =
                manager.getAreaCount();

        double totalSales =
                manager.getTotalMonthlySales();

        double averageSales = 0;

        if (totalBusinesses > 0) {
            averageSales =
                    totalSales / totalBusinesses;
        }

        String topBusiness = "-";
        double highestSales = 0;

        ArrayList<Business> businesses =
                manager.getAllBusinesses();

        for (Business business : businesses) {

            if (business.getMonthlySales()
                    > highestSales) {

                highestSales =
                        business.getMonthlySales();

                topBusiness =
                        business.getBusinessName();
            }
        }

        cardsPanel.add(
                createCard(
                        "Total Businesses",
                        String.valueOf(totalBusinesses)
                )
        );

        cardsPanel.add(
                createCard(
                        "Total Areas",
                        String.valueOf(totalAreas)
                )
        );

        cardsPanel.add(
                createCard(
                        "Total Monthly Sales",
                        "₹" + String.format(
                                "%.2f",
                                totalSales
                        )
                )
        );

        cardsPanel.add(
                createCard(
                        "Average Monthly Sales",
                        "₹" + String.format(
                                "%.2f",
                                averageSales
                        )
                )
        );

        add(cardsPanel, BorderLayout.CENTER);

        // Top business section
        JPanel bottomPanel = new JPanel(
                new GridLayout(2, 1)
        );

        bottomPanel.setBackground(Color.WHITE);

        JLabel topLabel = new JLabel(
                "Highest Selling Business: "
                        + topBusiness,
                SwingConstants.CENTER
        );

        topLabel.setFont(
                new Font("Arial", Font.BOLD, 20)
        );

        topLabel.setForeground(
                new Color(30, 90, 160)
        );

        JLabel salesLabel = new JLabel(
                "Highest Monthly Sales: ₹"
                        + String.format(
                                "%.2f",
                                highestSales
                        ),
                SwingConstants.CENTER
        );

        salesLabel.setFont(
                new Font("Arial", Font.BOLD, 18)
        );

        bottomPanel.add(topLabel);
        bottomPanel.add(salesLabel);

        add(bottomPanel, BorderLayout.SOUTH);
    }

    private JPanel createCard(
            String title,
            String value) {

        JPanel card = new JPanel(
                new BorderLayout()
        );

        card.setBackground(Color.WHITE);

        card.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(
                                new Color(220, 225, 235)
                        ),
                        BorderFactory.createEmptyBorder(
                                20, 20, 20, 20
                        )
                )
        );

        JLabel titleLabel = new JLabel(title);

        titleLabel.setFont(
                new Font("Arial", Font.PLAIN, 14)
        );

        titleLabel.setForeground(
                new Color(100, 110, 125)
        );

        JLabel valueLabel = new JLabel(value);

        valueLabel.setFont(
                new Font("Arial", Font.BOLD, 25)
        );

        valueLabel.setForeground(
                new Color(30, 90, 160)
        );

        card.add(
                titleLabel,
                BorderLayout.NORTH
        );

        card.add(
                valueLabel,
                BorderLayout.CENTER
        );

        return card;
    }
}