package src.dialogs;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.time.LocalDate;

import javax.swing.BorderFactory;
import javax.swing.Box.Filler;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.text.PlainDocument;

import src.domain.Reader;

public class EditReaderDialog extends JDialog {
    private JTextField readerIdField;
    private JTextField nameField;
    private JTextField idCardField;
    private JTextField dateOfBirthField;
    private JComboBox<String> genderCombo;
    private JTextField emailField;
    private JTextField addressField;
    private JButton submitButton;
    private JButton cancelButton;
    private boolean isSubmitted = false;
    private Reader originalReader;

    public EditReaderDialog(JFrame parent, Reader readerToEdit) {
        super(parent, "Edit Reader", true);
        this.originalReader = readerToEdit;
        this.setSize(500, 400);
        this.setLocationRelativeTo(parent);

        this.readerIdField = new JTextField(20);
        this.nameField = new JTextField(20);
        this.idCardField = new JTextField(20);
        this.dateOfBirthField = new JTextField(20);
        this.emailField = new JTextField(20);
        this.addressField = new JTextField(20);
        this.genderCombo = new JComboBox<>(new String[] { "Nam", "Nữ", "Khác" });

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBackground(Color.WHITE);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        Font labelFont = new Font("Arial", Font.BOLD, 12);
        Font inputFont = new Font("Arial", Font.PLAIN, 12);

        mainPanel.add(createFieldRow("Mã độc giả:", labelFont, inputFont, this.readerIdField));
        mainPanel.add(createVerticalSpace());
        mainPanel.add(createFieldRow("Họ tên:", labelFont, inputFont, this.nameField));
        mainPanel.add(createVerticalSpace());
        mainPanel.add(createFieldRow("CMND:", labelFont, inputFont, this.idCardField));
        mainPanel.add(createVerticalSpace());
        mainPanel.add(createFieldRow("Ngày sinh (YYYY-MM-DD):", labelFont, inputFont, this.dateOfBirthField));
        mainPanel.add(createVerticalSpace());
        mainPanel.add(createGenderRow("Giới tính:", labelFont, inputFont, this.genderCombo));
        mainPanel.add(createVerticalSpace());
        mainPanel.add(createFieldRow("Email:", labelFont, inputFont, this.emailField));
        mainPanel.add(createVerticalSpace());
        mainPanel.add(createFieldRow("Địa chỉ:", labelFont, inputFont, this.addressField));
        mainPanel.add(createVerticalSpace());

        JPanel buttonPanel = new JPanel(new GridLayout(1, 2, 10, 0));
        buttonPanel.setBackground(Color.WHITE);
        buttonPanel.setMaximumSize(new Dimension(400, 40));

        this.submitButton = createButton("Update", new Color(0, 172, 193));
        this.cancelButton = createButton("Cancel", new Color(0, 172, 193));

        this.submitButton.addActionListener(e -> {
            String error = validateInput();
            if (error != null) {
                JOptionPane.showMessageDialog(this, error, "Invalid Input", JOptionPane.ERROR_MESSAGE);
                return;
            }
            isSubmitted = true;
            dispose();
        });

        this.cancelButton.addActionListener(e -> {
            isSubmitted = false;
            dispose();
        });

        buttonPanel.add(submitButton);
        buttonPanel.add(cancelButton);
        mainPanel.add(buttonPanel);

        this.add(mainPanel);
        loadReaderData(readerToEdit);
    }

    private void loadReaderData(Reader reader) {
        this.readerIdField.setText(reader.getReaderId());
        this.nameField.setText(reader.getName());
        this.idCardField.setText(reader.getIdCard());
        this.dateOfBirthField.setText(reader.getDateOfBirth().toString());
        this.genderCombo.setSelectedItem(reader.getGender());
        this.emailField.setText(reader.getEmail());
        this.addressField.setText(reader.getAddress());
    }

    private JPanel createFieldRow(String labelText, Font labelFont, Font inputFont, JTextField field) {
        JPanel row = new JPanel(new BorderLayout(10, 0));
        row.setBackground(Color.WHITE);
        row.setMaximumSize(new Dimension(400, 30));
        row.setAlignmentX(JPanel.CENTER_ALIGNMENT);

        JLabel label = new JLabel(labelText);
        label.setFont(labelFont);
        label.setPreferredSize(new Dimension(170, 30));

        field.setFont(inputFont);
        field.setEditable(true);
        field.enableInputMethods(false);

        PlainDocument doc = new PlainDocument();
        field.setDocument(doc);

        row.add(label, BorderLayout.WEST);
        row.add(field, BorderLayout.CENTER);
        return row;
    }

    private JPanel createGenderRow(String labelText, Font labelFont, Font inputFont, JComboBox<String> combo) {
        JPanel row = new JPanel(new BorderLayout(10, 0));
        row.setBackground(Color.WHITE);
        row.setMaximumSize(new Dimension(400, 30));
        row.setAlignmentX(JPanel.CENTER_ALIGNMENT);

        JLabel label = new JLabel(labelText);
        label.setFont(labelFont);
        label.setPreferredSize(new Dimension(170, 30));

        combo.setFont(inputFont);

        row.add(label, BorderLayout.WEST);
        row.add(combo, BorderLayout.CENTER);
        return row;
    }

    private Filler createVerticalSpace() {
        return new Filler(new Dimension(0, 5), new Dimension(0, 5), new Dimension(0, 5));
    }

    private JButton createButton(String text, Color bgColor) {
        JButton btn = new JButton(text);
        btn.setFont(new Font("Arial", Font.BOLD, 12));
        btn.setBackground(bgColor);
        btn.setForeground(Color.WHITE);
        btn.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        btn.setFocusPainted(false);
        return btn;
    }

    public boolean isSubmitted() {
        return isSubmitted;
    }

    public Reader getReader() {
        if (!isSubmitted) {
            return null;
        }

        String readerId = this.readerIdField.getText();
        String name = this.nameField.getText();
        String idCard = this.idCardField.getText();
        LocalDate dateOfBirth = LocalDate.parse(this.dateOfBirthField.getText());
        String gender = (String) this.genderCombo.getSelectedItem();
        String email = this.emailField.getText();
        String address = this.addressField.getText();
        LocalDate cardCreationDate = originalReader.getCardCreationDate();

        return new Reader(readerId, name, idCard, dateOfBirth, gender, email, address, cardCreationDate);
    }

    private String validateInput() {
        if (this.readerIdField.getText().trim().isEmpty()) {
            return "Mã độc giả không được để trống!";
        }
        if (this.nameField.getText().trim().isEmpty()) {
            return "Họ tên không được để trống!";
        }
        if (this.idCardField.getText().trim().isEmpty()) {
            return "CMND không được để trống!";
        }
        if (this.dateOfBirthField.getText().trim().isEmpty()) {
            return "Ngày sinh không được để trống!";
        }
        if (this.emailField.getText().trim().isEmpty()) {
            return "Email không được để trống!";
        }
        if (this.addressField.getText().trim().isEmpty()) {
            return "Địa chỉ không được để trống!";
        }

        try {
            LocalDate.parse(this.dateOfBirthField.getText().trim());
        } catch (Exception e) {
            return "Ngày sinh phải có format YYYY-MM-DD";
        }

        String email = this.emailField.getText().trim();
        if (!email.contains("@") || !email.contains(".")) {
            return "Email không hợp lệ!";
        }

        String idCard = this.idCardField.getText().trim();
        if (!idCard.matches("\\d+")) {
            return "CMND phải chứa toàn số!";
        }

        return null;
    }
}
