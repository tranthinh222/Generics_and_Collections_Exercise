package src.dialogs;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import src.domain.BorrowRecord;

public class BorrowDetailDialog extends JDialog {
    private BorrowRecord record;

    public BorrowDetailDialog(JFrame parent, BorrowRecord record) {
        super(parent, "Borrow Record Details", true);
        this.record = record;
        this.setSize(600, 500);
        this.setLocationRelativeTo(parent);
        this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

        initializeDialog();
    }

    private void initializeDialog() {
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(Color.WHITE);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        // Info Panel
        JPanel infoPanel = new JPanel(new GridLayout(0, 2, 10, 10));
        infoPanel.setBackground(Color.WHITE);
        infoPanel.setBorder(BorderFactory.createTitledBorder("Thông Tin Chung"));

        addInfoRow(infoPanel, "Mã Phiếu:", record.getRecordId());
        addInfoRow(infoPanel, "Mã Độc Giả:", record.getReaderId());
        addInfoRow(infoPanel, "Ngày Mượn:", record.getBorrowDate().toString());
        addInfoRow(infoPanel, "Ngày Trả Dự Kiến:", record.getExpectedReturnDate().toString());
        addInfoRow(infoPanel, "Ngày Trả Thực Tế:",
                record.getActualReturnDate() != null ? record.getActualReturnDate().toString() : "Chưa trả");
        addInfoRow(infoPanel, "Trạng Thái:", record.getStatus());
        addInfoRow(infoPanel, "Phạt (VNĐ):", String.valueOf(record.calculateOverduePenalty()));

        JPanel isbnPanel = new JPanel(new BorderLayout());
        isbnPanel.setBackground(Color.WHITE);
        isbnPanel.setBorder(BorderFactory.createTitledBorder("Danh Sách ISBN Được Mượn"));

        StringBuilder isbnList = new StringBuilder();
        for (int i = 0; i < record.getBorrowedISBNs().size(); i++) {
            isbnList.append((i + 1)).append(". ").append(record.getBorrowedISBNs().get(i)).append("\n");
        }

        JTextArea isbnTextArea = new JTextArea(isbnList.toString());
        isbnTextArea.setFont(new Font("Arial", Font.PLAIN, 12));
        isbnTextArea.setEditable(false);
        isbnTextArea.setBackground(new Color(240, 240, 240));
        isbnTextArea.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JScrollPane isbnScrollPane = new JScrollPane(isbnTextArea);
        isbnPanel.add(isbnScrollPane, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(Color.WHITE);
        JButton closeButton = new JButton("Đóng");
        closeButton.setFont(new Font("Arial", Font.PLAIN, 12));
        closeButton.setBackground(new Color(0, 172, 193));
        closeButton.setForeground(Color.WHITE);
        closeButton.setPreferredSize(new Dimension(100, 35));
        closeButton.addActionListener(e -> dispose());
        buttonPanel.add(closeButton);

        mainPanel.add(infoPanel, BorderLayout.NORTH);
        mainPanel.add(isbnPanel, BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        this.add(mainPanel);
    }

    private void addInfoRow(JPanel panel, String label, String value) {
        JLabel labelComponent = new JLabel(label);
        labelComponent.setFont(new Font("Arial", Font.BOLD, 12));

        JLabel valueComponent = new JLabel(value);
        valueComponent.setFont(new Font("Arial", Font.PLAIN, 12));

        panel.add(labelComponent);
        panel.add(valueComponent);
    }
}
