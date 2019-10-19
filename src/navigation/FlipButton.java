package navigation;

import java.awt.Color;
import java.awt.Graphics2D;
import pages.FlashCards;

public class FlipButton extends Button{
    public FlipButton(int x, int y, int width, int height){
        super(x, y, width, height);
        an.setMsg("Flip card");
    }
    
    @Override
    public void drawIcon(Graphics2D comp){
        comp.setColor(Color.green);
        comp.drawOval(x + 10, y + 10, width - 20, height - 20);
        
        //draw the arrows
        comp.drawLine(x + 5, y + height / 2 - 5, x + 10, y + height / 2);
        comp.drawLine(x + 10, y + height / 2, x + 15, y + height / 2 - 5);
        comp.drawLine(x + width - 15, y + height / 2 + 5, x + width - 10, y + height / 2);
        comp.drawLine(x + width - 10, y + height / 2, x + width - 5, y + height / 2 + 5);
    }
    
    @Override
    public void execute(){
        FlashCards.front = !FlashCards.front;
    }
}