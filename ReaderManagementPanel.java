import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;

public class ReaderManagementPanel extends JPanel {
    private JComboBox<String> searchTypeCombo;
    private JTextField searchField;
    private JButton searchButton;
    private JTable table;
    private DefaultTableModel tableModel;
    private JButton addButton;
    private JButton editButton;
    private JButton deleteButton;

    private ReaderManagement readerManagement;
    private int currentPage = 0;
    private int rowsPerPage = 10;
    private JLabel pageLabel;
    private JButton prevButton;
    private JButton nextButton;

    public ReaderManagementPanel() {
        this.setLayout(new BorderLayout());
        this.setBackground(Color.WHITE);
        this.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        readerManagement = new ReaderManagement();

        JPanel searchPanel = new JPanel();
        searchPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 10));
        searchPanel.setBackground(Color.WHITE);
        searchPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));

        JLabel label = new JLabel("Search By:");
        label.setFont(new Font("Arial", Font.BOLD, 12));
        searchTypeCombo = new JComboBox<>(new String[] { "Họ tên", "CMND/CCCD" });
        searchField = new JTextField(20);
        searchField.setFont(new Font("Arial", Font.PLAIN, 12));
        searchField.setPreferredSize(new Dimension(50, 25));
        searchField.enableInputMethods(false);
        searchButton = createButton("Search", new Color(0, 188, 212));

        searchPanel.add(label);
        searchPanel.add(searchTypeCombo);
        searchPanel.add(searchField);
        searchPanel.add(searchButton);

        searchButton.addActionListener(e -> performSearch());

        String[] columns = { "ID", "Mã độc giả", "Họ tên", "CMND", "Ngày sinh", "Giới tính", "Email", "Địa chỉ",
                "Ngày lập thẻ",
                "Ngày hết hạn" };
        this.tableModel = new DefaultTableModel(columns, 0);
        this.table = new JTable(tableModel);
        table.setFont(new Font("Arial", Font.PLAIN, 12));
        table.setRowHeight(25);
        table.setSelectionBackground(new Color(0, 172, 193));

        table.getTableHeader().setFont(new Font("Arial", Font.BOLD, 12));

        JPanel tablePanel = new JPanel(new BorderLayout());
        tablePanel.setBackground(Color.WHITE);
        tablePanel.add(table.getTableHeader(), BorderLayout.NORTH);
        tablePanel.add(table, BorderLayout.CENTER);
        tablePanel.setBorder(BorderFactory.createLineBorder(new Color(50, 50, 50), 1));

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 10));
        buttonPanel.setBorder(BorderFactory.createLineBorder(new Color(50, 50, 50), 1));

        this.addButton = createButton("Add", new Color(0, 172, 193));
        this.editButton = createButton("Edit", new Color(0, 172, 193));
        this.deleteButton = createButton("Delete", new Color(0, 172, 193));

        this.addButton.addActionListener(e -> {
            AddReaderDialog dialog = new AddReaderDialog((JFrame) SwingUtilities.getWindowAncestor(this));
            dialog.setVisible(true);

            if (dialog.isSubmitted()) {
                Reader newReader = dialog.getReader();
                if (newReader != null) {
                    this.readerManagement.addReader(newReader);
                    this.readerManagement.saveReaderListToFile();
                    loadTableData();
                    JOptionPane.showMessageDialog(this, "Reader added successfully!", "Success",
                            JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(this, "Invalid data entered!", "Error",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        this.deleteButton.addActionListener(e -> {
            int selectedRow = this.table.getSelectedRow();
            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(this, "Please select a reader to delete!", "Warning",
                        JOptionPane.WARNING_MESSAGE);
                return;
            }

            int index = (currentPage * rowsPerPage) + selectedRow;
            if (index >= this.readerManagement.getReaders().size()) {
                JOptionPane.showMessageDialog(this, "Invalid selection!", "Error",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            Reader selectedReader = this.readerManagement.getReaders().get(index);
            int confirm = JOptionPane.showConfirmDialog(this,
                    "Are you sure you want to delete reader: " + selectedReader.getName() + "?",
                    "Confirm Delete",
                    JOptionPane.YES_NO_OPTION);

            if (confirm == JOptionPane.YES_OPTION) {
                this.readerManagement.getReaders().remove(index);
                this.readerManagement.saveReaderListToFile();
                loadTableData();
                JOptionPane.showMessageDialog(this, "Reader deleted successfully!", "Success",
                        JOptionPane.INFORMATION_MESSAGE);
            }
        });

        buttonPanel.add(addButton);
        buttonPanel.add(editButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(new JLabel(" | "));

        prevButton = createButton("< Previous", new Color(0, 172, 193));
        prevButton.setPreferredSize(new Dimension(90, 30));
        pageLabel = new JLabel("Page 1 / 1");
        pageLabel.setFont(new Font("Arial", Font.BOLD, 12));
        nextButton = createButton("Next >", new Color(0, 172, 193));
        prevButton.addActionListener(l -> previousPage());
        nextButton.addActionListener(l -> nextPage());

        buttonPanel.add(prevButton);
        buttonPanel.add(pageLabel);
        buttonPanel.add(nextButton);

        add(searchPanel, BorderLayout.NORTH);
        add(tablePanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    public void previousPage() {
        if (currentPage > 0) {
            currentPage--;
            loadTableData();
        }

    }

    public void nextPage() {
        int totalPages = (this.readerManagement.getReaders().size() + rowsPerPage - 1) / rowsPerPage;
        if (currentPage < totalPages) {
            currentPage++;
            loadTableData();
        }
    }

    public void loadTableData() {
        this.readerManagement.loadReadersFromFile();
        tableModel.setRowCount(0);
        int startIndex = currentPage * rowsPerPage;
        int endIndex = Math.min(startIndex + rowsPerPage, this.readerManagement.getReaders().size());

        for (int i = startIndex; i < endIndex; i++) {
            Reader reader = this.readerManagement.getReaders().get(i);
            Object[] rowData = {
                    i + 1,
                    reader.getReaderId(),
                    reader.getName(),
                    reader.getIdCard(),
                    reader.getDateOfBirth().toString(),
                    reader.getGender(),
                    reader.getEmail(),
                    reader.getAddress(),
                    reader.getCardCreationDate().toString(),
                    reader.getExpiryDate().toString()
            };
            tableModel.addRow(rowData);
        }

        int totalPages = (this.readerManagement.getReaders().size() + rowsPerPage - 1) / rowsPerPage;
        if (totalPages == 0)
            totalPages = 1;
        pageLabel.setText("Page " + (currentPage + 1) + " / " + totalPages);

        prevButton.setEnabled(currentPage > 0);
        nextButton.setEnabled(currentPage < totalPages - 1);
    }

    public DefaultTableModel getTableModel() {
        return tableModel;
    }

    public void addRowToTable(Reader reader) {
        this.readerManagement.getReaders().add(reader);
        currentPage = 0;
        loadTableData();
    }

    private JButton createButton(String text, Color bgColor) {
        JButton btn = new JButton(text);
        btn.setFont(new Font("Arial", Font.BOLD, 12));
        btn.setPreferredSize(new Dimension(75, 30));
        btn.setBackground(bgColor);
        btn.setForeground(Color.WHITE);
        btn.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        btn.setFocusPainted(false);
        return btn;
    }

    private void performSearch() {
        String searchType = (String) searchTypeCombo.getSelectedItem();
        String searchValue = searchField.getText().trim();

        if (searchValue.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter search value!", "Warning",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        this.readerManagement.loadReadersFromFile();
        ArrayList<Reader> allReaders = this.readerManagement.getReaders();
        ArrayList<Reader> searchResults = new java.util.ArrayList<>();
        for (Reader reader : allReaders) {
            boolean match = false;

            if ("Họ tên".equals(searchType)) {
                match = reader.getName().toLowerCase().contains(searchValue.toLowerCase());
            } else if ("CMND/CCCD".equals(searchType)) {
                match = reader.getIdCard().contains(searchValue);
            }

            if (match) {
                searchResults.add(reader);
            }
        }
        if (searchResults.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No readers found matching: " + searchValue, "Search Result",
                    JOptionPane.INFORMATION_MESSAGE);
            loadTableData();
        } else {
            currentPage = 0;
            displaySearchResults(searchResults);
        }
    }

    private void displaySearchResults(ArrayList<Reader> results) {
        tableModel.setRowCount(0);
        int startIndex = currentPage * rowsPerPage;
        int endIndex = Math.min(startIndex + rowsPerPage, results.size());

        for (int i = startIndex; i < endIndex; i++) {
            Reader reader = results.get(i);
            Object[] rowData = {
                    i + 1,
                    reader.getReaderId(),
                    reader.getName(),
                    reader.getIdCard(),
                    reader.getDateOfBirth().toString(),
                    reader.getGender(),
                    reader.getEmail(),
                    reader.getAddress(),
                    reader.getCardCreationDate().toString(),
                    reader.getExpiryDate().toString()
            };
            tableModel.addRow(rowData);
        }

        int totalPages = (results.size() + rowsPerPage - 1) / rowsPerPage;
        if (totalPages == 0)
            totalPages = 1;
        pageLabel.setText("Page " + (currentPage + 1) + " / " + totalPages + " (Search Results)");

        prevButton.setEnabled(currentPage > 0);
        nextButton.setEnabled(currentPage < totalPages - 1);

        prevButton.addActionListener(l -> {
            if (currentPage > 0) {
                currentPage--;
                displaySearchResults(results);
            }
        });

        nextButton.addActionListener(l -> {
            int totalPagesForResults = (results.size() + rowsPerPage - 1) / rowsPerPage;
            if (currentPage < totalPagesForResults - 1) {
                currentPage++;
                displaySearchResults(results);
            }
        });
    }
}
