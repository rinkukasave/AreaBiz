
package areabusiness;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class DeleteBusinessPanel extends JPanel {

    private final BusinessManager manager;
    private final Runnable refreshCallback;
    private JTextField idField;

    private final Color BACKGROUND = new Color(245, 247, 251);
    private final Color NAVY = new Color(20, 32, 55);
    private final Color RED = new Color(210, 60, 65);
    private final Color TEXT = new Color(35, 47, 68);
    private final Color MUTED = new Color(110, 121, 140);
    private final Color BORDER = new Color(220, 227, 238);

    public DeleteBusinessPanel(BusinessManager manager) {
        this(manager, null);
    }

    public DeleteBusinessPanel(
            BusinessManager manager,
            Runnable refreshCallback) {

        this.manager = manager;
        this.refreshCallback = refreshCallback;

        setLayout(new BorderLayout(0, 20));
        setBackground(BACKGROUND);
        setBorder(new EmptyBorder(20, 25, 25, 25));

        JLabel title = new JLabel("Delete Business");
        title.setFont(new Font("Arial", Font.BOLD, 28));
        title.setForeground(NAVY);

        JLabel subtitle = new JLabel(
                "Remove a business record using its Business ID."
        );
        subtitle.setFont(new Font("Arial", Font.PLAIN, 14));
        subtitle.setForeground(MUTED);

        JPanel titlePanel = new JPanel();
        titlePanel.setLayout(
                new BoxLayout(titlePanel, BoxLayout.Y_AXIS));
        titlePanel.setBackground(BACKGROUND);
        titlePanel.add(title);
        titlePanel.add(Box.createVerticalStrut(7));
        titlePanel.add(subtitle);

        add(titlePanel, BorderLayout.NORTH);

        JPanel card = new JPanel();
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBackground(Color.WHITE);
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(BORDER, 1),
                new EmptyBorder(30, 35, 30, 35)
        ));

        JLabel cardTitle = new JLabel("Business Details");
        cardTitle.setFont(new Font("Arial", Font.BOLD, 20));
        cardTitle.setForeground(TEXT);
        cardTitle.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel idLabel = new JLabel("Business ID");
        idLabel.setFont(new Font("Arial", Font.BOLD, 14));
        idLabel.setForeground(TEXT);
        idLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        idField = new JTextField();
        idField.setFont(new Font("Arial", Font.PLAIN, 15));
        idField.setMaximumSize(
                new Dimension(Integer.MAX_VALUE, 42));
        idField.setPreferredSize(new Dimension(300, 42));
        idField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(BORDER, 1),
                new EmptyBorder(8, 12, 8, 12)
        ));
        idField.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel warning = new JLabel(
                "<html><body style='width: 300px'>"
                + "Warning: This action permanently removes the "
                + "selected business record."
                + "</body></html>"
        );
        warning.setFont(new Font("Arial", Font.PLAIN, 13));
        warning.setForeground(RED);
        warning.setAlignmentX(Component.LEFT_ALIGNMENT);

        JButton deleteButton = new JButton("Delete Business");
        deleteButton.setFont(new Font("Arial", Font.BOLD, 15));
        deleteButton.setForeground(Color.WHITE);
        deleteButton.setBackground(RED);
        deleteButton.setFocusPainted(false);
        deleteButton.setCursor(
                new Cursor(Cursor.HAND_CURSOR));
        deleteButton.setBorderPainted(false);
        deleteButton.setOpaque(true);
        deleteButton.setPreferredSize(
                new Dimension(190, 44));
        deleteButton.setMaximumSize(
                new Dimension(220, 44));
        deleteButton.setAlignmentX(Component.LEFT_ALIGNMENT);

        card.add(cardTitle);
        card.add(Box.createVerticalStrut(25));
        card.add(idLabel);
        card.add(Box.createVerticalStrut(8));
        card.add(idField);
        card.add(Box.createVerticalStrut(18));
        card.add(warning);
        card.add(Box.createVerticalStrut(25));
        card.add(deleteButton);

        JPanel cardHolder = new JPanel(new BorderLayout());
        cardHolder.setBackground(BACKGROUND);
        cardHolder.add(card, BorderLayout.NORTH);

        add(cardHolder, BorderLayout.CENTER);

        deleteButton.addActionListener(e -> deleteBusiness());
        idField.addActionListener(e -> deleteBusiness());
    }

    private void deleteBusiness() {

        String input = idField.getText().trim();

        if (input.isEmpty()) {
            JOptionPane.showMessageDialog(
                    this,
                    "Please enter a Business ID.",
                    "Missing Input",
                    JOptionPane.WARNING_MESSAGE
            );
            idField.requestFocusInWindow();
            return;
        }

        int id;

        try {
            id = Integer.parseInt(input);

            if (id <= 0) {
                JOptionPane.showMessageDialog(
                        this,
                        "Business ID must be a positive number.",
                        "Invalid Input",
                        JOptionPane.ERROR_MESSAGE
                );
                return;
            }

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(
                    this,
                    "Please enter a valid numeric Business ID.",
                    "Invalid Input",
                    JOptionPane.ERROR_MESSAGE
            );
            return;
        }

        int choice = JOptionPane.showConfirmDialog(
                this,
                "Are you sure you want to delete Business ID "
                        + id + "?\nThis action cannot be undone.",
                "Confirm Delete",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE
        );

        if (choice != JOptionPane.YES_OPTION) {
            return;
        }

        boolean deleted = manager.deleteBusiness(id);

        if (deleted) {

            DataManager.saveData(manager.getAllBusinesses());

            if (refreshCallback != null) {
                refreshCallback.run();
            }

            JOptionPane.showMessageDialog(
                    this,
                    "Business deleted successfully!",
                    "Success",
                    JOptionPane.INFORMATION_MESSAGE
            );

            idField.setText("");
            idField.requestFocusInWindow();

        } else {
            JOptionPane.showMessageDialog(
                    this,
                    "Business ID not found.",
                    "Not Found",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }
}