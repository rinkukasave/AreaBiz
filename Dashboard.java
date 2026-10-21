


package areabusiness;

import javax.swing.*;
import java.awt.*;
import java.beans.PropertyChangeListener;

public class Dashboard extends JFrame {

    private final BusinessManager manager;
    private PropertyChangeListener businessChangeListener;

    private final Color BACKGROUND = new Color(245, 247, 251);
    private final Color NAVY = new Color(20, 32, 55);
    private final Color BLUE = new Color(48, 112, 220);
    private final Color TEXT = new Color(35, 47, 68);
    private final Color MUTED = new Color(110, 121, 140);
    private final Color BORDER = new Color(225, 231, 240);

    public Dashboard() {

        manager = new BusinessManager();
        manager.getAllBusinesses().addAll(DataManager.loadData());

        setTitle("Area-Wise Smart Business Management");
        setSize(1250, 780);
        setMinimumSize(new Dimension(1050, 680));
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        buildDashboard();

        // Listen for add, update and delete events
        businessChangeListener = event -> SwingUtilities.invokeLater(
                this::refreshDashboard
        );

        manager.addChangeListener(businessChangeListener);
    }

    private void buildDashboard() {

        JPanel root = new JPanel(new BorderLayout());
        root.setBackground(BACKGROUND);

        // SIDEBAR
        JPanel sidebar = new JPanel();
        sidebar.setPreferredSize(new Dimension(235, 700));
        sidebar.setBackground(NAVY);
        sidebar.setLayout(new BoxLayout(sidebar, BoxLayout.Y_AXIS));
        sidebar.setBorder(
                BorderFactory.createEmptyBorder(20, 12, 20, 12));

        JLabel brand = new JLabel("  BUSINESS");
        brand.setFont(new Font("SansSerif", Font.BOLD, 23));
        brand.setForeground(Color.WHITE);
        brand.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel subtitle = new JLabel("  MANAGEMENT SYSTEM");
        subtitle.setFont(new Font("SansSerif", Font.BOLD, 11));
        subtitle.setForeground(new Color(130, 180, 255));
        subtitle.setAlignmentX(Component.LEFT_ALIGNMENT);

        sidebar.add(brand);
        sidebar.add(Box.createVerticalStrut(5));
        sidebar.add(subtitle);
        sidebar.add(Box.createVerticalStrut(35));

        JLabel menuTitle = new JLabel("  MAIN MENU");
        menuTitle.setFont(new Font("SansSerif", Font.BOLD, 11));
        menuTitle.setForeground(new Color(150, 164, 190));
        menuTitle.setAlignmentX(Component.LEFT_ALIGNMENT);

        sidebar.add(menuTitle);
        sidebar.add(Box.createVerticalStrut(12));

        String[] menuItems = {
            "Dashboard", "Add Business", "All Businesses",
            "Search Business", "Search by Area", "Suggestions",
            "Reports", "Update Business", "Delete Business"
        };

        for (String item : menuItems) {

            JButton button = new JButton(item);

            button.setFont(new Font("SansSerif", Font.BOLD, 13));
            button.setForeground(new Color(235, 241, 255));
            button.setBackground(item.equals("Dashboard")
                    ? BLUE : new Color(29, 45, 72));
            button.setFocusPainted(false);
            button.setBorderPainted(false);
            button.setOpaque(true);
            button.setHorizontalAlignment(SwingConstants.LEFT);
            button.setBorder(BorderFactory.createEmptyBorder(
                    10, 15, 10, 8));
            button.setMaximumSize(
                    new Dimension(Integer.MAX_VALUE, 43));
            button.setPreferredSize(new Dimension(210, 43));
            button.setAlignmentX(Component.LEFT_ALIGNMENT);
            button.setCursor(new Cursor(Cursor.HAND_CURSOR));

            button.addActionListener(e -> openSection(item));

            sidebar.add(button);
            sidebar.add(Box.createVerticalStrut(7));
        }

        sidebar.add(Box.createVerticalGlue());

        JLabel footer = new JLabel("  SMART BUSINESS • v1.0");
        footer.setFont(new Font("SansSerif", Font.PLAIN, 10));
        footer.setForeground(new Color(150, 164, 190));
        footer.setAlignmentX(Component.LEFT_ALIGNMENT);

        sidebar.add(footer);

        // MAIN CONTENT
        JPanel content = new JPanel(new BorderLayout(0, 18));
        content.setBackground(BACKGROUND);
        content.setBorder(BorderFactory.createEmptyBorder(
                22, 25, 22, 25));

        JPanel header = new JPanel(new BorderLayout());
        header.setOpaque(false);

        JPanel headingPanel = new JPanel();
        headingPanel.setOpaque(false);
        headingPanel.setLayout(
                new BoxLayout(headingPanel, BoxLayout.Y_AXIS));

        JLabel heading = new JLabel("Dashboard Overview");
        heading.setFont(new Font("SansSerif", Font.BOLD, 29));
        heading.setForeground(TEXT);

        JLabel description = new JLabel(
                "Monitor businesses, areas and monthly sales."
        );
        description.setFont(new Font("SansSerif", Font.PLAIN, 13));
        description.setForeground(MUTED);

        headingPanel.add(heading);
        headingPanel.add(Box.createVerticalStrut(5));
        headingPanel.add(description);

        JLabel dateLabel = new JLabel("BUSINESS INSIGHTS");
        dateLabel.setFont(new Font("SansSerif", Font.BOLD, 11));
        dateLabel.setForeground(BLUE);

        header.add(headingPanel, BorderLayout.WEST);
        header.add(dateLabel, BorderLayout.EAST);

        content.add(header, BorderLayout.NORTH);

        // STAT CARDS
        JPanel cards = new JPanel(new GridLayout(1, 4, 15, 15));
        cards.setOpaque(false);

        cards.add(createCard(
                "TOTAL BUSINESSES",
                String.valueOf(manager.getBusinessCount()),
                new Color(48, 112, 220)
        ));

        cards.add(createCard(
                "TOTAL AREAS",
                String.valueOf(manager.getAreaCount()),
                new Color(16, 155, 120)
        ));

        cards.add(createCard(
                "MONTHLY SALES",
                "₹" + String.format("%,.0f",
                        manager.getTotalMonthlySales()),
                new Color(225, 145, 40)
        ));

        cards.add(createCard(
                "TOP BUSINESS",
                getTopBusiness(),
                new Color(135, 90, 210)
        ));

        // LOWER PANELS
        JPanel lower = new JPanel(new GridLayout(1, 2, 16, 16));
        lower.setOpaque(false);

        JPanel chartCard = createSectionCard("Sales by Area");
        chartCard.add(
                new SalesByAreaPanel(manager),
                BorderLayout.CENTER);

        JPanel recentCard = createSectionCard("Recent Businesses");
        recentCard.add(
                new RecentBusinessPanel(manager),
                BorderLayout.CENTER);

        lower.add(chartCard);
        lower.add(recentCard);

        JPanel body = new JPanel(new BorderLayout(0, 18));
        body.setOpaque(false);
        body.add(cards, BorderLayout.NORTH);
        body.add(lower, BorderLayout.CENTER);

        content.add(body, BorderLayout.CENTER);

        root.add(sidebar, BorderLayout.WEST);
        root.add(content, BorderLayout.CENTER);

        setContentPane(root);
        revalidate();
        repaint();
    }

