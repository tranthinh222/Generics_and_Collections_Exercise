import javax.swing.*;

import java.awt.*;


public class LoginPanel extends JPanel{
    public JTextField usernameField;
    public JPasswordField passwordField;
    public JButton createdButton;
    public JButton loginButton;
    private JLabel errorJLabel;
    
    public String getUsername(){
        return this.usernameField.getText();
    }

    public String getPassword(){
        return new String(this.passwordField.getPassword());
    }

    public LoginPanel(){
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
        passwordPanel.setMaximumSize(new Dimension(330, 32));
        passwordPanel.add(passwordLabel, BorderLayout.WEST);
        passwordPanel.add(this.passwordField, BorderLayout.CENTER);
        mainPanel.add(passwordPanel);
        mainPanel.add(Box.createVerticalStrut(15));
        this.add(mainPanel);

        JPanel buttonJPanel = new JPanel();
        buttonJPanel.setLayout(new GridLayout(1, 2, 10, 0));
        buttonJPanel.setBackground(Color.WHITE);
        buttonJPanel.setMaximumSize(new Dimension(330, 40));
        this.loginButton = createButton("Login");
        this.createdButton = createButton("Create Account");
        buttonJPanel.add(this.createdButton);
        buttonJPanel.add(this.loginButton);
        mainPanel.add(buttonJPanel);
        this.add(mainPanel, BorderLayout.CENTER);
        this.setBorder(BorderFactory.createEmptyBorder(70, 80, 70, 80));
    }

    public JButton createButton(String text){
        JButton newButton = new JButton();
        newButton.setText(text);
        newButton.setFont(new Font("Arial", Font.BOLD, 12));
        newButton.setBackground(new Color(5, 148, 237));
        newButton.setForeground(Color.WHITE);
        newButton.setBorder(BorderFactory.createEmptyBorder(20, 10, 20, 10));
        newButton.setFocusPainted(false);

        return newButton;
    }

    public void showError(String message){
        this.errorJLabel.setText(message);
    }

    public void clearError(){
        this.errorJLabel.setText("");
    }

    // public static void main(String[] args) {
    //     javax.swing.SwingUtilities.invokeLater(() -> {
    //         JFrame frame = new JFrame("LoginPanel Preview");
    //         frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    //         frame.setSize(600, 500);
    //         LoginPanel panel = new LoginPanel();
    //         panel.setPreferredSize(new Dimension(400, 300));

    //         frame.getContentPane().add(panel);
    //         frame.setLocationRelativeTo(null);
    //         frame.setVisible(true);
    //     });
    // }
    

}
