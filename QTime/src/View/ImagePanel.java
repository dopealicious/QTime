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
        // Load the GIF using ImageIcon
        icon = new ImageIcon(getClass().getResource("/Resources/Assets/space.gif"));

        // Set up a Timer to repaint the panel every 100 milliseconds
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

        // Get the width and height of the panel
        int panelWidth = getWidth();
        int panelHeight = getHeight();

        // Calculate the scaling factors
        double scaleX = (double) panelWidth / icon.getIconWidth();
        double scaleY = (double) panelHeight / icon.getIconHeight();

        // Apply the scaling transformation
        transform = AffineTransform.getScaleInstance(scaleX, scaleY);
        graphics2D.transform(transform);

        // Draw the icon at (0,0) after applying the transformation
        icon.paintIcon(this, graphics2D, 0, 0);

        graphics2D.dispose();
    }
}
