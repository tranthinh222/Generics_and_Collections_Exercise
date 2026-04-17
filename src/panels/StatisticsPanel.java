package src.panels;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import src.domain.Book;
import src.domain.BorrowRecord;
import src.domain.Reader;
import src.management.BookManagement;
import src.management.BorrowRecordManagement;
import src.management.ReaderManagement;

public class StatisticsPanel extends JPanel {
    private BookManagement bookManagement;
    private ReaderManagement readerManagement;
    private BorrowRecordManagement borrowRecordManagement;
    private JTextArea statsTextArea;

    public StatisticsPanel() {
        this.setLayout(new BorderLayout());
        this.setBackground(Color.WHITE);
        this.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        bookManagement = new BookManagement();
        readerManagement = new ReaderManagement();
        borrowRecordManagement = new BorrowRecordManagement();

        JLabel headerLabel = new JLabel("Thống Kê Hệ Thống Thư Viện");
        headerLabel.setFont(new Font("Arial", Font.BOLD, 20));
        headerLabel.setForeground(new Color(5, 148, 237));
        headerLabel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        this.add(headerLabel, BorderLayout.NORTH);

        statsTextArea = new JTextArea();
        statsTextArea.setFont(new Font("Courier New", Font.PLAIN, 12));
        statsTextArea.setEditable(false);
        statsTextArea.setBackground(new Color(240, 240, 240));
        statsTextArea.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JScrollPane scrollPane = new JScrollPane(statsTextArea);
        this.add(scrollPane, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(Color.WHITE);
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));

        JButton refreshButton = new JButton("Refresh");
        refreshButton.setFont(new Font("Arial", Font.BOLD, 12));
        refreshButton.setBackground(new Color(0, 172, 193));
        refreshButton.setForeground(Color.WHITE);
        refreshButton.setBorder(BorderFactory.createEmptyBorder(8, 15, 8, 15));
        refreshButton.addActionListener(e -> loadStatistics());

