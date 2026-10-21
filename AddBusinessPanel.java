
package areabusiness;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class AddBusinessPanel extends JPanel {

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
    private final Color BLUE = new Color(48, 112, 220);
    private final Color TEXT = new Color(35, 47, 68);
    private final Color MUTED = new Color(110, 121, 140);
    private final Color BORDER = new Color(220, 227, 238);

    // Existing constructor
    public AddBusinessPanel(BusinessManager manager) {
        this(manager, null);
    }

    // Constructor with refresh callback
    public AddBusinessPanel(
            BusinessManager manager,
            Runnable refreshCallback) {

        this.manager = manager;
        this.refreshCallback = refreshCallback;

        setLayout(new BorderLayout());
        setBackground(BACKGROUND);

        // HEADER
        JPanel header = new JPanel();
        header.setLayout(new BoxLayout(header, BoxLayout.Y_AXIS));
        header.setBackground(BACKGROUND);
        header.setBorder(new EmptyBorder(22, 28, 15, 28));

        JLabel title = new JLabel("Add New Business");
        title.setFont(new Font("SansSerif", Font.BOLD, 27));
        title.setForeground(TEXT);

        JLabel subtitle = new JLabel(
                "Enter the business details to add a new record."
        );
        subtitle.setFont(new Font("SansSerif", Font.PLAIN, 13));
        subtitle.setForeground(MUTED);

        header.add(title);
        header.add(Box.createVerticalStrut(6));
        header.add(subtitle);

        add(header, BorderLayout.NORTH);

        // FORM CARD
        JPanel card = new JPanel(new BorderLayout(0, 15));
        card.setBackground(Color.WHITE);
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(BORDER),
                new EmptyBorder(22, 25, 22, 25)
        ));

        JLabel formTitle = new JLabel("Business Information");
        formTitle.setFont(new Font("SansSerif", Font.BOLD, 17));
        formTitle.setForeground(TEXT);

        JPanel form = new JPanel(new GridBagLayout());
        form.setBackground(Color.WHITE);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(9, 8, 9, 8);
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

        addField(form, gbc, "Business ID *", idField, 0, 0);
        addField(form, gbc, "Business Name *", nameField, 1, 0);

        addField(form, gbc, "Owner Name *", ownerField, 0, 1);
        addField(form, gbc, "Business Type *", typeField, 1, 1);

        addField(form, gbc, "Area / Location *", areaField, 0, 2);
        addField(form, gbc, "Contact Number *", contactField, 1, 2);

        addField(form, gbc, "Investment (₹) *",
                investmentField, 0, 3);
        addField(form, gbc, "Monthly Sales (₹) *",
                salesField, 1, 3);

        addField(form, gbc, "Area Type *", areaTypeBox, 0, 4);
        addField(form, gbc, "Business Category *",
                categoryBox, 1, 4);

        JPanel buttons = new JPanel(
                new FlowLayout(FlowLayout.RIGHT, 10, 0));
        buttons.setBackground(Color.WHITE);

        JButton clearButton = new JButton("Clear Form");
        styleButton(clearButton,
                new Color(235, 239, 246), TEXT);

        JButton addButton = new JButton(" +  Add Business ");
        styleButton(addButton, BLUE, Color.WHITE);

        clearButton.addActionListener(e -> clearForm());
        addButton.addActionListener(e -> addBusiness());

        buttons.add(clearButton);
        buttons.add(addButton);

        card.add(formTitle, BorderLayout.NORTH);
        card.add(form, BorderLayout.CENTER);
        card.add(buttons, BorderLayout.SOUTH);

        JPanel wrapper = new JPanel(new BorderLayout());
        wrapper.setBackground(BACKGROUND);
        wrapper.setBorder(new EmptyBorder(0, 28, 25, 28));
        wrapper.add(card, BorderLayout.NORTH);

        add(wrapper, BorderLayout.CENTER);
    }

    private void addField(
            JPanel panel,
            GridBagConstraints gbc,
            String labelText,
            JComponent field,
            int column,
            int row) {

        JPanel fieldPanel = new JPanel(new BorderLayout(0, 7));
        fieldPanel.setBackground(Color.WHITE);

        JLabel label = new JLabel(labelText);
        label.setFont(new Font("SansSerif", Font.BOLD, 12));
        label.setForeground(TEXT);

        field.setFont(new Font("SansSerif", Font.PLAIN, 14));
        field.setPreferredSize(new Dimension(220, 38));

        if (field instanceof JTextField) {
            field.setBackground(new Color(252, 253, 255));
            field.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(BORDER),
                    new EmptyBorder(5, 9, 5, 9)
            ));
        } else {
            field.setBackground(Color.WHITE);
        }

        fieldPanel.add(label, BorderLayout.NORTH);
        fieldPanel.add(field, BorderLayout.CENTER);

        gbc.gridx = column;
        gbc.gridy = row;
        gbc.weightx = 1.0;
        gbc.gridwidth = 1;

        panel.add(fieldPanel, gbc);
    }

    private void styleButton(
            JButton button,
            Color background,
            Color foreground) {

        button.setFont(new Font("SansSerif", Font.BOLD, 13));
        button.setBackground(background);
        button.setForeground(foreground);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setOpaque(true);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setPreferredSize(new Dimension(145, 40));
    }

    private void addBusiness() {

        String idText = idField.getText().trim();
        String name = nameField.getText().trim();
        String owner = ownerField.getText().trim();
        String type = typeField.getText().trim();
        String area = areaField.getText().trim();
        String contact = contactField.getText().trim();
        String investmentText = investmentField.getText().trim();
        String salesText = salesField.getText().trim();

        if (idText.isEmpty() || name.isEmpty()
                || owner.isEmpty() || type.isEmpty()
                || area.isEmpty() || contact.isEmpty()
                || investmentText.isEmpty()
                || salesText.isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please fill in all required fields.",
                    "Missing Information",
                    JOptionPane.WARNING_MESSAGE
            );
            return;
        }

        try {
            int id = Integer.parseInt(idText);
            double investment =
                    Double.parseDouble(investmentText);
            double sales = Double.parseDouble(salesText);

            if (id <= 0) {
                showError("Business ID must be greater than zero.");
                return;
            }

            if (!Double.isFinite(investment)
                    || !Double.isFinite(sales)
                    || investment < 0 || sales < 0) {
                showError(
                        "Investment and sales must be valid, "
                        + "non-negative numbers.");
                return;
            }

            for (Business existing : manager.getAllBusinesses()) {
                if (existing.getBusinessId() == id) {
                    showError("This Business ID already exists.");
                    return;
                }
            }

            Business business = new Business(
                    id,
                    name,
                    owner,
                    type,
                    area,
                    contact,
                    investment,
                    sales,
                    (String) areaTypeBox.getSelectedItem(),
                    (String) categoryBox.getSelectedItem()
            );

            manager.addBusiness(business);
            DataManager.saveData(manager.getAllBusinesses());

            // Refresh dashboard when callback is supplied
            if (refreshCallback != null) {
                refreshCallback.run();
            }

            JOptionPane.showMessageDialog(
                    this,
                    "Business added successfully!",
                    "Success",
                    JOptionPane.INFORMATION_MESSAGE
            );

            clearForm();

        } catch (NumberFormatException ex) {
            showError(
                    "Business ID must be a whole number. "
                    + "Investment and sales must be valid numbers.");
        }
    }

    private void showError(String message) {
        JOptionPane.showMessageDialog(
                this,
                message,
                "Invalid Information",
                JOptionPane.WARNING_MESSAGE
        );
    }

    private void clearForm() {
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