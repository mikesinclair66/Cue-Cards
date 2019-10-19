package navigation;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import main.MainPanel;

public class DeleteButton extends Button {
    public boolean deleting;
    
    public DeleteButton(int x, int y, int width, int height){
        super(x, y, width, height);
        an.setMsg(MainPanel.page == 0 ? "Delete folder" : "Delete category");
    }
    
    @Override
    public void drawIcon(Graphics2D comp){
        comp.setFont(new Font("Comic Sans", Font.BOLD, width));
        comp.setColor(Color.red);
        
        comp.drawString("X", x + 8, y + 39);
        
        if(deleting){
            comp.setColor(new Color(255, 0, 0, 175));
            comp.fillRoundRect(x + 1, y + 1, width - 2, height - 2, 10, 10);
            comp.setColor(new Color(255, 255, 255, 50));
            comp.fillRect(x + 5, y + 5, width / 6, height / 6);
        }
    }
    
    public void execute(){
        deleting = !deleting;
        if(deleting)
            Directory.an.visible = false;
    }
}