import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.time.LocalDate;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

public class ReturnBorrowDialog extends JDialog {
    private BorrowRecord record;
    private BookManagement bookManagement;
    private boolean submitted = false;
    private ArrayList<String> missingBooks; // ISBN của sách bị mất
    private BorrowRecordManagement borrowRecordManagement;

    public ReturnBorrowDialog(JFrame parent, BorrowRecord record, BookManagement bookManagement) {
        super(parent, "Trả Sách", true);
        this.record = record;
        this.bookManagement = bookManagement;
        this.borrowRecordManagement = new BorrowRecordManagement();
        this.missingBooks = new ArrayList<>();

        this.setSize(600, 500);
        this.setLocationRelativeTo(parent);
        this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        this.setResizable(false);

        initializeDialog();
    }

    public ReturnBorrowDialog(JFrame parent, BorrowRecord record, BorrowRecordManagement borrowRecordManagement) {
        super(parent, "Return Borrow", true);
        this.record = record;
        this.bookManagement = new BookManagement();
        this.borrowRecordManagement = borrowRecordManagement;
        this.missingBooks = new ArrayList<>();

        this.setSize(600, 500);
        this.setLocationRelativeTo(parent);
        this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        this.setResizable(false);

        initializeDialog();
    }

    private void initializeDialog() {

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(Color.WHITE);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        JPanel infoPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        infoPanel.setBackground(Color.WHITE);
        infoPanel.setBorder(BorderFactory.createTitledBorder("Thông Tin Phiếu Mượn"));

        JLabel recordInfo = new JLabel("<html>Mã Phiếu: " + record.getRecordId() + "<br>" +
                "Mã Độc Giả: " + record.getReaderId() + "<br>" +
                "Ngày Mượn: " + record.getBorrowDate() + "<br>" +
                "Ngày Trả DK: " + record.getExpectedReturnDate() + "</html>");
        recordInfo.setFont(new Font("Arial", Font.PLAIN, 12));

        infoPanel.add(recordInfo);

        JPanel booksPanel = new JPanel(new BorderLayout());
        booksPanel.setBackground(Color.WHITE);
        booksPanel.setBorder(BorderFactory.createTitledBorder("Danh Sách Sách"));

        DefaultListModel<String> listModel = new DefaultListModel<>();
        for (String isbn : record.getBorrowedISBNs()) {
            Book book = bookManagement.getBookByISBN(isbn);
            String bookName = book != null ? book.getTitle() + " (" + isbn + ")" : "Unknown - " + isbn;
            listModel.addElement(bookName);
        }

        JList<String> booksList = new JList<>(listModel);
        booksList.setFont(new Font("Arial", Font.PLAIN, 12));
        booksList.setBackground(new Color(240, 240, 240));

        JScrollPane scrollPane = new JScrollPane(booksList);
        scrollPane.setPreferredSize(new Dimension(550, 150));

        booksPanel.add(scrollPane, BorderLayout.CENTER);

        JPanel missingPanel = new JPanel(new BorderLayout());
        missingPanel.setBackground(Color.WHITE);
        missingPanel.setBorder(BorderFactory.createTitledBorder("Sách Bị Mất"));

        DefaultListModel<String> missingListModel = new DefaultListModel<>();
        for (String isbn : record.getBorrowedISBNs()) {
            missingListModel.addElement(isbn);
        }

        JList<String> missingList = new JList<>(missingListModel);
        missingList.setFont(new Font("Arial", Font.PLAIN, 12));
        missingList.setBackground(new Color(240, 240, 240));

        JScrollPane missingScrollPane = new JScrollPane(missingList);
        missingScrollPane.setPreferredSize(new Dimension(550, 100));

        JButton markMissingButton = new JButton("Đánh Dấu Bị Mất");
        markMissingButton.setFont(new Font("Arial", Font.BOLD, 11));
        markMissingButton.setBackground(new Color(255, 107, 107));
        markMissingButton.setForeground(Color.WHITE);
        markMissingButton.setPreferredSize(new Dimension(120, 30));

        JPanel missingButtonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        missingButtonPanel.setBackground(Color.WHITE);
        missingButtonPanel.add(markMissingButton);

        missingPanel.add(missingScrollPane, BorderLayout.CENTER);
        missingPanel.add(missingButtonPanel, BorderLayout.SOUTH);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        buttonPanel.setBackground(Color.WHITE);

        JButton submitButton = new JButton("Xác Nhận Trả");
        submitButton.setFont(new Font("Arial", Font.BOLD, 12));
        submitButton.setBackground(new Color(76, 175, 80));
        submitButton.setForeground(Color.WHITE);
        submitButton.setPreferredSize(new Dimension(120, 35));

        JButton cancelButton = new JButton("Hủy");
        cancelButton.setFont(new Font("Arial", Font.BOLD, 12));
        cancelButton.setBackground(new Color(244, 67, 54));
        cancelButton.setForeground(Color.WHITE);
        cancelButton.setPreferredSize(new Dimension(120, 35));

        buttonPanel.add(submitButton);
        buttonPanel.add(cancelButton);

        JPanel centerPanel = new JPanel(new BorderLayout());
        centerPanel.setBackground(Color.WHITE);
        centerPanel.add(booksPanel, BorderLayout.NORTH);
        centerPanel.add(missingPanel, BorderLayout.CENTER);

        mainPanel.add(infoPanel, BorderLayout.NORTH);
        mainPanel.add(centerPanel, BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        this.add(mainPanel);

        markMissingButton.addActionListener(e -> {
            int selectedIndex = missingList.getSelectedIndex();
            if (selectedIndex != -1) {
                String selectedISBN = missingListModel.getElementAt(selectedIndex);
                if (!missingBooks.contains(selectedISBN)) {
                    missingBooks.add(selectedISBN);
                    missingListModel.remove(selectedIndex);
                    JOptionPane.showMessageDialog(this, "Đã đánh dấu sách bị mất!", "Thông báo",
                            JOptionPane.INFORMATION_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(this, "Vui lòng chọn sách!", "Thông báo",
                        JOptionPane.WARNING_MESSAGE);
            }
        });

        submitButton.addActionListener(e -> {
            long overduePenalty = record.calculateOverduePenalty();
            long missingPenalty = calculateMissingPenalty();
            long totalPenalty = overduePenalty + missingPenalty;

            String penaltyInfo = "Phạt Quá Hạn: " + overduePenalty + " VNĐ\n" +
                    "Phạt Mất Sách: " + missingPenalty + " VNĐ\n" +
                    "Tổng Cộng: " + totalPenalty + " VNĐ";

            int confirm = JOptionPane.showConfirmDialog(this,
                    "Xác nhận trả sách?\n\n" + penaltyInfo,
                    "Xác Nhận",
                    JOptionPane.YES_NO_OPTION);

            if (confirm == JOptionPane.YES_OPTION) {
                // Mark as returned
                record.setActualReturnDate(LocalDate.now());
                borrowRecordManagement.updateRecord(record.getRecordId(), record);
                submitted = true;
                this.dispose();
            }
        });

        cancelButton.addActionListener(e -> {
            submitted = false;
            this.dispose();
        });
    }

    private long calculateMissingPenalty() {
        long totalPenalty = 0;
        for (String isbn : missingBooks) {
            Book book = bookManagement.getBookByISBN(isbn);
            if (book != null) {
                double price = book.getPrice();
                long penalty = (long) (price * 2); // 200% of price
                totalPenalty += penalty;
            }
        }
        return totalPenalty;
    }

    public boolean isSubmitted() {
        return submitted;
    }
}
