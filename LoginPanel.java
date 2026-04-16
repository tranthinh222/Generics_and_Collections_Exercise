import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class LoginPanel extends JPanel {
    public JTextField usernameField;
    public JPasswordField passwordField;
    public JButton createdButton;
    public JButton loginButton;
    private JLabel errorJLabel;

    public String getUsername() {
        return this.usernameField.getText();
    }

    public String getPassword() {
        return new String(this.passwordField.getPassword());
    }

    public LoginPanel() {
        this.setLayout(new BorderLayout());
        this.setBackground(Color.WHITE);

        JPanel mainPanel = new JPanel();
        mainPanel.setBackground(Color.WHITE);
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBorder(BorderFactory.createLineBorder(new Color(5, 148, 237), 2));
        mainPanel.setPreferredSize(new Dimension(300, 300));

        JLabel iconLabel = new JLabel("Login");
        iconLabel.setFont(new Font("Times New Roman", Font.PLAIN, 50));
        iconLabel.setAlignmentX(CENTER_ALIGNMENT);
        mainPanel.add(iconLabel);
        mainPanel.add(Box.createVerticalStrut(30));

        this.errorJLabel = new JLabel();
        this.errorJLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        this.errorJLabel.setForeground(Color.RED);
        this.errorJLabel.setAlignmentX(CENTER_ALIGNMENT);
        mainPanel.add(errorJLabel);
        mainPanel.add(Box.createVerticalStrut(5));

        JPanel userPanel = new JPanel();
        userPanel.setLayout(new BorderLayout());
        userPanel.setBackground(Color.WHITE);
        JLabel userLabel = new JLabel();
        userLabel.setFont(new Font("Arial", Font.BOLD, 14));
        userLabel.setForeground(new Color(50, 50, 50));
        userLabel.setText("Username:");
        userLabel.setPreferredSize(new Dimension(80, 35));
        this.usernameField = new JTextField(30);
        this.usernameField.setFont(new Font("Arial", Font.PLAIN, 14));
        this.usernameField.setBorder(BorderFactory.createLineBorder(new Color(5, 148, 237), 1));
        this.usernameField.enableInputMethods(false);
        this.usernameField.setPreferredSize(new Dimension(230, 25));
        userPanel.setMaximumSize(new Dimension(330, 32));
        userPanel.add(userLabel, BorderLayout.WEST);
        userPanel.add(this.usernameField, BorderLayout.CENTER);
        mainPanel.add(userPanel);
        mainPanel.add(Box.createVerticalStrut(15));

        JPanel passwordPanel = new JPanel();
        passwordPanel.setBackground(Color.WHITE);
        passwordPanel.setLayout(new BorderLayout());
        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setFont(new Font("Arial", Font.BOLD, 14));
        passwordLabel.setForeground(new Color(50, 50, 50));
        passwordLabel.setPreferredSize(new Dimension(80, 35));
        this.passwordField = new JPasswordField(30);
        this.passwordField.setFont(new Font("Arial", Font.PLAIN, 14));
        this.passwordField.setBorder(BorderFactory.createLineBorder(new Color(5, 148, 237), 1));
        this.passwordField.enableInputMethods(false);
        this.passwordField.setPreferredSize(new Dimension(185, 25));

        JButton togglePasswordBtn = new JButton("show");
        togglePasswordBtn.setFont(new Font("Arial", Font.PLAIN, 11));
        togglePasswordBtn.setPreferredSize(new Dimension(45, 25));
        togglePasswordBtn.setBackground(new Color(255, 255, 255));
        togglePasswordBtn.setForeground(new Color(5, 148, 237));
        togglePasswordBtn.setBorder(BorderFactory.createEmptyBorder());
        togglePasswordBtn.setFocusPainted(false);
        togglePasswordBtn.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        togglePasswordBtn.setContentAreaFilled(false);
        togglePasswordBtn.setOpaque(false);

        final boolean[] isPasswordVisible = { false };
        togglePasswordBtn.addActionListener(e -> {
            if (isPasswordVisible[0]) {
                this.passwordField.setEchoChar('●');
                togglePasswordBtn.setText("show");
                isPasswordVisible[0] = false;
            } else {
                this.passwordField.setEchoChar('\u0000');
                togglePasswordBtn.setText("hide");
                isPasswordVisible[0] = true;
            }
        });

        JPanel passwordFieldPanel = new JPanel();
        passwordFieldPanel.setBackground(Color.WHITE);
        passwordFieldPanel.setLayout(new BorderLayout(5, 0));
        passwordFieldPanel.add(this.passwordField, BorderLayout.CENTER);
        passwordFieldPanel.add(togglePasswordBtn, BorderLayout.EAST);

        passwordPanel.setMaximumSize(new Dimension(330, 32));
        passwordPanel.add(passwordLabel, BorderLayout.WEST);
        passwordPanel.add(passwordFieldPanel, BorderLayout.CENTER);
        passwordPanel.setPreferredSize(new Dimension(330, 35));
        mainPanel.add(passwordPanel);
        mainPanel.add(Box.createVerticalStrut(15));
        this.add(mainPanel);

        JPanel buttonJPanel = new JPanel();
        buttonJPanel.setLayout(new GridLayout(1, 2, 10, 0));
        buttonJPanel.setBackground(Color.WHITE);
        buttonJPanel.setMaximumSize(new Dimension(330, 40));
        this.loginButton = createButton("Login");
        this.createdButton = createButton("Register");
        buttonJPanel.add(this.createdButton);
        buttonJPanel.add(this.loginButton);
        mainPanel.add(buttonJPanel);
        this.add(mainPanel, BorderLayout.CENTER);
        this.setBorder(BorderFactory.createEmptyBorder(70, 80, 70, 80));
    }

    public JButton createButton(String text) {
        JButton newButton = new JButton();
        newButton.setText(text);
        newButton.setFont(new Font("Arial", Font.BOLD, 12));
        newButton.setBackground(new Color(5, 148, 237));
        newButton.setForeground(Color.WHITE);
        newButton.setBorder(BorderFactory.createEmptyBorder(20, 10, 20, 10));
        newButton.setFocusPainted(false);

        return newButton;
    }

    public void showError(String message) {
        this.errorJLabel.setText(message);
    }

    public void clearError() {
        this.errorJLabel.setText("");
    }

    public void clearLoginForm() {
        this.usernameField.setText("");
        this.passwordField.setText("");
        this.errorJLabel.setText("");
    }

}

// Custom rounded button class for capsule style
class RoundedButton extends JButton {
    private int radius = 20;

    public RoundedButton(String text) {
        super(text);
        setContentAreaFilled(false);
        setFocusPainted(false);
    }

    public void setRadius(int radius) {
        this.radius = radius;
    }

    @Override
    protected void paintComponent(java.awt.Graphics g) {
        java.awt.Graphics2D g2 = (java.awt.Graphics2D) g;
        g2.setRenderingHint(java.awt.RenderingHints.KEY_ANTIALIASING,
                java.awt.RenderingHints.VALUE_ANTIALIAS_ON);

        if (getModel().isArmed()) {
            g2.setColor(new Color(5, 130, 210));
        } else {
            g2.setColor(getBackground());
        }
        g2.fillRoundRect(0, 0, getWidth(), getHeight(), radius, radius);

        super.paintComponent(g);
    }

    @Override
    protected void paintBorder(java.awt.Graphics g) {
        // No border painting for smooth look
    }
}
