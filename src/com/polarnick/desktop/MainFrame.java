package com.polarnick.desktop;

import com.polarnick.desktop.life.LifeDrawerThread;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;

/**
 * Polyarniy Nikolay, 17.12.16.
 */
public class MainFrame extends JFrame {

    private LifeDrawerThread drawer;

    public MainFrame() throws HeadlessException {
        setPreferredSize(new Dimension(640, 480));
        setResizable(true);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        pack();
        setLocationRelativeTo(null);
        createBufferStrategy(2);

        drawer = new LifeDrawerThread(this);

        addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                drawer.nextUpdater();
            }
        });
    }

    public void start() {
        Thread thread = new Thread(drawer);
        thread.start();
    }

    public void paint(BufferedImage image, String text) {
        BufferStrategy bs = getBufferStrategy();
        Graphics g = bs.getDrawGraphics();

        g.drawImage(image, 0, 0, getWidth(), getHeight(), null);
        g.setColor(Color.WHITE);
        g.drawString(text, 5, 30);
        g.dispose();

        bs.show();
    }

    public static void main(String[] args) {
        MainFrame main = new MainFrame();
        main.start();
        main.setVisible(true);
    }

}
