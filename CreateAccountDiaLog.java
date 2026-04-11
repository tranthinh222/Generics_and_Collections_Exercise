import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class CreateAccountDiaLog extends JDialog{
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JPasswordField confirmPasswordField;
    private JButton createdButton;
    private JButton canceledButton;
    private JLabel errorJLabel;
    private boolean isCreated = false;
    
    public CreateAccountDiaLog(){
        this.setTitle("Create Account");
        this.setSize(500, 380);
        this.setModal(true);
        this.setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20));

        JLabel iconLabel = new JLabel("Register");
        iconLabel.setFont(new Font("Times New Roman", Font.PLAIN, 50));
        iconLabel.setAlignmentX(CENTER_ALIGNMENT);
        panel.add(iconLabel);
        panel.add(Box.createVerticalStrut(15));

        this.errorJLabel = new JLabel();
        this.errorJLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        this.errorJLabel.setForeground(Color.RED);
        this.errorJLabel.setAlignmentX(CENTER_ALIGNMENT);
        panel.add(errorJLabel);
        panel.add(Box.createVerticalStrut(5));

        Font labelFont = new Font("Arial", Font.BOLD, 14);
        Font inputFont = new Font("Arial", Font.PLAIN, 14);

        this.usernameField = new JTextField(30);
        panel.add(createRow("Username:", this.usernameField, labelFont, inputFont));
        panel.add(Box.createVerticalStrut(8));

        this.passwordField = new JPasswordField(30);
        panel.add(createRow("Password:", this.passwordField, labelFont, inputFont));
        panel.add(Box.createVerticalStrut(8));

        this.confirmPasswordField = new JPasswordField(30);
        panel.add(createRow("Confirm password:", this.confirmPasswordField, labelFont, inputFont));
        panel.add(Box.createVerticalStrut(8));

        
        JPanel buttonPanel = new JPanel(new GridLayout(1, 2, 10, 0));
        buttonPanel.setBackground(Color.WHITE);
        buttonPanel.setMaximumSize(new Dimension(360, 35));

        this.createdButton = this.createButton("Create Account");
        this.canceledButton = this.createButton("Cancel");
        buttonPanel.add(createdButton);
        buttonPanel.add(canceledButton);
        

        panel.add(buttonPanel);
        this.add(panel);
        this.createdButton.addActionListener(l ->{
            
        });

        this.canceledButton.addActionListener(l -> {
            this.isCreated = false;
            dispose();
        });

    }

    public JButton createButton(String text){
        JButton btn = new JButton();
        btn.setText(text);
        btn.setFont(new Font("Arial", Font.BOLD, 12));
        btn.setBackground(new Color(5, 148, 237));
        btn.setForeground(Color.WHITE);
        btn.setBorder(BorderFactory.createEmptyBorder(15, 10, 15, 10));
        btn.setFocusPainted(false);

        return btn;
    } 

    public JPanel createRow(String textLabel, JTextField textField, Font labelFont, Font inputFont){
        JPanel row = new JPanel();
        row.setLayout(new BorderLayout(20, 0));
        row.setBackground(Color.WHITE);
    
        JLabel label = new JLabel();
        label.setText(textLabel);
        label.setFont(labelFont);
        label.setForeground(Color.BLACK);
        label.setPreferredSize(new Dimension(140, 26));
        textField.setBorder(BorderFactory.createLineBorder(new Color(5, 148, 237), 1));
        row.add(label, BorderLayout.WEST);
        row.add(textField, BorderLayout.CENTER);
        row.setMaximumSize(new Dimension(360, 35));
        return row;
    }
    
    public String getUsername(){
        return this.usernameField.getText();
    }

    public String getPassword(){
        return new String(this.passwordField.getPassword());
    }

    public String getConfirmPassword(){
        return new String(this.confirmPasswordField.getPassword());
    }

    public void setCreated(boolean created){
        this.isCreated = created;
    }

    public boolean isCreated(){
        return this.isCreated;
    }

    public void showError(String message){
        this.errorJLabel.setText(message);
    }

    public void clearError(){
        this.errorJLabel.setText("");
    }

    public void checkValidInformation(){
        String username = this.getUsername();
        String password = this.getPassword();
        String confirmPassword = this.getConfirmPassword();

        if (username == null)
            this.showError("Username không được trống");

        if (password == null)
            this.showError("Password không được trống");

        if (confirmPassword == null)
            this.showError("Confirm Password không được trống");

        

    }

    // public static void main(String[] args) {
    //     javax.swing.SwingUtilities.invokeLater(() -> {
    //         JFrame frame = new JFrame("Preview - CreateAccountDiaLog");
    //         frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    //         frame.setSize(new Dimension(600, 400));
    //         frame.setLocationRelativeTo(null);
    //         frame.getContentPane().setBackground(Color.WHITE);
    //         frame.setVisible(true);

    //         CreateAccountDiaLog dialog = new CreateAccountDiaLog();
    //         dialog.setModal(false);
    //         dialog.setLocationRelativeTo(frame);
    //         dialog.setVisible(true);
    //     });
    // }
}
