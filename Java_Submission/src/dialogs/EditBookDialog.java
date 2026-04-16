import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.Box.Filler;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.text.PlainDocument;

public class EditBookDialog extends JDialog {
    private JTextField isbnField;
    private JTextField titleField;
    private JTextField authorField;
    private JTextField publisherField;
    private JTextField publicationYearField;
    private JTextField categoryField;
    private JTextField priceField;
    private JTextField quantityField;
    private JButton submitButton;
    private JButton cancelButton;
    private boolean isSubmitted = false;

    public EditBookDialog(JFrame parent, Book book) {
        super(parent, "Edit Book", true);
        this.setSize(500, 425);
        this.setLocationRelativeTo(parent);

        this.isbnField = new JTextField(20);
        this.titleField = new JTextField(20);
        this.authorField = new JTextField(20);
        this.publisherField = new JTextField(20);
        this.publicationYearField = new JTextField(20);
        this.categoryField = new JTextField(20);
        this.priceField = new JTextField(20);
        this.quantityField = new JTextField(20);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBackground(Color.WHITE);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        Font labelFont = new Font("Arial", Font.BOLD, 12);
        Font inputFont = new Font("Arial", Font.PLAIN, 12);

        mainPanel.add(createFieldRow("ISBN:", labelFont, inputFont, this.isbnField));
        mainPanel.add(createVerticalSpace());
        mainPanel.add(createFieldRow("Tên sách:", labelFont, inputFont, this.titleField));
        mainPanel.add(createVerticalSpace());
        mainPanel.add(createFieldRow("Tác giả:", labelFont, inputFont, this.authorField));
        mainPanel.add(createVerticalSpace());
        mainPanel.add(createFieldRow("Nhà xuất bản:", labelFont, inputFont, this.publisherField));
        mainPanel.add(createVerticalSpace());
        mainPanel.add(createFieldRow("Năm xuất bản:", labelFont, inputFont, this.publicationYearField));
        mainPanel.add(createVerticalSpace());
        mainPanel.add(createFieldRow("Thể loại:", labelFont, inputFont, this.categoryField));
        mainPanel.add(createVerticalSpace());
        mainPanel.add(createFieldRow("Giá sách:", labelFont, inputFont, this.priceField));
        mainPanel.add(createVerticalSpace());
        mainPanel.add(createFieldRow("Số quyển:", labelFont, inputFont, this.quantityField));
        mainPanel.add(createVerticalSpace());

        this.isbnField.setText(book.getIsbn());
        this.isbnField.setEditable(false);
        this.titleField.setText(book.getTitle());
        this.authorField.setText(book.getAuthor());
        this.publisherField.setText(book.getPublisher());
        this.publicationYearField.setText(String.valueOf(book.getPublicationYear()));
        this.categoryField.setText(book.getCategory());
        this.priceField.setText(String.valueOf(book.getPrice()));
        this.quantityField.setText(String.valueOf(book.getQuantity()));

        JPanel buttonPanel = new JPanel(new GridLayout(1, 2, 10, 0));
        buttonPanel.setBackground(Color.WHITE);
        buttonPanel.setMaximumSize(new Dimension(400, 40));

        this.submitButton = createButton("Submit", new Color(0, 172, 193));
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
        this.titleField.requestFocus();
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

    public Book getBook() {
        if (!isSubmitted) {
            return null;
        }

        try {
            String isbn = this.isbnField.getText().trim();
            String title = this.titleField.getText().trim();
            String author = this.authorField.getText().trim();
            String publisher = this.publisherField.getText().trim();
            int publicationYear = Integer.parseInt(this.publicationYearField.getText().trim());
            String category = this.categoryField.getText().trim();
            double price = Double.parseDouble(this.priceField.getText().trim());
            int quantity = Integer.parseInt(this.quantityField.getText().trim());

            return new Book(isbn, title, author, publisher, publicationYear, category, price, quantity);
        } catch (NumberFormatException e) {
            System.err.println("Error parsing book data: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    private String validateInput() {
        if (this.titleField.getText().trim().isEmpty()) {
            return "Tên sách không được để trống!";
        }

        if (this.authorField.getText().trim().isEmpty()) {
            return "Tác giả không được để trống!";
        }

        if (this.publisherField.getText().trim().isEmpty()) {
            return "Nhà xuất bản không được để trống!";
        }

        if (this.publicationYearField.getText().trim().isEmpty()) {
            return "Năm xuất bản không được để trống!";
        }

        if (this.categoryField.getText().trim().isEmpty()) {
            return "Thể loại không được để trống!";
        }

        if (this.priceField.getText().trim().isEmpty()) {
            return "Giá sách không được để trống!";
        }

        if (this.quantityField.getText().trim().isEmpty()) {
            return "Số quyển không được để trống!";
        }

        try {
            Integer.parseInt(this.publicationYearField.getText().trim());
        } catch (Exception e) {
            return "Năm xuất bản phải là số!";
        }

        try {
            Double.parseDouble(this.priceField.getText().trim());
        } catch (Exception e) {
            return "Giá sách phải là số!";
        }

        try {
            Integer.parseInt(this.quantityField.getText().trim());
        } catch (Exception e) {
            return "Số quyển phải là số!";
        }

        return null;
    }
}
