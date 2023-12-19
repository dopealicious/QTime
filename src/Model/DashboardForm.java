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
        // Create an instance of LandingPanel
        landingPanel = new LandingPanel(username);

        // In DashboardForm
        ProfileForm profileForm = new ProfileForm(username, this);

        // Add LandingPanel to mainpanel
        mainpanel.add(landingPanel);
        
        // Set LandingPanel size to match mainpanel
        landingPanel.setSize(mainpanel.getSize());
        this.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                adjustLayout();
            }
        });        
    }
    
    public void switchToLandingPanel(String username) {
        // Switch to LandingPanel and pass the username
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
        // Remove the current landingPanel
        mainpanel.remove(landingPanel);

        // Create an instance of QuizPanel if it's not already created
        quizPanel = new QuizPanel(username);
        // if (quizPanel == null) {
        // }
        
        // Add QuizPanel to mainpanel
        mainpanel.add(quizPanel);

        // Set QuizPanel size to match mainpanel
        quizPanel.setSize(mainpanel.getSize());

        // Repaint the mainpanel to reflect changes
        mainpanel.revalidate();
        mainpanel.repaint();
    }
    
    public void switchToFinishPanel(String username){
        // Show FinishPanel instead of QuizPanel
        mainpanel.remove(quizPanel);
        finishPanel = new FinishPanel(username);

        // Add QuizPanel to mainpanel
        mainpanel.add(finishPanel);

        // Set QuizPanel size to match mainpanel
        finishPanel.setSize(mainpanel.getSize());

        // Repaint the mainpanel to reflect changes
        mainpanel.revalidate();
        mainpanel.repaint();
    }

    public void logout() {
        this.dispose(); // Close the DashboardForm
    
        // Create a new instance of AccountForm and make it visible
        AccountForm accountForm = new AccountForm();
        accountForm.setVisible(true);
    }    

    private void adjustLayout() {
        // Get the size of the frame
        int frameWidth = getWidth();
        int frameHeight = getHeight();

        // Update LandingPanel layout
        landingPanel.adjustLayout(frameWidth, frameHeight);

        // If QuizPanel is active, update its layout
        if (quizPanel != null) {
            quizPanel.adjustLayout(frameWidth, frameHeight);
        }
        // If FinishPanel is active, update its layout
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