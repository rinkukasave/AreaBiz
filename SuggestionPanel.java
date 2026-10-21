
package areabusiness;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class SuggestionPanel extends JPanel {

    private JComboBox<String> areaTypeBox;
    private JTextField budgetField;
    private JComboBox<String> competitionBox;

    private JLabel resultLabel;
    private JLabel scoreLabel;
    private JLabel descriptionLabel;

    private final Color BACKGROUND = new Color(245, 247, 251);
    private final Color NAVY = new Color(20, 32, 55);
    private final Color BLUE = new Color(48, 112, 220);
    private final Color TEXT = new Color(35, 47, 68);
    private final Color MUTED = new Color(110, 121, 140);
    private final Color BORDER = new Color(220, 227, 238);

    public SuggestionPanel() {

        setLayout(new BorderLayout(0, 18));
        setBackground(BACKGROUND);
        setBorder(new EmptyBorder(22, 25, 22, 25));

        // TITLE
        JLabel title = new JLabel("Smart Business Suggestions");
        title.setFont(new Font("SansSerif", Font.BOLD, 27));
        title.setForeground(NAVY);

        JLabel subtitle = new JLabel(
                "Find a business idea based on your area, budget and competition."
        );
        subtitle.setFont(new Font("SansSerif", Font.PLAIN, 13));
        subtitle.setForeground(MUTED);

        JPanel titlePanel = new JPanel();
        titlePanel.setLayout(new BoxLayout(titlePanel, BoxLayout.Y_AXIS));
        titlePanel.setOpaque(false);

        titlePanel.add(title);
        titlePanel.add(Box.createVerticalStrut(7));
        titlePanel.add(subtitle);

        add(titlePanel, BorderLayout.NORTH);

        // FORM CARD
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBackground(Color.WHITE);

        formPanel.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(BORDER),
                        new EmptyBorder(24, 25, 24, 25)
                )
        );

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 8, 10, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;

        // AREA TYPE
        areaTypeBox = new JComboBox<>(new String[]{
            "College",
            "Residential",
            "Market",
            "Railway Station",
            "Office",
            "Tourist",
            "Other"
        });

        // BUDGET
        budgetField = new JTextField();
        budgetField.setToolTipText("Enter your budget in rupees");

        // COMPETITION
        competitionBox = new JComboBox<>(new String[]{
            "Low",
            "Medium",
            "High"
        });

        styleCombo(areaTypeBox);
        styleCombo(competitionBox);
        styleField(budgetField);

        // FORM FIELDS
        addFormField(
                formPanel, gbc, 0, 0,
                "Area Type", areaTypeBox
        );

        addFormField(
                formPanel, gbc, 0, 1,
                "Available Budget (₹)", budgetField
        );

        addFormField(
                formPanel, gbc, 1, 0,
                "Competition Level", competitionBox
        );

        // BUTTONS
        JButton suggestButton = createButton(
                "Get Recommendation",
                BLUE,
                Color.WHITE
        );

        JButton resetButton = createButton(
                "Reset",
                new Color(238, 242, 248),
                TEXT
        );

        JPanel buttonPanel = new JPanel(
                new FlowLayout(FlowLayout.LEFT, 10, 8)
        );
        buttonPanel.setOpaque(false);

        buttonPanel.add(suggestButton);
        buttonPanel.add(resetButton);

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        gbc.weightx = 1.0;

        formPanel.add(buttonPanel, gbc);

        // RESULT LABEL
        resultLabel = new JLabel(
                "Your recommended business will appear here.",
                SwingConstants.CENTER
        );

        resultLabel.setFont(
                new Font("SansSerif", Font.BOLD, 19)
        );
        resultLabel.setForeground(BLUE);

        // SCORE LABEL
        scoreLabel = new JLabel(
                "Recommendation Score: --",
                SwingConstants.CENTER
        );

        scoreLabel.setFont(
                new Font("SansSerif", Font.BOLD, 15)
        );
        scoreLabel.setForeground(TEXT);

        // DESCRIPTION LABEL
        descriptionLabel = new JLabel(
                "Enter your details and click Get Recommendation.",
                SwingConstants.CENTER
        );

        descriptionLabel.setFont(
                new Font("SansSerif", Font.PLAIN, 12)
        );
        descriptionLabel.setForeground(MUTED);

        // RESULT CARD
        JPanel resultPanel = new JPanel();
        resultPanel.setLayout(
                new BoxLayout(resultPanel, BoxLayout.Y_AXIS)
        );
        resultPanel.setBackground(Color.WHITE);

        resultPanel.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(BORDER),
                        new EmptyBorder(24, 20, 24, 20)
                )
        );

        resultLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        scoreLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        descriptionLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        resultPanel.add(resultLabel);
        resultPanel.add(Box.createVerticalStrut(14));
        resultPanel.add(scoreLabel);
        resultPanel.add(Box.createVerticalStrut(8));
        resultPanel.add(descriptionLabel);

        // CENTER CONTENT
        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(
                new BoxLayout(contentPanel, BoxLayout.Y_AXIS)
        );
        contentPanel.setOpaque(false);

        formPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        resultPanel.setAlignmentX(Component.LEFT_ALIGNMENT);

        formPanel.setMaximumSize(
                new Dimension(Integer.MAX_VALUE, 300)
        );

        resultPanel.setMaximumSize(
                new Dimension(Integer.MAX_VALUE, 180)
        );

        contentPanel.add(formPanel);
        contentPanel.add(Box.createVerticalStrut(18));
        contentPanel.add(resultPanel);

        // SCROLL PANE
        JScrollPane scrollPane = new JScrollPane(contentPanel);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false);

        scrollPane.getVerticalScrollBar().setUnitIncrement(16);

        scrollPane.setHorizontalScrollBarPolicy(
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER
        );

        add(scrollPane, BorderLayout.CENTER);

        // BUTTON ACTIONS
        suggestButton.addActionListener(
                e -> generateSuggestion()
        );

        resetButton.addActionListener(e -> {
            areaTypeBox.setSelectedIndex(0);
            budgetField.setText("");
            competitionBox.setSelectedIndex(0);

            resultLabel.setText(
                    "Your recommended business will appear here."
            );

            scoreLabel.setText("Recommendation Score: --");

            descriptionLabel.setText(
                    "Enter your details and click Get Recommendation."
            );
        });

        budgetField.addActionListener(
                e -> generateSuggestion()
        );
    }

    // ADD FORM FIELD
    private void addFormField(
            JPanel panel,
            GridBagConstraints gbc,
            int row,
            int column,
            String labelText,
            JComponent component) {

        JPanel fieldPanel = new JPanel();

        fieldPanel.setLayout(
                new BoxLayout(fieldPanel, BoxLayout.Y_AXIS)
        );

        fieldPanel.setBackground(Color.WHITE);

        JLabel label = new JLabel(labelText);
        label.setFont(
                new Font("SansSerif", Font.BOLD, 13)
        );
        label.setForeground(TEXT);
        label.setAlignmentX(Component.LEFT_ALIGNMENT);

        component.setAlignmentX(Component.LEFT_ALIGNMENT);

        component.setMaximumSize(
                new Dimension(Integer.MAX_VALUE, 40)
        );

        fieldPanel.add(label);
        fieldPanel.add(Box.createVerticalStrut(8));
        fieldPanel.add(component);

        gbc.gridx = column;
        gbc.gridy = row;
        gbc.gridwidth = 1;

        panel.add(fieldPanel, gbc);
    }

    // STYLE COMBO BOX
    private void styleCombo(JComboBox<String> combo) {

        combo.setFont(
                new Font("SansSerif", Font.PLAIN, 13)
        );

        combo.setBackground(Color.WHITE);
        combo.setForeground(TEXT);

        combo.setPreferredSize(
                new Dimension(220, 38)
        );
    }

    // STYLE TEXT FIELD
    private void styleField(JTextField field) {

        field.setFont(
                new Font("SansSerif", Font.PLAIN, 13)
        );

        field.setForeground(TEXT);
        field.setBackground(Color.WHITE);
        field.setCaretColor(BLUE);

        field.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(BORDER),
                        new EmptyBorder(8, 10, 8, 10)
                )
        );

        field.setPreferredSize(
                new Dimension(220, 38)
        );
    }

    // CREATE BUTTON
    private JButton createButton(
            String text,
            Color background,
            Color foreground) {

        JButton button = new JButton(text);

        button.setFont(
                new Font("SansSerif", Font.BOLD, 13)
        );

        button.setForeground(foreground);
        button.setBackground(background);
        button.setFocusPainted(false);
        button.setOpaque(true);
        button.setBorderPainted(false);

        button.setBorder(
                new EmptyBorder(12, 18, 12, 18)
        );

        button.setCursor(
                new Cursor(Cursor.HAND_CURSOR)
        );

        return button;
    }

    // GENERATE RECOMMENDATION
    private void generateSuggestion() {

        String budgetText = budgetField.getText().trim();

        if (budgetText.isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please enter your budget.",
                    "Missing Budget",
                    JOptionPane.WARNING_MESSAGE
            );

            budgetField.requestFocusInWindow();
            return;
        }

        try {

            double budget = Double.parseDouble(budgetText);

            if (!Double.isFinite(budget) || budget <= 0) {

                JOptionPane.showMessageDialog(
                        this,
                        "Budget must be greater than zero.",
                        "Invalid Budget",
                        JOptionPane.WARNING_MESSAGE
                );

                budgetField.requestFocusInWindow();
                return;
            }

            String areaType =
                    (String) areaTypeBox.getSelectedItem();

            String competition =
                    (String) competitionBox.getSelectedItem();

            // GET BUSINESS SUGGESTION
            String suggestion =
                    Recommendation.getSuggestion(areaType, budget);

            if (suggestion == null || suggestion.trim().isEmpty()) {
                suggestion = "General Store / Food Outlet";
            }

            // CALCULATE ESTIMATED SCORE
            int score = 70;

            // BUDGET SCORE
            if (budget >= 200000) {
                score += 10;
            } else if (budget >= 100000) {
                score += 5;
            }

            // COMPETITION SCORE
            if ("Low".equals(competition)) {
                score += 10;
            } else if ("Medium".equals(competition)) {
                score += 5;
            }

            score = Math.min(score, 100);

            // DISPLAY RESULT
            resultLabel.setText(
                    "<html><div style='text-align:center;'>"
                    + "Recommended Business: "
                    + suggestion
                    + "</div></html>"
            );

            scoreLabel.setText(
                    "Recommendation Score: " + score + "%"
            );

            descriptionLabel.setText(
                    "Rule-based estimate, not a guaranteed success probability."
            );

        } catch (NumberFormatException ex) {

            JOptionPane.showMessageDialog(
                    this,
                    "Enter a valid budget, for example: 50000",
                    "Invalid Budget",
                    JOptionPane.ERROR_MESSAGE
            );

            budgetField.requestFocusInWindow();
        }
    }
}