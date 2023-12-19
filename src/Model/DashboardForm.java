package Model;

import java.awt.Toolkit;
import javax.swing.*;


public class DashboardForm extends JFrame {
    private JPanel mainpanel;
    private String username;

    private DashboardForm() {
        initComponents();
    }
    
    public DashboardForm(String username){
        initComponents();
        setLocationRelativeTo(null);
        setIconImage();
      
    }

    public void logout() {
        this.dispose(); // Close the DashboardForm
    
        // Create a new instance of AccountForm and make it visible
        AccountForm accountForm = new AccountForm();
        accountForm.setVisible(true);
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