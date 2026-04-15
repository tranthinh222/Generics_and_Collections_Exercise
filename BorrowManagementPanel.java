import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.time.LocalDate;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;

public class BorrowManagementPanel extends JPanel {
    private JTable table;
    private DefaultTableModel tableModel;
    private BorrowRecordManagement borrowRecordManagement;
    private BookManagement bookManagement;
    private int currentPage = 0;
    private int rowsPerPage = 10;
    private JLabel pageLabel;
    private JButton prevButton;
    private JButton nextButton;

    public BorrowManagementPanel() {
        this.setLayout(new BorderLayout());
        this.setBackground(Color.WHITE);
        this.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        borrowRecordManagement = new BorrowRecordManagement();
        bookManagement = new BookManagement();

        // Button panel
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 10));
        buttonPanel.setBorder(BorderFactory.createLineBorder(new Color(50, 50, 50), 1));
        buttonPanel.setBackground(Color.WHITE);

        JButton createButton = createButton("Lập Phiếu", new Color(0, 172, 193));
        JButton viewDetailsButton = createButton("Chi Tiết", new Color(0, 172, 193));
        JButton returnButton = createButton("Trả Sách", new Color(0, 172, 193));

        buttonPanel.add(createButton);
        buttonPanel.add(viewDetailsButton);
        buttonPanel.add(returnButton);
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

        // Table
        String[] columns = { "ID", "Mã Phiếu", "Mã Độc Giả", "Ngày Mượn", "Ngày Trả DK",
                "Ngày Trả TT", "Số Sách", "Trạng Thái", "Phạt (VNĐ)" };
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

        add(buttonPanel, BorderLayout.NORTH);
        add(tablePanel, BorderLayout.CENTER);

        // Event listeners
        createButton.addActionListener(e -> {
            CreateBorrowDialog dialog = new CreateBorrowDialog((JFrame) SwingUtilities.getWindowAncestor(this));
            dialog.setVisible(true);

            if (dialog.isSubmitted()) {
                String readerId = dialog.getSelectedReaderId();
                ArrayList<String> isbns = dialog.getSelectedISBNs();

                String recordId = borrowRecordManagement.generateRecordId();
                BorrowRecord record = new BorrowRecord(recordId, readerId, LocalDate.now(), isbns);
                borrowRecordManagement.addRecord(record);
                loadTableData();
                JOptionPane.showMessageDialog(this, "Phiếu mượn lập thành công!", "Thành công",
                        JOptionPane.INFORMATION_MESSAGE);
            }
        });

        viewDetailsButton.addActionListener(e -> {
            int selectedRow = table.getSelectedRow();
            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(this, "Vui lòng chọn phiếu mượn!", "Thông báo",
                        JOptionPane.WARNING_MESSAGE);
                return;
            }

            int actualIndex = (currentPage * rowsPerPage) + selectedRow;
            if (actualIndex >= borrowRecordManagement.getRecords().size()) {
                JOptionPane.showMessageDialog(this, "Lựa chọn không hợp lệ!", "Lỗi",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            BorrowRecord record = borrowRecordManagement.getRecords().get(actualIndex);
            showBorrowDetails(record);
        });

        returnButton.addActionListener(e -> {
            int selectedRow = table.getSelectedRow();
            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(this, "Vui lòng chọn phiếu mượn!", "Thông báo",
                        JOptionPane.WARNING_MESSAGE);
                return;
            }

            int actualIndex = (currentPage * rowsPerPage) + selectedRow;
            if (actualIndex >= borrowRecordManagement.getRecords().size()) {
                JOptionPane.showMessageDialog(this, "Lựa chọn không hợp lệ!", "Lỗi",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            BorrowRecord record = borrowRecordManagement.getRecords().get(actualIndex);
            if (record.getStatus().equals("RETURNED")) {
                JOptionPane.showMessageDialog(this, "Phiếu này đã được trả rồi!", "Thông báo",
                        JOptionPane.WARNING_MESSAGE);
                return;
            }

            ReturnBorrowDialog returnDialog = new ReturnBorrowDialog(
                    (JFrame) SwingUtilities.getWindowAncestor(this), record, bookManagement);
            returnDialog.setVisible(true);

            if (returnDialog.isSubmitted()) {
                loadTableData();
                JOptionPane.showMessageDialog(this, "Phiếu trả đã được xử lý!", "Thành công",
                        JOptionPane.INFORMATION_MESSAGE);
            }
        });
    }

    private void showBorrowDetails(BorrowRecord record) {
        StringBuilder details = new StringBuilder();
        details.append("Mã Phiếu: ").append(record.getRecordId()).append("\n");
        details.append("Mã Độc Giả: ").append(record.getReaderId()).append("\n");
        details.append("Ngày Mượn: ").append(record.getBorrowDate()).append("\n");
        details.append("Ngày Trả DK: ").append(record.getExpectedReturnDate()).append("\n");

        if (record.getActualReturnDate() != null) {
            details.append("Ngày Trả TT: ").append(record.getActualReturnDate()).append("\n");
        } else {
            details.append("Ngày Trả TT: Chưa trả\n");
        }

        details.append("\nDanh Sách Sách:\n");
        for (String isbn : record.getBorrowedISBNs()) {
            Book book = bookManagement.getBookByISBN(isbn);
            if (book != null) {
                details.append("- ").append(book.getTitle()).append(" (").append(isbn).append(")\n");
            } else {
                details.append("- ISBN: ").append(isbn).append(" (Không tìm thấy)\n");
            }
        }

        details.append("\nTrạng Thái: ").append(record.getStatus()).append("\n");

        long penalty = record.calculateOverduePenalty();
        if (penalty > 0) {
            details.append("Phạt Quá Hạn: ").append(penalty).append(" VNĐ\n");
        }

        JOptionPane.showMessageDialog(this, details.toString(), "Chi Tiết Phiếu Mượn",
                JOptionPane.INFORMATION_MESSAGE);
    }

    public void previousPage() {
        if (currentPage > 0) {
            currentPage--;
            loadTableData();
        }
    }

    public void nextPage() {
        int totalPages = (borrowRecordManagement.getRecords().size() + rowsPerPage - 1) / rowsPerPage;
        if (currentPage < totalPages - 1) {
            currentPage++;
            loadTableData();
        }
    }

    public void loadTableData() {
        borrowRecordManagement.loadRecordsFromFile();
        tableModel.setRowCount(0);
        int startIndex = currentPage * rowsPerPage;
        int endIndex = Math.min(startIndex + rowsPerPage, borrowRecordManagement.getRecords().size());

        for (int i = startIndex; i < endIndex; i++) {
            BorrowRecord record = borrowRecordManagement.getRecords().get(i);
            long penalty = record.calculateOverduePenalty();
            String penaltyStr = penalty > 0 ? String.valueOf(penalty) : "-";

            Object[] rowData = {
                    i + 1,
                    record.getRecordId(),
                    record.getReaderId(),
                    record.getBorrowDate(),
                    record.getExpectedReturnDate(),
                    record.getActualReturnDate() != null ? record.getActualReturnDate().toString() : "-",
                    record.getBorrowedISBNs().size(),
                    record.getStatus(),
                    penaltyStr
            };
            tableModel.addRow(rowData);
        }

        int totalPages = (borrowRecordManagement.getRecords().size() + rowsPerPage - 1) / rowsPerPage;
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
