import java.awt.*;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import javax.swing.*;
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

    public ReaderManagementPanel(){
        this.setLayout(new BorderLayout());
        this.setBackground(Color.WHITE);
        this.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        this.readerManagement = new ReaderManagement();

        JPanel searchPanel = new JPanel();
        searchPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 10));
        searchPanel.setBackground(Color.WHITE);
        searchPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
        
        JLabel label = new JLabel("Search By:");
        label.setFont(new Font("Arial", Font.BOLD, 12));
        searchTypeCombo = new JComboBox<>(new String[]{"Họ tên", "Mã độc giả"});
        searchField = new JTextField(20);
        searchField.setFont(new Font("Arial", Font.PLAIN, 12));
        searchField.setPreferredSize(new Dimension(50, 25));
        searchButton = createButton("Search", new Color(0, 188, 212));


        searchPanel.add(label);
        searchPanel.add(searchTypeCombo);
        searchPanel.add(searchField);
        searchPanel.add(searchButton);

        String[] columns = { "ID", "Mã độc giả", "Họ tên", "Ngày sinh", "Giới tính", "Email", "Địa chỉ", "Ngày lập thẻ", "Ngày hết hạn"};
        this.tableModel = new DefaultTableModel(columns, 0);
        this.table = new JTable(tableModel);
        table.setFont(new Font("Arial", Font.PLAIN, 12));
        table.setRowHeight(25);
        table.setSelectionBackground(new Color(0, 172, 193));

        JPanel tablePanel = new JPanel(new BorderLayout());
        tablePanel.setBackground(Color.WHITE);
        tablePanel.add(table.getTableHeader(), BorderLayout.NORTH);
        tablePanel.add(table, BorderLayout.CENTER);
        tablePanel.setBorder(BorderFactory.createLineBorder(new Color(50, 50, 50), 1));

        JPanel buttonPanel = new JPanel();
        // buttonPanel.setBackground(Color.WHITE);
        buttonPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 10));
        buttonPanel.setBorder(BorderFactory.createLineBorder(new Color(50, 50, 50), 1));
        
        this.addButton = createButton("Add", new Color(0, 172, 193));
        this.editButton = createButton("Edit", new Color(0, 172, 193));
        this.deleteButton = createButton("Delete", new Color(0, 172, 193));
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

    public void previousPage(){
        if (currentPage > 0)
        {
            currentPage--;
            loadTableData();
        }
            
    }

    public void nextPage(){
        int totalPages = (this.readerManagement.getReaders().size() + rowsPerPage - 1)/ rowsPerPage; 
        if (currentPage > 0){
            currentPage++;
            loadTableData();
        }
    }

    public void loadTableData(){
        tableModel.setRowCount(0);
        int startIndex = currentPage * rowsPerPage;
        int endIndex = Math.min(startIndex + rowsPerPage, this.readerManagement.getReaders().size());
        
        this.readerManagement.loadReadersFromFile();
        for (int i = startIndex; i < endIndex; i++) {
            Reader reader = this.readerManagement.getReaders().get(i);
            Object[] rowData = {
                i + 1,
                reader.getReaderId(),
                reader.getName(),
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

    public DefaultTableModel getTableModel(){
        return tableModel;
    }

    public void addRowToTable(Reader reader){
        this.readerManagement.getReaders().add(reader);
        currentPage = 0;
        loadTableData();
    }

    public void loadReadersToTable(java.util.ArrayList<Reader> readers){
        this.readerManagement.getReaders().clear();
        if (readers != null) {
            for (Reader reader : readers) {
                this.readerManagement.getReaders().add(reader);
            }
        }
        currentPage = 0;
        loadTableData();
    }

    private JButton createButton(String text, Color bgColor)
    {
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
