package src.dialogs;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import src.domain.Book;
import src.domain.Reader;
import src.management.BookManagement;
import src.management.ReaderManagement;

public class CreateBorrowDialog extends JDialog {
    private JComboBox<String> readerCombo;
    private JComboBox<String> bookCombo;
    private JList<String> selectedBooksList;
    private DefaultListModel<String> listModel;
    private boolean submitted = false;
    private String selectedReaderId;
    private ArrayList<String> selectedISBNs;
    private ReaderManagement readerManagement;
    private BookManagement bookManagement;

    public CreateBorrowDialog(JFrame parent) {
        super(parent, "Lập Phiếu Mượn Sách", true);
        this.setSize(500, 500);
        this.setLocationRelativeTo(parent);
        this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        this.setResizable(false);

        readerManagement = new ReaderManagement();
        bookManagement = new BookManagement();

        readerManagement.loadReadersFromFile();
        bookManagement.loadBooksFromFile();

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(Color.WHITE);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        // Reader selection panel
        JPanel readerPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        readerPanel.setBackground(Color.WHITE);
        readerPanel.setBorder(BorderFactory.createTitledBorder("Chọn Độc Giả"));

        JLabel readerLabel = new JLabel("Độc Giả:");
        readerLabel.setFont(new Font("Arial", Font.BOLD, 12));

        String[] readerNames = new String[readerManagement.getReaders().size() + 1];
        readerNames[0] = "-- Chọn độc giả --";
        for (int i = 0; i < readerManagement.getReaders().size(); i++) {
            Reader reader = readerManagement.getReaders().get(i);
            readerNames[i + 1] = reader.getReaderId() + " - " + reader.getName();
        }

        readerCombo = new JComboBox<>(readerNames);
        readerCombo.setFont(new Font("Arial", Font.PLAIN, 12));
        readerCombo.setPreferredSize(new Dimension(300, 30));

        readerPanel.add(readerLabel);
        readerPanel.add(readerCombo);

        // Book selection panel
        JPanel bookPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        bookPanel.setBackground(Color.WHITE);
        bookPanel.setBorder(BorderFactory.createTitledBorder("Chọn Sách"));

        JLabel bookLabel = new JLabel("Sách:");
        bookLabel.setFont(new Font("Arial", Font.BOLD, 12));

        String[] bookTitles = new String[bookManagement.getBooks().size() + 1];
        bookTitles[0] = "-- Chọn sách --";
        for (int i = 0; i < bookManagement.getBooks().size(); i++) {
            Book book = bookManagement.getBooks().get(i);
            bookTitles[i + 1] = book.getIsbn() + " - " + book.getTitle();
        }

        bookCombo = new JComboBox<>(bookTitles);
        bookCombo.setFont(new Font("Arial", Font.PLAIN, 12));
        bookCombo.setPreferredSize(new Dimension(300, 30));

        JButton addButton = new JButton("Thêm");
        addButton.setFont(new Font("Arial", Font.BOLD, 12));
        addButton.setBackground(new Color(0, 172, 193));
        addButton.setForeground(Color.WHITE);
        addButton.setPreferredSize(new Dimension(80, 30));

        bookPanel.add(bookLabel);
        bookPanel.add(bookCombo);
        bookPanel.add(addButton);

        // Selected books list
        JPanel listPanel = new JPanel(new BorderLayout());
        listPanel.setBackground(Color.WHITE);
        listPanel.setBorder(BorderFactory.createTitledBorder("Danh Sách Sách Được Chọn"));

        listModel = new DefaultListModel<>();
        selectedBooksList = new JList<>(listModel);
        selectedBooksList.setFont(new Font("Arial", Font.PLAIN, 12));
        selectedBooksList.setBackground(new Color(240, 240, 240));

        JScrollPane scrollPane = new JScrollPane(selectedBooksList);
        scrollPane.setPreferredSize(new Dimension(450, 150));

        JButton removeButton = new JButton("Xóa");
        removeButton.setFont(new Font("Arial", Font.BOLD, 12));
        removeButton.setBackground(new Color(255, 107, 107));
        removeButton.setForeground(Color.WHITE);
        removeButton.setPreferredSize(new Dimension(80, 30));

        JPanel removePanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        removePanel.setBackground(Color.WHITE);
        removePanel.add(removeButton);

        listPanel.add(scrollPane, BorderLayout.CENTER);
        listPanel.add(removePanel, BorderLayout.SOUTH);

        // Button panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        buttonPanel.setBackground(Color.WHITE);

        JButton submitButton = new JButton("Lập Phiếu");
        submitButton.setFont(new Font("Arial", Font.BOLD, 12));
        submitButton.setBackground(new Color(76, 175, 80));
        submitButton.setForeground(Color.WHITE);
        submitButton.setPreferredSize(new Dimension(100, 35));

        JButton cancelButton = new JButton("Hủy");
        cancelButton.setFont(new Font("Arial", Font.BOLD, 12));
        cancelButton.setBackground(new Color(244, 67, 54));
        cancelButton.setForeground(Color.WHITE);
        cancelButton.setPreferredSize(new Dimension(100, 35));

        buttonPanel.add(submitButton);
        buttonPanel.add(cancelButton);

        // Add panels to main
        mainPanel.add(readerPanel, BorderLayout.NORTH);

        JPanel centerPanel = new JPanel(new BorderLayout());
        centerPanel.setBackground(Color.WHITE);
        centerPanel.add(bookPanel, BorderLayout.NORTH);
        centerPanel.add(listPanel, BorderLayout.CENTER);

        mainPanel.add(centerPanel, BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        this.add(mainPanel);

        // Event listeners
        addButton.addActionListener(e -> {
            int selectedIndex = bookCombo.getSelectedIndex();
            if (selectedIndex == 0) {
                JOptionPane.showMessageDialog(this, "Vui lòng chọn sách!", "Thông báo",
                        JOptionPane.WARNING_MESSAGE);
                return;
            }

            String selectedBook = bookCombo.getSelectedItem().toString();
            if (!listModel.contains(selectedBook)) {
                listModel.addElement(selectedBook);
            } else {
                JOptionPane.showMessageDialog(this, "Sách này đã được thêm rồi!", "Thông báo",
                        JOptionPane.WARNING_MESSAGE);
            }
        });

        removeButton.addActionListener(e -> {
            int selectedIndex = selectedBooksList.getSelectedIndex();
            if (selectedIndex != -1) {
                listModel.remove(selectedIndex);
            } else {
                JOptionPane.showMessageDialog(this, "Vui lòng chọn sách để xóa!", "Thông báo",
                        JOptionPane.WARNING_MESSAGE);
            }
        });

        submitButton.addActionListener(e -> {
            // Validate reader
            int readerIndex = readerCombo.getSelectedIndex();
            if (readerIndex == 0) {
                JOptionPane.showMessageDialog(this, "Vui lòng chọn độc giả!", "Thông báo",
                        JOptionPane.WARNING_MESSAGE);
                return;
            }

            // Validate books
            if (listModel.size() == 0) {
                JOptionPane.showMessageDialog(this, "Vui lòng chọn ít nhất một sách!", "Thông báo",
                        JOptionPane.WARNING_MESSAGE);
                return;
            }

            // Extract reader ID
            String readerSelection = readerCombo.getSelectedItem().toString();
            selectedReaderId = readerSelection.split(" - ")[0];

            // Extract ISBNs
            selectedISBNs = new ArrayList<>();
            for (int i = 0; i < listModel.size(); i++) {
                String bookItem = listModel.getElementAt(i);
                String isbn = bookItem.split(" - ")[0];
                selectedISBNs.add(isbn);
            }

            submitted = true;
            this.dispose();
        });

        cancelButton.addActionListener(e -> {
            submitted = false;
            this.dispose();
        });
    }

    public boolean isSubmitted() {
        return submitted;
    }

    public String getSelectedReaderId() {
        return selectedReaderId;
    }

    public ArrayList<String> getSelectedISBNs() {
        return selectedISBNs;
    }
}
