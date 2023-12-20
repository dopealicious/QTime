package Model;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import Controller.DatabaseHandler;


public class ProfileForm extends JFrame {
    private JLabel attemptlabel;
    private JButton logoutbtn;
    private JLabel passwordlabel;
    private JLabel scorelabel;
    private JLabel usernamelabel;
    private JButton ubahbtn;
    private String username;
    private DatabaseHandler databaseHandler;
    private JPanel profilepanel;
    private DashboardForm dashboardForm;

    private ProfileForm() {
        initComponents();
    }
    private void setIconImage(){
        setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/Resources/Assets/qtime-logo.png")));
    }
    public ProfileForm(String username, DashboardForm dashboardForm) {
        databaseHandler = new DatabaseHandler();
        initComponents();
        setIconImage();
        setResizable(false);
        
        this.dashboardForm = dashboardForm;
        this.username = username;
        usernamelabel.setText("Nama: " + username);
        int userId = databaseHandler.fetchUserId(username);
        String pass = databaseHandler.getPassInfo(username);
        int totalattempt = databaseHandler.getTotalTests(userId);
        int scoreshow = databaseHandler.getHighScore(userId);
        attemptlabel.setText("Jumlah Tes: " + totalattempt);
        scorelabel.setText("Skor Sebelumnya: " + scoreshow);
        passwordlabel.setText("Kata Sandi: " + pass);
    }

    private void initComponents() {
        profilepanel = new JPanel();
        usernamelabel = new JLabel();
        passwordlabel = new JLabel();
        scorelabel = new JLabel();
        attemptlabel = new JLabel();
        logoutbtn = new JButton();
        ubahbtn = new JButton("Ubah");

        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setBackground(new Color(255, 255, 255));
        setLocationRelativeTo(null);

        profilepanel.setBackground(new Color(255, 255, 255));
        profilepanel.setPreferredSize(new Dimension(420, 215));

        usernamelabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));

        passwordlabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));

        scorelabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));

        attemptlabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));

        logoutbtn.setBackground(new Color(51, 51, 255));
        logoutbtn.setForeground(new Color(255, 255, 255));
        logoutbtn.setText("Logout");
        logoutbtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                logoutbtnActionPerformed(evt);
            }
        });

        ubahbtn.setBackground(new Color(0, 153, 0));
        ubahbtn.setForeground(new Color(255, 255, 255));
        ubahbtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                ubahbtnActionPerformed(evt);
            }
        });

        GroupLayout profilepanelLayout = new GroupLayout(profilepanel);
        profilepanel.setLayout(profilepanelLayout);
        profilepanelLayout.setHorizontalGroup(
                profilepanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(profilepanelLayout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(profilepanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                        .addComponent(usernamelabel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(passwordlabel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(scorelabel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(attemptlabel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addGroup(profilepanelLayout.createSequentialGroup()
                                                .addComponent(logoutbtn)
                                                .addGap(20,20,20) // Adding a gap of 20
                                                .addComponent(ubahbtn)
                            .addContainerGap())))
        );
        profilepanelLayout.setVerticalGroup(
                profilepanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(profilepanelLayout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(usernamelabel)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(passwordlabel)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(scorelabel)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(attemptlabel)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(profilepanelLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                        .addComponent(logoutbtn)
                                        .addComponent(ubahbtn))
                                .addContainerGap(20, Short.MAX_VALUE)) // Adding a gap of 20
        );

        GroupLayout layout = new GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addComponent(profilepanel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addComponent(profilepanel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE))
        );

        pack();
    }

    private void logoutbtnActionPerformed(ActionEvent evt) {
        int option = JOptionPane.showConfirmDialog(this, "Apakah Anda yakin ingin logout?", "Logout", JOptionPane.YES_NO_OPTION);
        if (option == JOptionPane.YES_OPTION) {
            this.dispose();
            dashboardForm.logout();
        }
    }

    private void ubahbtnActionPerformed(ActionEvent evt) {
        String currentPassword = databaseHandler.getPassInfo(username);
        String newUsername = JOptionPane.showInputDialog(this, "Masukkan username baru:");
        String newPassword = JOptionPane.showInputDialog(this, "Masukkan password baru:");

        if (newUsername != null && newPassword != null && !newPassword.equals(currentPassword)) {
            int userId = databaseHandler.fetchUserId(username);
            databaseHandler.updateUserInfo(userId, newUsername, newPassword);

            usernamelabel.setText("Nama: " + newUsername);
            passwordlabel.setText("Password: " + newPassword);

            ubahbtn.setVisible(true);
        } else if (newPassword != null && newPassword.equals(currentPassword)) {
            JOptionPane.showMessageDialog(this, "Password baru tidak boleh sama dengan password sebelumnya!","Warning", JOptionPane.WARNING_MESSAGE);
        }
    }

    public static void main(String[] args) {
        new ProfileForm().setVisible(true);
    }
}
