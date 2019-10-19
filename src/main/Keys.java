package main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import navigation.Directory;
import pages.FlashCards;

public class Keys implements KeyListener {

    @Override
    public void keyTyped(KeyEvent e) {
        
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int keyCode = e.getKeyCode();
        char key = e.getKeyChar();
        
        switch(MainPanel.page){
            case 0:
            case 1:
                Directory.namePackage(keyCode, key);
                break;
            case 2:
                FlashCards.keyPressed(keyCode, key);
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int keyCode = e.getKeyCode();
        FlashCards.keyReleased(keyCode);
    }
}