package View;

import java.awt.event.*;
import javax.swing.*;
import java.awt.*;

import Model.DashboardForm;
import Model.LeaderboardForm;
import Model.ProfileForm;

public class LandingPanel extends JPanel {
    private String username;
    private JButton leaderboardbtn;
    private JLabel profilelabel;
    private JButton startquizbtn;
    private JLabel welcomelabel;

    public LandingPanel(String username) {
        initComponents();
        this.username = username;
        welcomelabel.setText("Welcome " + username);

        this.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                setProfileImage("/Resources/Assets/profile.png");
            }
        });
    }
    
    public void adjustLayout(int frameWidth, int frameHeight) {
        setSize(frameWidth, frameHeight);
        int labelWidth = profilelabel.getWidth();
        profilelabel.setLocation(frameWidth - labelWidth - 14, 14);

        int centerX = frameWidth / 2;
        int centerY = frameHeight / 2 + 52;
        int labelX = centerX - welcomelabel.getWidth() / 2;
        int labelY = centerY - welcomelabel.getHeight() / 2 - 125;
        welcomelabel.setLocation(labelX, labelY);

        int buttonSpacing = 60;
        int buttonWidth = startquizbtn.getWidth();

        int buttonX = centerX - buttonWidth - buttonSpacing / 2;
        int buttonY = centerY + 20;
        startquizbtn.setLocation(buttonX, buttonY);

        int button2X = centerX + buttonSpacing / 2;
        leaderboardbtn.setLocation(button2X, buttonY);
    }    
    
    private void setProfileImage(String imagePath) {
        ImageIcon originalIcon = new ImageIcon(getClass().getResource(imagePath));
        Image originalImage = originalIcon.getImage();

        int labelWidth = profilelabel.getWidth();
        int labelHeight = profilelabel.getHeight();

        if (labelWidth > 0 && labelHeight > 0) {
            Image scaledImage = originalImage.getScaledInstance(labelWidth, labelHeight, Image.SCALE_SMOOTH);
            ImageIcon scaledIcon = new ImageIcon(scaledImage);
            profilelabel.setIcon(scaledIcon);
        } else {
            System.out.println("Label size is zero. Image not set.");
        }
    }

    private void initComponents() {
        startquizbtn = new JButton();
        leaderboardbtn = new JButton();
        welcomelabel = new JLabel();
        profilelabel = new JLabel();

        setBackground(new Color(255, 255, 255));

        startquizbtn.setBackground(new Color(51, 51, 255));
        startquizbtn.setFont(new Font("Segoe UI", 0, 14)); // NOI18N
        startquizbtn.setForeground(new Color(255, 255, 255));
        startquizbtn.setText("Start Quiz");
        startquizbtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                startquizbtnActionPerformed(evt);
            }
        });

        leaderboardbtn.setBackground(new Color(51, 51, 255));
        leaderboardbtn.setFont(new Font("Segoe UI", 0, 14)); // NOI18N
        leaderboardbtn.setForeground(new Color(255, 255, 255));
        leaderboardbtn.setText("Leaderboard");
        leaderboardbtn.setToolTipText("");
        leaderboardbtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                leaderboardbtnActionPerformed(evt);
            }
        });

        profilelabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                profilelabelMouseClicked(e);
            }
        });

        welcomelabel.setFont(new Font("Segoe UI", 1, 36)); // NOI18N
        welcomelabel.setText("Welcome");

        GroupLayout layout = new GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(373, 373, 373)
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                    .addComponent(welcomelabel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(startquizbtn, GroupLayout.PREFERRED_SIZE, 110, GroupLayout.PREFERRED_SIZE)
                        .addGap(60, 60, 60)
                        .addComponent(leaderboardbtn, GroupLayout.PREFERRED_SIZE, 115, GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(347, Short.MAX_VALUE))
            .addGroup(GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(profilelabel, GroupLayout.PREFERRED_SIZE, 80, GroupLayout.PREFERRED_SIZE)
                .addGap(14, 14, 14))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(profilelabel, GroupLayout.PREFERRED_SIZE, 80, GroupLayout.PREFERRED_SIZE)
                .addGap(104, 104, 104)
                .addComponent(welcomelabel)
                .addGap(125, 125, 125)
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                    .addComponent(startquizbtn, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(leaderboardbtn, GroupLayout.DEFAULT_SIZE, 35, Short.MAX_VALUE))
                .addContainerGap(108, Short.MAX_VALUE))
        );
    }                      

    private void leaderboardbtnActionPerformed(ActionEvent evt) {                                               
        LeaderboardForm leaderboardForm = new LeaderboardForm();
        leaderboardForm.setVisible(true);
    }                                              

    private void startquizbtnActionPerformed(ActionEvent evt) {                                             
        ((DashboardForm) SwingUtilities.getWindowAncestor(this)).switchToQuizPanel(username);
    }

    private void profilelabelMouseClicked(MouseEvent evt) {
        DashboardForm dashboardForm = (DashboardForm) SwingUtilities.getWindowAncestor(this);
        ProfileForm profileForm = new ProfileForm(username, dashboardForm);
        
        dashboardForm.setVisible(true);
        profileForm.setVisible(true);
    }
                                                                 
}

