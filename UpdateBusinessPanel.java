
package areabusiness;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class UpdateBusinessPanel extends JPanel {

    private final BusinessManager manager;
    private final Runnable refreshCallback;

    private JTextField idField;
    private JTextField nameField;
    private JTextField ownerField;
    private JTextField typeField;
    private JTextField areaField;
    private JTextField contactField;
    private JTextField investmentField;
    private JTextField salesField;

    private JComboBox<String> areaTypeBox;
    private JComboBox<String> categoryBox;

    private final Color BACKGROUND = new Color(245, 247, 251);
    private final Color NAVY = new Color(20, 32, 55);
    private final Color BLUE = new Color(48, 112, 220);
    private final Color TEXT = new Color(35, 47, 68);
    private final Color MUTED = new Color(110, 121, 140);
    private final Color BORDER = new Color(220, 227, 238);

    public UpdateBusinessPanel(BusinessManager manager) {
        this(manager, null);
    }

    public UpdateBusinessPanel(
            BusinessManager manager,
            Runnable refreshCallback) {

        this.manager = manager;
        this.refreshCallback = refreshCallback;

        setLayout(new BorderLayout(0, 18));
        setBackground(BACKGROUND);
        setBorder(new EmptyBorder(22, 25, 22, 25));

        JLabel title = new JLabel("Update Business");
        title.setFont(new Font("SansSerif", Font.BOLD, 27));
        title.setForeground(NAVY);

        JLabel subtitle = new JLabel(
                "Enter the existing Business ID and update its details."
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

        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBackground(Color.WHITE);
        formPanel.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(BORDER),
                        new EmptyBorder(20, 22, 20, 22)
                )
        );

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 10, 8, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;

        idField = new JTextField();
        nameField = new JTextField();
        ownerField = new JTextField();
        typeField = new JTextField();
        areaField = new JTextField();
        contactField = new JTextField();
        investmentField = new JTextField();
        salesField = new JTextField();

        areaTypeBox = new JComboBox<>(new String[]{
            "College", "Residential", "Market",
            "Railway Station", "Office", "Tourist", "Other"
        });

        categoryBox = new JComboBox<>(new String[]{
            "Food", "Grocery", "Stationery", "Clothing",
            "Electronics", "Pharmacy", "Services", "Other"
        });

        styleField(idField);
        styleField(nameField);
        styleField(ownerField);
        styleField(typeField);
        styleField(areaField);
        styleField(contactField);
        styleField(investmentField);
        styleField(salesField);

        styleCombo(areaTypeBox);
        styleCombo(categoryBox);

        addField(formPanel, gbc, 0, 0, "Business ID *", idField);
        addField(formPanel, gbc, 0, 1, "Business Name *", nameField);
        addField(formPanel, gbc, 1, 0, "Owner Name *", ownerField);
        addField(formPanel, gbc, 1, 1, "Business Type *", typeField);
        addField(formPanel, gbc, 2, 0, "Area *", areaField);
        addField(formPanel, gbc, 2, 1, "Contact *", contactField);
        addField(formPanel, gbc, 3, 0, "Investment (₹) *", investmentField);
        addField(formPanel, gbc, 3, 1, "Monthly Sales (₹) *", salesField);
        addField(formPanel, gbc, 4, 0, "Area Type *", areaTypeBox);
        addField(formPanel, gbc, 4, 1, "Category *", categoryBox);

        JButton updateButton = createButton(
                "Update Business", BLUE, Color.WHITE
        );

        JButton clearButton = createButton(
                "Clear Fields", new Color(238, 242, 248), TEXT
        );

        JPanel buttonPanel = new JPanel(
                new FlowLayout(FlowLayout.RIGHT, 10, 5)
        );
        buttonPanel.setOpaque(false);
        buttonPanel.add(clearButton);
        buttonPanel.add(updateButton);

        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridwidth = 2;
        gbc.weightx = 1.0;
        formPanel.add(buttonPanel, gbc);

        JPanel contentPanel = new JPanel(new BorderLayout());
        contentPanel.setBackground(BACKGROUND);
        contentPanel.add(formPanel, BorderLayout.NORTH);

        JScrollPane scrollPane = new JScrollPane(contentPanel);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        scrollPane.setHorizontalScrollBarPolicy(
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER
        );
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        scrollPane.getViewport().setBackground(BACKGROUND);

        add(scrollPane, BorderLayout.CENTER);

        updateButton.addActionListener(e -> updateBusiness());
        clearButton.addActionListener(e -> clearFields());
        idField.addActionListener(e -> nameField.requestFocusInWindow());
    }

    private void addField(
            JPanel panel,
            GridBagConstraints gbc,
            int row,
            int column,
            String labelText,
            JComponent component) {

        JPanel fieldPanel = new JPanel();
        fieldPanel.setLayout(new BoxLayout(fieldPanel, BoxLayout.Y_AXIS));
        fieldPanel.setBackground(Color.WHITE);

        JLabel label = new JLabel(labelText);
        label.setFont(new Font("SansSerif", Font.BOLD, 13));
        label.setForeground(TEXT);
        label.setAlignmentX(Component.LEFT_ALIGNMENT);

        component.setAlignmentX(Component.LEFT_ALIGNMENT);
        component.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));

        fieldPanel.add(label);
        fieldPanel.add(Box.createVerticalStrut(7));
        fieldPanel.add(component);

        gbc.gridx = column;
        gbc.gridy = row;
        gbc.gridwidth = 1;

        panel.add(fieldPanel, gbc);
    }

    private void styleField(JTextField field) {
        field.setFont(new Font("SansSerif", Font.PLAIN, 13));
        field.setForeground(TEXT);
        field.setBackground(Color.WHITE);
        field.setCaretColor(BLUE);

        field.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(BORDER),
                        new EmptyBorder(8, 10, 8, 10)
                )
        );

        field.setPreferredSize(new Dimension(220, 38));
    }

    private void styleCombo(JComboBox<String> combo) {
        combo.setFont(new Font("SansSerif", Font.PLAIN, 13));
        combo.setForeground(TEXT);
        combo.setBackground(Color.WHITE);
        combo.setPreferredSize(new Dimension(220, 38));
    }

    private JButton createButton(
            String text,
            Color background,
            Color foreground) {

        JButton button = new JButton(text);
        button.setFont(new Font("SansSerif", Font.BOLD, 13));
        button.setForeground(foreground);
        button.setBackground(background);
        button.setFocusPainted(false);
        button.setOpaque(true);
        button.setBorderPainted(false);
        button.setBorder(new EmptyBorder(12, 18, 12, 18));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));

        return button;
    }

    private void updateBusiness() {

        String idText = idField.getText().trim();

        if (idText.isEmpty()) {
            showWarning("Please enter the Business ID.");
            idField.requestFocusInWindow();
            return;
        }

        try {
            int id = Integer.parseInt(idText);

            if (id <= 0) {
                showWarning("Business ID must be greater than zero.");
                return;
            }

            if (nameField.getText().trim().isEmpty()
                    || ownerField.getText().trim().isEmpty()
                    || typeField.getText().trim().isEmpty()
                    || areaField.getText().trim().isEmpty()
                    || contactField.getText().trim().isEmpty()
                    || investmentField.getText().trim().isEmpty()
                    || salesField.getText().trim().isEmpty()) {

                showWarning("Please fill in all fields.");
                return;
            }

            double investment =
                    Double.parseDouble(investmentField.getText().trim());

            double sales =
                    Double.parseDouble(salesField.getText().trim());

            if (!Double.isFinite(investment)
                    || !Double.isFinite(sales)) {
                showWarning("Investment and sales must be valid numbers.");
                return;
            }

            if (investment < 0 || sales < 0) {
                showWarning("Investment and sales cannot be negative.");
                return;
            }

            String name = nameField.getText().trim();
            String owner = ownerField.getText().trim();
            String type = typeField.getText().trim();
            String area = areaField.getText().trim();
            String contact = contactField.getText().trim();

            String areaType = areaTypeBox.getSelectedItem().toString();
            String category = categoryBox.getSelectedItem().toString();

            Business updatedBusiness = new Business(
                    id,
                    name,
                    owner,
                    type,
                    area,
                    contact,
                    investment,
                    sales,
                    areaType,
                    category
            );

            // FIX: use the existing ID variable "id"
            boolean updated = manager.updateBusiness(id, updatedBusiness);

            if (updated) {
                DataManager.saveData(manager.getAllBusinesses());

                // Refresh dashboard if callback was provided
                if (refreshCallback != null) {
                    refreshCallback.run();
                }

                JOptionPane.showMessageDialog(
                        this,
                        "Business updated successfully!",
                        "Success",
                        JOptionPane.INFORMATION_MESSAGE
                );

                clearFields();

            } else {
                JOptionPane.showMessageDialog(
                        this,
                        "No business found with ID: " + id,
                        "Business Not Found",
                        JOptionPane.WARNING_MESSAGE
                );
            }

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(
                    this,
                    "Please enter valid numeric values for ID, Investment and Sales.",
                    "Invalid Input",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    private void showWarning(String message) {
        JOptionPane.showMessageDialog(
                this,
                message,
                "Check Input",
                JOptionPane.WARNING_MESSAGE
        );
    }

    private void clearFields() {
        idField.setText("");
        nameField.setText("");
        ownerField.setText("");
        typeField.setText("");
        areaField.setText("");
        contactField.setText("");
        investmentField.setText("");
        salesField.setText("");

        areaTypeBox.setSelectedIndex(0);
        categoryBox.setSelectedIndex(0);

        idField.requestFocusInWindow();
    }
}