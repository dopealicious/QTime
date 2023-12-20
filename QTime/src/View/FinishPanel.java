package View;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

import Controller.DatabaseHandler;
import Model.DashboardForm;

public class FinishPanel extends JPanel {
    private JLabel attemptlabel;
    private JLabel finishlabel;
    private JLabel header;
    private JButton homebtn;
    private JLabel scorelabel;
    private JLabel timelabel;
    private String username;
    private int highScore,timetotal,attempttotal;

    public FinishPanel(String username) {
        this.username = username;
        DatabaseHandler databaseHandler = new DatabaseHandler();
        int userId = databaseHandler.fetchUserId(username);
        this.highScore = databaseHandler.getHighScore(userId);
        this.timetotal = databaseHandler.getTotalTime(userId);
        this.attempttotal = databaseHandler.getTotalTests(userId);
        initComponents();
    }

    public void adjustLayout(int frameWidth, int frameHeight) {
        setSize(frameWidth, frameHeight);

        int headerX = (frameWidth - header.getWidth()) / 2;
        int headerY = 13;
        header.setLocation(headerX, headerY);

        int finishLabelX = (frameWidth - finishlabel.getWidth()) / 2;
        int finishLabelY = headerY + header.getHeight() + 104;
        finishlabel.setLocation(finishLabelX, finishLabelY);

        int scoreLabelX = (frameWidth - scorelabel.getWidth()) / 2;
        int scoreLabelY = finishLabelY + finishlabel.getHeight() + 40;
        scorelabel.setLocation(scoreLabelX, scoreLabelY);

        int timeLabelX = (frameWidth - timelabel.getWidth()) / 2;
        int timeLabelY = scoreLabelY + scorelabel.getHeight() + 18;
        timelabel.setLocation(timeLabelX, timeLabelY);

        int attemptLabelX = (frameWidth - attemptlabel.getWidth()) / 2;
        int attemptLabelY = timeLabelY + timelabel.getHeight() + 18;
        attemptlabel.setLocation(attemptLabelX, attemptLabelY);
        
        int homeBtnX = (frameWidth - homebtn.getWidth()) / 2;
        int homeBtnY = attemptLabelY + attemptlabel.getHeight() + 56;
        homebtn.setLocation(homeBtnX, homeBtnY);
    }
           
    private void initComponents() {
        header = new JLabel();
        homebtn = new JButton();
        finishlabel = new JLabel();
        scorelabel = new JLabel();
        timelabel = new JLabel();
        attemptlabel = new JLabel();

        setBackground(new Color(255, 255, 255));

        header.setFont(new Font("Segoe UI", 1, 40));
        header.setForeground(new Color(51, 51, 255));
        header.setText("QTime");

        homebtn.setBackground(new Color(51, 51, 255));
        homebtn.setFont(new Font("Segoe UI", 0, 14));
        homebtn.setForeground(new Color(255, 255, 255));
        homebtn.setText("Home");
        homebtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                homebtnActionPerformed(evt);
            }
        });

        finishlabel.setFont(new Font("Segoe UI", 1, 24));
        finishlabel.setText("Quiz Finish");

        scorelabel.setFont(new Font("Segoe UI", 0, 14));
        scorelabel.setText("Score: "+highScore);

        timelabel.setFont(new Font("Segoe UI", 0, 14));
        timelabel.setText("Time: "+timetotal);

        attemptlabel.setFont(new Font("Segoe UI", 0, 14));
        attemptlabel.setText("Total Attempt: "+attempttotal);

        GroupLayout layout = new GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(437, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                    .addComponent(header, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(finishlabel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(homebtn, GroupLayout.PREFERRED_SIZE, 85, GroupLayout.PREFERRED_SIZE))
                    .addComponent(scorelabel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(timelabel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(attemptlabel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(438, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(header)
                .addGap(104, 104, 104)
                .addComponent(finishlabel)
                .addGap(40, 40, 40)
                .addComponent(scorelabel)
                .addGap(18, 18, 18)
                .addComponent(timelabel)
                .addGap(18, 18, 18)
                .addComponent(attemptlabel)
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 56, Short.MAX_VALUE)
                .addComponent(homebtn)
                .addGap(76, 76, 76))
        );
    }

    private void homebtnActionPerformed(ActionEvent evt) {
        ((DashboardForm) SwingUtilities.getWindowAncestor(this)).switchToLandingPanel(username);
    }                                                    
}
