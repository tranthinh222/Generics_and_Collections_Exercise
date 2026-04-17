package src.panels;

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

import src.dialogs.BorrowDetailDialog;
import src.dialogs.CreateBorrowDialog;
import src.domain.BorrowRecord;
import src.management.BorrowRecordManagement;

public class BorrowManagementPanel extends JPanel {
    private JTable table;
    private DefaultTableModel tableModel;
    private BorrowRecordManagement borrowRecordManagement;
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

        add(tablePanel, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BorderLayout());
        buttonPanel.setBorder(BorderFactory.createLineBorder(new Color(50, 50, 50), 1));
        buttonPanel.setBackground(Color.WHITE);
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JPanel leftPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 0));
        leftPanel.setBackground(Color.WHITE);
        JButton createButton = createButton("Create Borrow", new Color(0, 172, 193));
        JButton detailsButton = createButton("Details", new Color(52, 152, 219));
        leftPanel.add(createButton);
        leftPanel.add(detailsButton);

        // Center side: Pagination
        JPanel centerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0));
        centerPanel.setBackground(Color.WHITE);

        JButton prevButtonCustom = new JButton("< Previous");
        prevButtonCustom.setFont(new Font("Arial", Font.BOLD, 14));
        prevButtonCustom.setPreferredSize(new Dimension(110, 30));
        prevButtonCustom.setBackground(new Color(0, 172, 193));
        prevButtonCustom.setForeground(Color.WHITE);
        prevButtonCustom.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));
        prevButtonCustom.setFocusPainted(false);
        prevButton = prevButtonCustom;

        pageLabel = new JLabel("Page 1 / 1");
        pageLabel.setFont(new Font("Arial", Font.BOLD, 14));

        JButton nextButtonCustom = new JButton("Next >");
        nextButtonCustom.setFont(new Font("Arial", Font.BOLD, 14));
        nextButtonCustom.setPreferredSize(new Dimension(110, 30));
        nextButtonCustom.setBackground(new Color(0, 172, 193));
        nextButtonCustom.setForeground(Color.WHITE);
        nextButtonCustom.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));
        nextButtonCustom.setFocusPainted(false);
        nextButton = nextButtonCustom;

        prevButton.addActionListener(l -> previousPage());
        nextButton.addActionListener(l -> nextPage());

        centerPanel.add(prevButton);
        centerPanel.add(pageLabel);
        centerPanel.add(nextButton);

        buttonPanel.add(leftPanel, BorderLayout.WEST);
        buttonPanel.add(centerPanel, BorderLayout.CENTER);

        add(buttonPanel, BorderLayout.SOUTH);

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

        detailsButton.addActionListener(e -> {
            int selectedRow = table.getSelectedRow();
            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(this, "Please select a borrow record!", "Warning",
                        JOptionPane.WARNING_MESSAGE);
                return;
            }

            int actualIndex = (currentPage * rowsPerPage) + selectedRow;
            if (actualIndex >= borrowRecordManagement.getRecords().size()) {
                JOptionPane.showMessageDialog(this, "Invalid selection!", "Error",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            BorrowRecord record = borrowRecordManagement.getRecords().get(actualIndex);
            BorrowDetailDialog detailDialog = new BorrowDetailDialog(
                    (JFrame) SwingUtilities.getWindowAncestor(this), record);
            detailDialog.setVisible(true);
        });
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
        btn.setBackground(bgColor);
        btn.setForeground(Color.WHITE);
        btn.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));
        btn.setFocusPainted(false);
        return btn;
    }
}
