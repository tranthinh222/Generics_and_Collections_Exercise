package src.dialogs;

import javax.swing.JButton;
import javax.swing.JTextField;

public class BookDialog {
    private JTextField isbnField;
    private JTextField titleField;
    private JTextField authorField;
    private JTextField publisherField;
    private JTextField yearField;
    private JTextField categoryField;
    private JTextField priceField;
    private JTextField quantityField;
    private JButton saveButton;
    private JButton cancelButton;
    private boolean isSaved;

    public String getIsbnField() {
        return isbnField.getText();
    }

    public String getTitleField() {
        return titleField.getText();
    }

    public String getAuthorField() {
        return authorField.getText();
    }

    public String getPublisherField() {
        return publisherField.getText();
    }

    public Integer getYearField() {
        return Integer.parseInt(this.yearField.getText());
    }

    public String getCategoryField() {
        return this.categoryField.getText();
    }

    public Integer getPriceField() {
        return Integer.parseInt(this.priceField.getText());
    }

    public Integer getQuantityField() {
        return Integer.parseInt(quantityField.getText());
    }

    public boolean isSaved() {
        return isSaved;
    }

}
