package Model;

import java.awt.Toolkit;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.*;

import View.ImagePanel;
import View.LoginPanel;
import View.PanelSlide;
import View.RegisterPanel;

public class AccountForm extends JFrame{
    private LoginPanel loginPanel;
    private RegisterPanel registerPanel;
    private PanelSlide slide;
    private ImagePanel imagepanel;

    public AccountForm(){
        initComponents();
        setIconImage();
        loginPanel = new LoginPanel();
        registerPanel = new RegisterPanel();

        slide.setAnimate(15);
        slide.init(loginPanel,registerPanel);
        registerPanel.setVisible(false);
        setLocationRelativeTo(null);
        
        loginPanel.getRegishereLabel().addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent evt){
                slide.show(1);
                registerPanel.registerPanel();
            }
        });
        
        registerPanel.getLoginherelabel().addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent evt){
                slide.show(0);
                loginPanel.login();
            }
        });

        loginPanel.setSize(slide.getSize());
        registerPanel.setSize(slide.getSize());
        this.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                adjustLayout();
            }
        });        

    }

    private void adjustLayout() {
        int frameHeight = getHeight();
        loginPanel.adjustLayout(frameHeight);
        registerPanel.adjustLayout(frameHeight);
    }    

    private void setIconImage(){
        setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/Resources/Assets/qtime-logo.png")));
    }

    private void initComponents() {
        slide = new PanelSlide();
        imagepanel = new ImagePanel();

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        GroupLayout slideLayout = new GroupLayout(slide);
        slide.setLayout(slideLayout);
        slideLayout.setHorizontalGroup(
            slideLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGap(0, 450, Short.MAX_VALUE)
        );
        slideLayout.setVerticalGroup(
            slideLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        GroupLayout imagepanelLayout = new GroupLayout(imagepanel);
        imagepanel.setLayout(imagepanelLayout);
        imagepanelLayout.setHorizontalGroup(
            imagepanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGap(0, 550, Short.MAX_VALUE)
        );
        imagepanelLayout.setVerticalGroup(
            imagepanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGap(0, 500, Short.MAX_VALUE)
        );

        GroupLayout layout = new GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(imagepanel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(0, 0, 0)
                .addComponent(slide, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                    .addComponent(imagepanel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(slide, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(0, 0, 0))
        );

        pack();
    }

    public static void main(String args[]) {
        new AccountForm().setVisible(true);
    }         
}
