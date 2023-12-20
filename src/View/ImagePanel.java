package View;

import java.awt.*;
import java.awt.event.*;
import java.awt.geom.AffineTransform;
import javax.swing.*;

public class ImagePanel extends JPanel {
    ImageIcon icon;
    Timer timer;
    AffineTransform transform;
    
    public ImagePanel(){
        icon = new ImageIcon(getClass().getResource("/Resources/Assets/space.gif"));
        timer = new Timer(100, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                repaint();
            }
        });
        timer.start();
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D graphics2D = (Graphics2D) g.create();

        int panelWidth = getWidth();
        int panelHeight = getHeight();

        double scaleX = (double) panelWidth / icon.getIconWidth();
        double scaleY = (double) panelHeight / icon.getIconHeight();

        transform = AffineTransform.getScaleInstance(scaleX, scaleY);
        graphics2D.transform(transform);

        icon.paintIcon(this, graphics2D, 0, 0);

        graphics2D.dispose();
    }
}
