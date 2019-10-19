package navigation;

import java.awt.Color;
import java.awt.Graphics2D;
import pages.FlashCards;

public class SwitchCard extends Button {
    boolean left;
    
    public SwitchCard(int x, int y, int width, int height, boolean left){
        super(x, y, width, height);
        this.left = left;
        an.setMsg(left ? "Go a card back" : "Go a card forward");
    }
    
    @Override
    void drawIcon(Graphics2D comp) {
        if(left){
            comp.drawLine(x + width / 2, y + height / 2, x + width / 4 * 3, y + height / 4);
            comp.drawLine(x + width / 2, y + height / 2, x + width / 4 * 3, y + height / 4 * 3);
        } else {
            comp.drawLine(x + width / 2, y + height / 2, x + width / 4, y + height / 4);
            comp.drawLine(x + width / 2, y + height / 2, x + width / 4, y + height / 4 * 3);
        }
    }

    @Override
    public void execute() {
        int page = FlashCards.page;
        if(left)
            page--;
        else
            page++;
        
        if(page < 0)
            page = Directory.PACK_LIMIT - 1;
        else if(page >= Directory.PACK_LIMIT)
            page = 0;
        
        FlashCards.page = page;
    }
}