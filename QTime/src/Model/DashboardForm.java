package Model;

import java.awt.Toolkit;
import java.awt.event.*;
import javax.swing.*;

import View.FinishPanel;
import View.LandingPanel;
import View.QuizPanel;

public class DashboardForm extends JFrame {
    private LandingPanel landingPanel;
    private JPanel mainpanel;
    private QuizPanel quizPanel;
    private FinishPanel finishPanel;
    private String username;

    private DashboardForm() {
        initComponents();
    }
    
    public DashboardForm(String username){
        initComponents();
        setLocationRelativeTo(null);
        setIconImage();

        landingPanel = new LandingPanel(username);

        ProfileForm profileForm = new ProfileForm(username, this);

        mainpanel.add(landingPanel);

        landingPanel.setSize(mainpanel.getSize());
        this.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                adjustLayout();
            }
        });        
    }
    
    public void switchToLandingPanel(String username) {
        mainpanel.remove(finishPanel);
        if (landingPanel == null) {
            landingPanel = new LandingPanel(username);
        }
        mainpanel.add(landingPanel);
        landingPanel.setSize(mainpanel.getSize());
        mainpanel.revalidate();
        mainpanel.repaint();
    }
    
    public void switchToQuizPanel(String username) {
        mainpanel.remove(landingPanel);
        quizPanel = new QuizPanel(username);
        mainpanel.add(quizPanel);
        quizPanel.setSize(mainpanel.getSize());
        mainpanel.revalidate();
        mainpanel.repaint();
    }
    
    public void switchToFinishPanel(String username){
        mainpanel.remove(quizPanel);
        finishPanel = new FinishPanel(username);
        mainpanel.add(finishPanel);
        finishPanel.setSize(mainpanel.getSize());
        mainpanel.revalidate();
        mainpanel.repaint();
    }

    public void logout() {
        this.dispose();
        AccountForm accountForm = new AccountForm();
        accountForm.setVisible(true);
    }    

    private void adjustLayout() {
        int frameWidth = getWidth();
        int frameHeight = getHeight();

        landingPanel.adjustLayout(frameWidth, frameHeight);
        if (quizPanel != null) {
            quizPanel.adjustLayout(frameWidth, frameHeight);
        }
        if (finishPanel != null) {
            finishPanel.adjustLayout(frameWidth,frameHeight);
        }    
    }
    
    private void setIconImage(){
        setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/Resources/Assets/qtime-logo.png")));
    }
                      
    private void initComponents() {

        mainpanel = new JPanel();

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        GroupLayout mainpanelLayout = new GroupLayout(mainpanel);
        mainpanel.setLayout(mainpanelLayout);
        mainpanelLayout.setHorizontalGroup(
            mainpanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGap(0, 1000, Short.MAX_VALUE)
        );
        mainpanelLayout.setVerticalGroup(
            mainpanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGap(0, 500, Short.MAX_VALUE)
        );

        GroupLayout layout = new GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addComponent(mainpanel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addComponent(mainpanel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }                      

    public static void main(String args[]) {
        new DashboardForm().setVisible(true);
    }                  
}