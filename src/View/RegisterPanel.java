package View;

import Controller.DatabaseHandler;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class RegisterPanel extends JPanel {
    private JLabel askLabel;
    private JPasswordField confirmpassField;
    private JLabel confirmpassLabel;
    private JLabel header;
    private JLabel loginhereLabel;
    private JPasswordField passField;
    private JLabel passwordLabel;
    private JLabel registerLabel;
    private JCheckBox showpass;
    private JButton submitBtn;
    private JTextField usernameField;
    private JLabel usernameLabel;

    public RegisterPanel() {
        initComponents();
    }
    
    public void registerPanel(){
        usernameField.grabFocus();
    }
    public void addEventRegister(ActionListener e){
        submitBtn.addActionListener(e);
    }

    public JLabel getLoginherelabel(){
        return this.loginhereLabel;
    }
    
    public void adjustLayout(int frameHeight){
        setSize(getWidth(),frameHeight);
    }    
                          
    private void initComponents() {
        header = new JLabel();
        registerLabel = new JLabel();
        usernameLabel = new JLabel();
        usernameField = new JTextField();
        passwordLabel = new JLabel();
        passField = new JPasswordField();
        showpass = new JCheckBox();
        submitBtn = new JButton();
        askLabel = new JLabel();
        loginhereLabel = new JLabel();
        confirmpassLabel = new JLabel();
        confirmpassField = new JPasswordField();

        setBackground(new Color(255, 255, 255));

        header.setFont(new Font("Segoe UI", 1, 40)); // NOI18N
        header.setForeground(new Color(51, 51, 255));
        header.setText("QTime");

        registerLabel.setFont(new Font("Segoe UI", 1, 24)); // NOI18N
        registerLabel.setText("Register");

        usernameLabel.setFont(new Font("Segoe UI Semibold", 0, 18)); // NOI18N
        usernameLabel.setText("Username");

        usernameField.setFont(new Font("Segoe UI", 0, 14)); // NOI18N

        passwordLabel.setFont(new Font("Segoe UI Semibold", 0, 18)); // NOI18N
        passwordLabel.setText("Password");


        showpass.setBackground(new Color(255, 255, 255));
        showpass.setText("Show Password");
        showpass.setToolTipText("");
        showpass.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                showpassActionPerformed(evt);
            }
        });

        submitBtn.setBackground(new Color(51, 51, 255));
        submitBtn.setForeground(new Color(255, 255, 255));
        submitBtn.setText("Create Account");
        submitBtn.setHorizontalTextPosition(SwingConstants.CENTER);
        submitBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                submitBtnActionPerformed(evt);
            }
        });

        askLabel.setFont(new Font("Segoe UI", 0, 14)); // NOI18N
        askLabel.setText("already have account?");

        loginhereLabel.setFont(new Font("Segoe UI", 0, 14)); // NOI18N
        loginhereLabel.setForeground(new Color(51, 51, 255));
        loginhereLabel.setText("Login here");

        confirmpassLabel.setFont(new Font("Segoe UI Semibold", 0, 18)); // NOI18N
        confirmpassLabel.setText("Confirm Password");

        GroupLayout layout = new GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(168, Short.MAX_VALUE)
                .addComponent(header)
                .addGap(159, 159, 159))
            .addGroup(layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                    .addComponent(confirmpassLabel)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(askLabel)
                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(loginhereLabel))
                    .addComponent(showpass)
                    .addComponent(usernameLabel)
                    .addComponent(registerLabel)
                    .addComponent(usernameField)
                    .addComponent(passwordLabel)
                    .addComponent(passField)
                    .addComponent(submitBtn, GroupLayout.DEFAULT_SIZE, 390, Short.MAX_VALUE)
                    .addComponent(confirmpassField))
                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addComponent(header)
                .addGap(18, 18, 18)
                .addComponent(registerLabel)
                .addGap(25, 25, 25)
                .addComponent(usernameLabel)
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(usernameField, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE)
                .addGap(25, 25, 25)
                .addComponent(passwordLabel)
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(passField, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE)
                .addGap(25, 25, 25)
                .addComponent(confirmpassLabel)
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(confirmpassField, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(showpass)
                .addGap(12, 12, 12)
                .addComponent(submitBtn)
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(askLabel)
                    .addComponent(loginhereLabel))
                .addContainerGap())
        );
    }

    public void showPassActionPerformed(ActionEvent e){
        if (showpass.isSelected()) {
            passField.setEchoChar((char)0);
            confirmpassField.setEchoChar((char)0);
        } else {
            passField.setEchoChar('*');
            confirmpassField.setEchoChar('*');
        }
    }
    private void showpassActionPerformed(ActionEvent evt) {
        showPassActionPerformed(evt);
    }                                        

    private void submitBtnActionPerformed(ActionEvent evt) {
        String username = usernameField.getText();
        String password = new String(passField.getPassword());
        String confirmPassword = new String(confirmpassField.getPassword());

        if (username.isEmpty() && password.isEmpty() && confirmPassword.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Field tidak boleh kosong!", "Warning", JOptionPane.WARNING_MESSAGE);
        }else if (username.isEmpty()) {
             JOptionPane.showMessageDialog(null, "Username tidak boleh kosong!", "Warning", JOptionPane.WARNING_MESSAGE);           
        }else if (password.isEmpty()) {
              JOptionPane.showMessageDialog(null, "Password tidak boleh kosong!", "Warning", JOptionPane.WARNING_MESSAGE);          
        }else if (confirmPassword.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Confirm Password tidak boleh kosong!", "Warning", JOptionPane.WARNING_MESSAGE);            
        }else{
            DatabaseHandler databaseHandler = new DatabaseHandler();
            databaseHandler.Register(username, password, confirmPassword);
        }
    }              
}