        buttonPanel.add(refreshButton);
        this.add(buttonPanel, BorderLayout.SOUTH);
    }

    public void loadStatistics() {
        bookManagement.loadBooksFromFile();
        readerManagement.loadReadersFromFile();
        borrowRecordManagement.loadRecordsFromFile();

        StringBuilder stats = new StringBuilder();

        stats.append("\n");
        stats.append("═══════════════════════════════════════════════════════════════\n");
        stats.append("1. THỐNG KÊ SỐ LƯỢNG SÁCH TRONG THƯ VIỆN\n");
        stats.append("═══════════════════════════════════════════════════════════════\n");
        stats.append("\n");
        int totalBooks = bookManagement.getBooks().size();
        stats.append("   Tổng số sách: ").append(totalBooks).append(" cuốn\n");
        stats.append("\n");

        stats.append("═══════════════════════════════════════════════════════════════\n");
        stats.append("2. THỐNG KÊ SỐ LƯỢNG SÁCH THEO THỂ LOẠI\n");
        stats.append("═══════════════════════════════════════════════════════════════\n");
        stats.append("\n");
        Map<String, Integer> categoryCount = countByCategory();
        if (categoryCount.isEmpty()) {
            stats.append("   Không có dữ liệu\n");
        } else {
            for (String category : categoryCount.keySet()) {
                int count = categoryCount.get(category);
                stats.append("   • ").append(category).append(": ")
                        .append(count).append(" cuốn\n");
            }
        }
        stats.append("\n");

        stats.append("═══════════════════════════════════════════════════════════════\n");
        stats.append("3. THỐNG KÊ SỐ LƯỢNG ĐỘC GIẢ\n");
        stats.append("═══════════════════════════════════════════════════════════════\n");
        stats.append("\n");
        int totalReaders = readerManagement.getReaders().size();
        stats.append("   Tổng số độc giả: ").append(totalReaders).append(" người\n");
        stats.append("\n");

        stats.append("═══════════════════════════════════════════════════════════════\n");
        stats.append("4. THỐNG KÊ ĐỘC GIẢ THEO GIỚI TÍNH\n");
        stats.append("═══════════════════════════════════════════════════════════════\n");
        stats.append("\n");

        int maleCount = 0, femaleCount = 0, otherCount = 0;
        for (Reader reader : readerManagement.getReaders()) {
            if ("Nam".equals(reader.getGender())) {
                maleCount++;
            } else if ("Nữ".equals(reader.getGender())) {
                femaleCount++;
            } else {
                otherCount++;
            }
        }
        stats.append("   • Nam: ").append(maleCount).append(" người\n");
        stats.append("   • Nữ: ").append(femaleCount).append(" người\n");
        if (otherCount > 0) {
            stats.append("   • Khác: ").append(otherCount).append(" người\n");
        }
        stats.append("\n");

        stats.append("═══════════════════════════════════════════════════════════════\n");
        stats.append("5. THỐNG KÊ SỐ SÁCH ĐANG ĐƯỢC MƯỢN\n");
        stats.append("═══════════════════════════════════════════════════════════════\n");
        stats.append("\n");

        int borrowingCount = 0;
        int totalBorrowingBooks = 0;
        for (BorrowRecord record : borrowRecordManagement.getRecords()) {
            if ("BORROWING".equals(record.getStatus())) {
                borrowingCount++;
                totalBorrowingBooks += record.getBorrowedISBNs().size();
            }
        }
        stats.append("   • Số phiếu đang mượn: ").append(borrowingCount).append(" phiếu\n");
        stats.append("   • Tổng số sách đang mượn: ").append(totalBorrowingBooks).append(" cuốn\n");
        stats.append("\n");

        stats.append("═══════════════════════════════════════════════════════════════\n");
        stats.append("6. DANH SÁCH ĐỘC GIẢ BỊ TRỄ HẠN\n");
        stats.append("═══════════════════════════════════════════════════════════════\n");
        stats.append("\n");

        Map<String, List<BorrowRecord>> overdueRecords = getOverdueRecords();
        if (overdueRecords.isEmpty()) {
            stats.append("    Không có độc giả bị trễ hạn\n");
        } else {
            int index = 1;
            for (String readerId : overdueRecords.keySet()) {
                Reader reader = findReaderById(readerId);
                String readerName = reader != null ? reader.getName() : "Unknown";
                stats.append("   ").append(index).append(". ").append(readerName)
                        .append(" (ID: ").append(readerId).append(")\n");

                List<BorrowRecord> records = overdueRecords.get(readerId);
                for (BorrowRecord record : records) {
                    long daysOverdue = java.time.temporal.ChronoUnit.DAYS
                            .between(record.getExpectedReturnDate(), LocalDate.now());
                    long penalty = record.calculateOverduePenalty();
                    stats.append("      • Phiếu: ").append(record.getRecordId())
                            .append(" | Trễ: ").append(daysOverdue).append(" ngày | Phạt: ")
                            .append(penalty).append(" VNĐ\n");
                }
                index++;
            }
        }
        stats.append("\n");

        statsTextArea.setText(stats.toString());
        statsTextArea.setCaretPosition(0);
    }

    private Map<String, Integer> countByCategory() {
        Map<String, Integer> categoryMap = new HashMap<>();
        for (Book book : bookManagement.getBooks()) {
            String category = book.getCategory();
            categoryMap.put(category, categoryMap.getOrDefault(category, 0) + 1);
        }
        return categoryMap;
    }

    private Map<String, List<BorrowRecord>> getOverdueRecords() {
        Map<String, List<BorrowRecord>> overdueMap = new HashMap<>();
        LocalDate today = LocalDate.now();

        for (BorrowRecord record : borrowRecordManagement.getRecords()) {
            if ("BORROWING".equals(record.getStatus()) &&
                    today.isAfter(record.getExpectedReturnDate())) {
                String readerId = record.getReaderId();
                overdueMap.putIfAbsent(readerId, new ArrayList<>());
                overdueMap.get(readerId).add(record);
            }
        }
        return overdueMap;
    }

    private Reader findReaderById(String readerId) {
        for (Reader reader : readerManagement.getReaders()) {
            if (reader.getReaderId().equals(readerId)) {
                return reader;
            }
        }
        return null;
    }
}
