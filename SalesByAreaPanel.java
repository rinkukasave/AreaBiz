package areabusiness;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class SalesByAreaPanel extends JPanel {

    private BusinessManager manager;

    public SalesByAreaPanel(BusinessManager manager) {

        this.manager = manager;

        setLayout(new BorderLayout());
        setBackground(new Color(245, 247, 250));

        JLabel title = new JLabel("Sales by Area");
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

        add(new SalesChart(manager), BorderLayout.CENTER);
    }

    // Custom chart
    class SalesChart extends JPanel {

        private BusinessManager manager;

        public SalesChart(BusinessManager manager) {
            this.manager = manager;
            setBackground(Color.WHITE);
        }

        @Override
        protected void paintComponent(Graphics g) {

            super.paintComponent(g);

            Graphics2D g2 =
                    (Graphics2D) g.create();

            g2.setRenderingHint(
                    RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_ON
            );

            HashMap<String, Double> salesByArea =
                    new HashMap<>();

            for (Business business :
                    manager.getAllBusinesses()) {

                String area =
                        business.getArea();

                double sales =
                        business.getMonthlySales();

                salesByArea.put(
                        area,
                        salesByArea.getOrDefault(area, 0.0)
                                + sales
                );
            }

            if (salesByArea.isEmpty()) {

                g2.setFont(
                        new Font("Arial", Font.BOLD, 18)
                );

                g2.drawString(
                        "No sales data available.",
                        250,
                        200
                );

                g2.dispose();
                return;
            }

            int width = getWidth();
            int height = getHeight();

            int leftMargin = 100;
            int bottomMargin = 80;
            int topMargin = 50;

            double maxSales = 0;

            for (double sales :
                    salesByArea.values()) {

                if (sales > maxSales) {
                    maxSales = sales;
                }
            }

            int chartHeight =
                    height - topMargin - bottomMargin;

            int barWidth = 70;

            int gap = 50;

            int x = leftMargin;

            // Y axis
            g2.drawLine(
                    leftMargin,
                    topMargin,
                    leftMargin,
                    height - bottomMargin
            );

            // X axis
            g2.drawLine(
                    leftMargin,
                    height - bottomMargin,
                    width - 40,
                    height - bottomMargin
            );

            ArrayList<Map.Entry<String, Double>>
                    entries =
                    new ArrayList<>(
                            salesByArea.entrySet()
                    );

            for (Map.Entry<String, Double> entry :
                    entries) {

                double sales = entry.getValue();

                int barHeight =
                        (int) (
                                (sales / maxSales)
                                * chartHeight
                        );

                int y =
                        height
                        - bottomMargin
                        - barHeight;

                g2.setColor(
                        new Color(35, 120, 210)
                );

                g2.fillRect(
                        x,
                        y,
                        barWidth,
                        barHeight
                );

                g2.setColor(
                        new Color(30, 40, 60)
                );

                g2.setFont(
                        new Font(
                                "Arial",
                                Font.BOLD,
                                12
                        )
                );

                // Area name
                g2.drawString(
                        entry.getKey(),
                        x,
                        height - 50
                );

                // Sales value
                String salesText =
                        "₹"
                        + String.format(
                                "%.0f",
                                sales
                        );

                g2.drawString(
                        salesText,
                        x,
                        y - 10
                );

                x += barWidth + gap;
            }

            g2.dispose();
        }
    }
}