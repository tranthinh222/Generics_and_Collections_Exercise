import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;

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

public class BookManagementPanel extends JPanel {
    private JTable table;
    private DefaultTableModel tableModel;

    private BookManagement bookManagement;
    private int currentPage = 0;
    private int rowsPerPage = 10;
    private JLabel pageLabel;
    private JButton prevButton;
    private JButton nextButton;

    public BookManagementPanel() {
        this.setLayout(new BorderLayout());
        this.setBackground(Color.WHITE);
        this.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        bookManagement = new BookManagement();

        JPanel searchPanel = new JPanel();
        searchPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 10));
        searchPanel.setBackground(Color.WHITE);
        searchPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));

        JLabel label = new JLabel("Search By:");
        label.setFont(new Font("Arial", Font.BOLD, 12));
        JComboBox<String> searchTypeCombo = new JComboBox<>(new String[] { "Tên sách", "ISBN" });
        JTextField searchField = new JTextField(20);
        searchField.setFont(new Font("Arial", Font.PLAIN, 12));
        searchField.setPreferredSize(new Dimension(150, 25));
        searchField.enableInputMethods(false);
        JButton searchButton = createButton("Search", new Color(0, 188, 212));

        searchPanel.add(label);
        searchPanel.add(searchTypeCombo);
        searchPanel.add(searchField);
        searchPanel.add(searchButton);

        String[] columns = { "ID", "ISBN", "Tên sách", "Tác giả", "Nhà xuất bản", "Năm", "Thể loại", "Giá",
                "Số quyển" };
        this.tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
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

        JButton addButton = createButton("Add", new Color(0, 172, 193));
        JButton editButton = createButton("Edit", new Color(0, 172, 193));
        JButton deleteButton = createButton("Delete", new Color(0, 172, 193));

        addButton.addActionListener(e -> {
            AddBookDialog dialog = new AddBookDialog((JFrame) SwingUtilities.getWindowAncestor(this));
            dialog.setVisible(true);

            if (dialog.isSubmitted()) {
                Book newBook = dialog.getBook();
                if (newBook != null) {
                    this.bookManagement.addBook(newBook);
                    this.bookManagement.saveBooksToFile();
                    this.bookManagement.loadBooksFromFile();
                    int totalBooks = this.bookManagement.getBooks().size();
                    currentPage = (totalBooks - 1) / rowsPerPage;
                    loadTableData();
                    JOptionPane.showMessageDialog(this, "Book added successfully!", "Success",
                            JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(this, "Error creating book. Please check your input.",
                            "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        deleteButton.addActionListener(e -> {
            int selectedRow = table.getSelectedRow();
            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(this, "Please select a book to delete!", "Warning",
                        JOptionPane.WARNING_MESSAGE);
                return;
            }

            int actualIndex = currentPage * rowsPerPage + selectedRow;
            Book selectedBook = this.bookManagement.getBooks().get(actualIndex);

            int confirm = JOptionPane.showConfirmDialog(this,
                    "Are you sure you want to delete:\n" + selectedBook.getTitle() + "?",
                    "Confirm Delete", JOptionPane.YES_NO_OPTION);

            if (confirm == JOptionPane.YES_OPTION) {
                this.bookManagement.getBooks().remove(actualIndex);
                this.bookManagement.saveBooksToFile();

                // Adjust currentPage if needed
                int totalBooks = this.bookManagement.getBooks().size();
                int totalPages = (totalBooks + rowsPerPage - 1) / rowsPerPage;
                if (totalPages == 0)
                    totalPages = 1;
                if (currentPage >= totalPages) {
                    currentPage = totalPages - 1;
                }

                loadTableData();
                JOptionPane.showMessageDialog(this, "Book deleted successfully!", "Success",
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
        int totalPages = (this.bookManagement.getBooks().size() + rowsPerPage - 1) / rowsPerPage;
        if (currentPage < totalPages) {
            currentPage++;
            loadTableData();
        }
    }

    public void loadTableData() {
        this.bookManagement.loadBooksFromFile();
        tableModel.setRowCount(0);
        int startIndex = currentPage * rowsPerPage;
        int endIndex = Math.min(startIndex + rowsPerPage, this.bookManagement.getBooks().size());

        for (int i = startIndex; i < endIndex; i++) {
            Book book = this.bookManagement.getBooks().get(i);
            Object[] rowData = {
                    i + 1,
                    book.getIsbn(),
                    book.getTitle(),
                    book.getAuthor(),
                    book.getPublisher(),
                    book.getPublicationYear(),
                    book.getCategory(),
                    String.format("%.2f", book.getPrice()),
                    book.getQuantity()
            };
            tableModel.addRow(rowData);
        }

        int totalPages = (this.bookManagement.getBooks().size() + rowsPerPage - 1) / rowsPerPage;
        if (totalPages == 0)
            totalPages = 1;
        pageLabel.setText("Page " + (currentPage + 1) + " / " + totalPages);

        prevButton.setEnabled(currentPage > 0);
        nextButton.setEnabled(currentPage < totalPages - 1);
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
}
