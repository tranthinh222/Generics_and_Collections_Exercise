import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

public class LibraryManagementSystem extends JFrame {
    private JPanel contentPanel;
    private CardLayout cardLayout;
    private LoginPanel loginPanel;
    private ReaderManagementPanel readerManagementPanel;
    private BookManagementPanel bookManagementPanel;
    private BorrowManagementPanel borrowManagementPanel;
    private ReturnManagementPanel returnManagementPanel;
    private LibrarianManagement librarianManagement;
    private JButton btn1, btn2, btn3, btn4, btn5, btn6;

    public LibraryManagementSystem() {
        this.setTitle("Library Management System");
        this.setSize(900, 600);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setBackground(Color.WHITE);
        panel.setLayout(new BorderLayout());

        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(new Color(5, 148, 237));
        headerPanel.setBorder(BorderFactory.createEmptyBorder(12, 20, 12, 20));

        JLabel label = new JLabel();
        label.setText("Library Management System");
        label.setFont(new Font("Arial", Font.BOLD, 24));
        label.setForeground(Color.WHITE);
        headerPanel.add(label, BorderLayout.WEST);

        JPanel menuPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 0));
        menuPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
        menuPanel.setBackground(new Color(5, 148, 237));

        Font buttonFont = new Font("Arial", Font.PLAIN, 12);
        btn1 = createButton("Login", buttonFont);
        btn2 = createButton("Readers", buttonFont);
        btn3 = createButton("Books", buttonFont);
        btn4 = createButton("Borrow", buttonFont);
        btn5 = createButton("Return", buttonFont);
        btn6 = createButton("Statistics", buttonFont);

        menuPanel.add(btn1);
        menuPanel.add(btn2);
        menuPanel.add(btn3);
        menuPanel.add(btn4);
        menuPanel.add(btn5);
        menuPanel.add(btn6);

        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBackground(new Color(5, 148, 237));
        topPanel.add(headerPanel, BorderLayout.NORTH);
        topPanel.add(menuPanel, BorderLayout.CENTER);

        this.cardLayout = new CardLayout();
        this.contentPanel = new JPanel(cardLayout);
        contentPanel.setBackground(Color.WHITE);
        contentPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));

        this.loginPanel = new LoginPanel();
        this.readerManagementPanel = new ReaderManagementPanel();
        this.bookManagementPanel = new BookManagementPanel();
        this.borrowManagementPanel = new BorrowManagementPanel();
        this.returnManagementPanel = new ReturnManagementPanel();
        contentPanel.add(loginPanel, "Login");
        contentPanel.add(readerManagementPanel, "Readers");
        contentPanel.add(bookManagementPanel, "Books");
        contentPanel.add(borrowManagementPanel, "Borrow");
        contentPanel.add(returnManagementPanel, "Return");

        panel.add(topPanel, BorderLayout.NORTH);
        panel.add(contentPanel, BorderLayout.CENTER);

        this.add(panel);
        this.setVisible(true);

        librarianManagement = new LibrarianManagement();
        librarianManagement.loadLibrarianListFromFile();

        // Set initial active button color
        setActiveButton(btn1);

        btn1.addActionListener(l -> {
            setActiveButton(btn1);
            this.cardLayout.show(contentPanel, "Login");
        });
        btn2.addActionListener(l -> {
            setActiveButton(btn2);
            this.readerManagementPanel.loadTableData();
            this.cardLayout.show(contentPanel, "Readers");
        });
        btn3.addActionListener(l -> {
            setActiveButton(btn3);
            this.bookManagementPanel.loadTableData();
            this.cardLayout.show(contentPanel, "Books");
        });
        btn4.addActionListener(l -> {
            setActiveButton(btn4);
            this.borrowManagementPanel.loadTableData();
            this.cardLayout.show(contentPanel, "Borrow");
        });
        btn5.addActionListener(l -> {
            setActiveButton(btn5);
            this.returnManagementPanel.loadTableData();
            this.cardLayout.show(contentPanel, "Return");
        });

        loginPanel.loginButton.addActionListener(l -> {
            String name = loginPanel.getUsername();
            String password = loginPanel.getPassword();

            loginPanel.clearError();

            if (name.isEmpty()) {
                loginPanel.showError("Username không được trống!");
            }

            if (password.isEmpty())
                loginPanel.showError("Password không được trống!");

            if (password.length() < 6)
                loginPanel.showError("Password phải >= 6 ký tự!");

            if (librarianManagement.checkValidAccount(name, password)) {
                this.remove(btn1);
                JOptionPane.showMessageDialog(this, "Đăng nhập thành công!", "Thành công",
                        JOptionPane.INFORMATION_MESSAGE);
            } else {
                loginPanel.showError("Username hoặc password không đúng!");
            }

        });

        loginPanel.createdButton.addActionListener(e -> {
            CreateAccountDiaLog dialog = new CreateAccountDiaLog();
            dialog.setVisible(true);
            if (dialog.isCreated()) {
                loginPanel.usernameField.setText(dialog.getUsername());
                loginPanel.passwordField.setText("");
            }
        });
    }

    private void setActiveButton(JButton activeButton) {
        // Reset all buttons to default color
        btn1.setBackground(new Color(0, 172, 193));
        btn2.setBackground(new Color(0, 172, 193));
        btn3.setBackground(new Color(0, 172, 193));
        btn4.setBackground(new Color(0, 172, 193));
        btn5.setBackground(new Color(0, 172, 193));
        btn6.setBackground(new Color(0, 172, 193));

        // Set active button to darker color
        activeButton.setBackground(new Color(0, 130, 150));
    }

    public JButton createButton(String text, Font font) {
        JButton button = new JButton(text);
        button.setFont(font);
        button.setBackground(new Color(0, 172, 193));
        button.setForeground(Color.WHITE);
        button.setBorder(BorderFactory.createEmptyBorder(6, 12, 6, 12));
        button.setFocusPainted(false);
        return button;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new LibraryManagementSystem());
    }
}
