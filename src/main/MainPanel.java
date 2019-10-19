package main;

/**
 * MainPanel sets up everything
 * inside of the window.
 */

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import javax.swing.JPanel;
import javax.swing.Timer;
import navigation.Directory;
import pages.*;
import storage.Load;

public class MainPanel extends JPanel implements ActionListener {
    public static int width = 1000, height = 800;
    Timer t = new Timer(50, this);
    
    public static byte page;//the page that the user is on; starts on the subject page
    
    public static int navbarHeight = 60;
    
    boolean loaded;
    
    public MainPanel(){
        t.start();
        setBackground(new Color(50, 50, 200));
        Directory.an.setMsg("Rename pack", "Delete pack");
    }
    
    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        Graphics2D comp = (Graphics2D) g;
        comp.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        
        comp.setColor(new Color(130, 42, 42));
        comp.fillRect(0, 0, width, navbarHeight);
        comp.setColor(Color.gray);
        comp.setStroke(new BasicStroke(3));
        comp.drawLine(0, navbarHeight - 1, width, navbarHeight - 1);
        
        switch(page){
            case 0:
                Subject.draw(comp);
                break;
            case 1:
                Categories.draw(comp);
                break;
            case 2:
                FlashCards.draw(comp);
                break;
        }
    }
    
    @Override
    public void actionPerformed(ActionEvent e){
        width = getWidth();
        height = getHeight();
        
        switch(page){
            case 0:
                Subject.update();
                break;
            case 1:
                Categories.update();
                break;
            case 2:
                FlashCards.update();
                break;
        }
        
        if(!loaded){
            loaded = true;
            Load.load();
        }
        
        repaint();
    }
}