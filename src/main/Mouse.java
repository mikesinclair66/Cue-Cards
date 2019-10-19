package main;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import navigation.Directory;
import pages.*;

public class Mouse implements MouseListener, MouseMotionListener {

    @Override
    public void mouseClicked(MouseEvent e) {
        
    }

    @Override
    public void mousePressed(MouseEvent e) {
        
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if(e.getButton() == MouseEvent.BUTTON1){
            switch(MainPanel.page){
                case 0:
                    if(!Directory.an.visible){
                        Subject.requestLeftClick();
                        Directory.requestLeftClick();
                    } else
                        Directory.an.requestLeftClick();
                    break;
                case 1:
                    if(!Directory.an.visible){
                        Categories.requestLeftClick();
                        Directory.requestLeftClick();
                    } else
                        Directory.an.requestLeftClick();
                    break;
                case 2:
                    FlashCards.requestLeftClick();
                    break;
            }
        } else if(e.getButton() == MouseEvent.BUTTON3){
            switch(MainPanel.page){
                case 0:
                case 1:
                    if(Directory.selected > -1)
                        Directory.an.requestRightClick(e.getX(), e.getY());
                    break;
            }
        }
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        
    }

    @Override
    public void mouseExited(MouseEvent e) {
        
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        int x = e.getX(), y = e.getY();
        
        switch(MainPanel.page){
            case 0:
                Subject.requestHighlight(x, y);
                Directory.requestHighlight(x, y);
                Directory.an.requestHover(x, y);
                break;
            case 1:
                Categories.requestHighlight(x, y);
                Directory.requestHighlight(x, y);
                Directory.an.requestHover(x, y);
                break;
            case 2:
                FlashCards.requestHighlight(x, y);
                break;
        }
    }
}