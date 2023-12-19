package View;

import java.awt.Component;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;
import Controller.Slider;

public class PanelSlide extends JPanel implements Slider {
    private int animate = 1;
    private Thread animationThread;  // Use Thread instead of Timer
    private Component com1;
    private Component com2;
    private List<Component> list = new ArrayList<>();
    private int currentShowing;
    private boolean animateRight;

    public int getAnimate() {
        return animate;
    }

    public void setAnimate(int animate) {
        this.animate = animate;
    }

    public PanelSlide() {
        initComponents();
        animationThread = new Thread(new Runnable() {
            @Override
            public void run() {
                animate();
            }
        });
    }

    @Override
    public void init(Component... com) {
        if (com.length > 0) {
            for (Component c : com) {
                list.add(c);
                c.setSize(getSize());
                c.setVisible(false);
                this.add(c);
            }
            Component show = list.get(0);
            show.setVisible(true);
            show.setLocation(0, 0);
        }
    }

    @Override
    public void show(int index) {
        if (!animationThread.isAlive()) {
            if (list.size() >= 2 && index < list.size() && index != currentShowing) {
                com2 = list.get(index);
                com1 = list.get(currentShowing);
                animateRight = index < currentShowing;
                currentShowing = index;
                com2.setVisible(true);
                if (animateRight) {
                    com2.setLocation(-com2.getWidth(), 0);
                } else {
                    com2.setLocation(getWidth(), 0);
                }

                animationThread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        animate();
                    }
                });
                
                animationThread.start();
            }
        }
    }    

    private void animate() {
        if (animateRight) {
            while (com2.getLocation().x < 0) {
                com2.setLocation(com2.getLocation().x + animate, 0);
                com1.setLocation(com1.getLocation().x + animate, 0);
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            com2.setLocation(0, 0);
            com1.setVisible(true);
        } else {
            while (com2.getLocation().x > 0) {
                com2.setLocation(com2.getLocation().x - animate, 0);
                com1.setLocation(com1.getLocation().x - animate, 0);
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            com2.setLocation(0, 0);
            com1.setVisible(true);
        }
    }

    private void initComponents() {
        GroupLayout layout = new GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGap(0, 626, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGap(0, 454, Short.MAX_VALUE)
        );
    }
}