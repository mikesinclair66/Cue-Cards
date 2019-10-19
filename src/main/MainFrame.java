package main;

/**
 * Projected started by Michael Sinclair
 * on August 11, 2019.
 * 
 * This program is an educational program
 * for students to use virtual cue cards.
 * 
 * MainFrame sets up the window for the
 * project.
 */

import javax.swing.JFrame;

public class MainFrame extends JFrame {
    public MainFrame(){
        super("Cue Cards v3.0");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(800, 700);
        setLocationRelativeTo(null);
        
        setContentPane(new MainPanel());
        
        Mouse m = new Mouse();
        addMouseListener(m);
        addMouseMotionListener(m);
        
        addKeyListener(new Keys());
        
        setVisible(true);
    }
    
    public static void main(String[] args){
        new MainFrame();
    }
}