    // Refresh the complete dashboard
    public void refreshDashboard() {
        if (!SwingUtilities.isEventDispatchThread()) {
            SwingUtilities.invokeLater(this::refreshDashboard);
            return;
        }

        buildDashboard();
    }

    private JPanel createCard(
            String title,
            String value,
            Color accentColor) {

        JPanel card = new JPanel(new BorderLayout(0, 12));
        card.setBackground(Color.WHITE);
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(BORDER),
                BorderFactory.createEmptyBorder(17, 16, 17, 16)
        ));

        JPanel accent = new JPanel();
        accent.setBackground(accentColor);
        accent.setPreferredSize(new Dimension(5, 5));

        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 10));
        titleLabel.setForeground(MUTED);

        JLabel valueLabel = new JLabel(value);
        valueLabel.setFont(new Font("SansSerif", Font.BOLD, 23));
        valueLabel.setForeground(TEXT);
        valueLabel.setToolTipText(value);

        JPanel text = new JPanel(new BorderLayout(0, 12));
        text.setOpaque(false);
        text.add(titleLabel, BorderLayout.NORTH);
        text.add(valueLabel, BorderLayout.CENTER);

        card.add(accent, BorderLayout.WEST);
        card.add(text, BorderLayout.CENTER);

        return card;
    }

    private JPanel createSectionCard(String title) {

        JPanel panel = new JPanel(new BorderLayout(0, 10));
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(BORDER),
                BorderFactory.createEmptyBorder(12, 12, 12, 12)
        ));

        JLabel label = new JLabel(title);
        label.setFont(new Font("SansSerif", Font.BOLD, 16));
        label.setForeground(TEXT);

        panel.add(label, BorderLayout.NORTH);

        return panel;
    }

    private String getTopBusiness() {

        Business top = null;

        for (Business business : manager.getAllBusinesses()) {
            if (top == null
                    || business.getMonthlySales()
                    > top.getMonthlySales()) {
                top = business;
            }
        }

        if (top == null) {
            return "No data";
        }

        String name = top.getBusinessName();

        if (name.length() > 15) {
            name = name.substring(0, 13) + "...";
        }

        return name;
    }

    private void openSection(String item) {

        if (item.equals("Dashboard")) {
            refreshDashboard();
            return;
        }

        JFrame frame = new JFrame(item);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        switch (item) {

            case "Add Business":
                frame.add(new AddBusinessPanel(
                        manager, this::refreshDashboard));
                frame.setSize(900, 750);
                break;

            case "All Businesses":
                frame.add(new AllBusinessPanel(manager));
                frame.setSize(1200, 600);
                break;

            case "Search Business":
                frame.add(new SearchBusinessPanel(manager));
                frame.setSize(900, 600);
                break;

            case "Search by Area":
                frame.add(new SearchAreaPanel(manager));
                frame.setSize(900, 650);
                break;

            case "Suggestions":
                frame.add(new SuggestionPanel());
                frame.setSize(900, 750);
                break;

            case "Reports":
                frame.add(new ReportsPanel(manager));
                frame.setSize(900, 650);
                break;

            case "Update Business":
                frame.add(new UpdateBusinessPanel(manager));
                frame.setSize(850, 750);
                break;

            case "Delete Business":
                frame.add(new DeleteBusinessPanel(
                        manager, this::refreshDashboard));
                frame.setSize(650, 500);
                break;

            default:
                frame.dispose();
                return;
        }

        frame.setMinimumSize(new Dimension(650, 450));
        frame.setLocationRelativeTo(this);
        frame.setVisible(true);
    }
}