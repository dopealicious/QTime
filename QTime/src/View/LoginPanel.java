package View;

import Controller.DatabaseHandler;
import Model.DashboardForm;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.*;
import javax.swing.*;

public class LoginPanel extends JPanel {
    private JLabel askLabel;
    private JLabel header;
    private JLabel loginLabel;
    private JPasswordField passField;
    private JLabel passwordLabel;
    private JLabel regishereLabel;
    private JCheckBox showpass;
    private JButton submitBtn;
    private JTextField usernameField;
    private JLabel usernameLabel;

    public LoginPanel() {
        initComponents();
    }
    
    public void login(){
        usernameField.grabFocus();
    }
    public void addEventRegister(ActionListener e){
        submitBtn.addActionListener(e);
    }
    public JLabel getRegishereLabel(){
        return this.regishereLabel;
    }

    public void adjustLayout(int frameHeight){
        setSize(getWidth(),frameHeight);
    }

    private void initComponents() {
        header = new JLabel();
        loginLabel = new JLabel();
        usernameLabel = new JLabel();
        usernameField = new JTextField();
        passwordLabel = new JLabel();
        passField = new JPasswordField();
        showpass = new JCheckBox();
        submitBtn = new JButton();
        askLabel = new JLabel();
        regishereLabel = new JLabel();

        setBackground(new Color(255, 255, 255));

        header.setFont(new Font("Segoe UI", 1, 40)); // NOI18N
        header.setForeground(new Color(51, 51, 255));
        header.setText("QTime");

        loginLabel.setFont(new Font("Segoe UI", 1, 24)); // NOI18N
        loginLabel.setText("Login");

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
        submitBtn.setText("Submit");
        submitBtn.setHorizontalTextPosition(SwingConstants.CENTER);
        submitBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                submitBtnActionPerformed(evt);
            }
        });

        askLabel.setFont(new Font("Segoe UI", 0, 14)); // NOI18N
        askLabel.setText("don't have an account ?");

        regishereLabel.setFont(new Font("Segoe UI", 0, 14)); // NOI18N
        regishereLabel.setForeground(new Color(51, 51, 255));
        regishereLabel.setText("Register here");

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
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(askLabel)
                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(regishereLabel))
                    .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                        .addComponent(showpass)
                        .addComponent(usernameLabel)
                        .addComponent(loginLabel)
                        .addComponent(usernameField)
                        .addComponent(passwordLabel)
                        .addComponent(passField)
                        .addComponent(submitBtn, GroupLayout.DEFAULT_SIZE, 390, Short.MAX_VALUE)))
                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addComponent(header)
                .addGap(18, 18, 18)
                .addComponent(loginLabel)
                .addGap(25, 25, 25)
                .addComponent(usernameLabel)
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(usernameField, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE)
                .addGap(25, 25, 25)
                .addComponent(passwordLabel)
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(passField, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(showpass)
                .addGap(30, 30, 30)
                .addComponent(submitBtn)
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(askLabel)
                    .addComponent(regishereLabel))
                .addContainerGap(82, Short.MAX_VALUE))
        );
    }                                                              

    private void submitBtnActionPerformed(ActionEvent evt) {
        String username = usernameField.getText();
        String password = String.valueOf(passField.getPassword());
        
        if (username.isEmpty() && password.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Field tidak boleh kosong!", "Warning", JOptionPane.WARNING_MESSAGE);
        } else if (username.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Username tidak boleh kosong!", "Warning", JOptionPane.WARNING_MESSAGE);
        } else if (password.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Password tidak boleh kosong!", "Warning", JOptionPane.WARNING_MESSAGE);
        }else{
            DatabaseHandler databaseHandler = new DatabaseHandler();
            int UserId = databaseHandler.Login(username, password);
            if (UserId != 0) {
                DashboardForm dashboardForm = new DashboardForm(username);
                dashboardForm.setVisible(true);
                
                SwingUtilities.getWindowAncestor(this).dispose();
            }else{
                System.out.println("username is null");
            }
        }
    }                                         
    public void showPassActionPerformed(ActionEvent e){
        if (showpass.isSelected()) {
            passField.setEchoChar((char)0);
        } else {
            passField.setEchoChar('*');
        }
    }
    
    private void showpassActionPerformed(ActionEvent evt) {
        showPassActionPerformed(evt);
    }                                                     
}