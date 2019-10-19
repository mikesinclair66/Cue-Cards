package navigation;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;

public abstract class Button {
    int x, y;
    int width, height;
    
    public boolean highlighted;//if the cursor is hovering over the button
    
    public Annotation an = new Annotation(false);
    
    public Button(int x, int y, int width, int height){
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }
    
    public void draw(Graphics2D comp){
        comp.setStroke(new BasicStroke(2));
        drawIcon(comp);
        
        if(highlighted){
            comp.setColor(new Color(255, 255, 255, 127));
            comp.fillRoundRect(x + 1, y + 1, width - 2, height - 2, 10, 10);
        }
        
        comp.setColor(Color.black);
        comp.drawRoundRect(x, y, width, height, 10, 10);
    }
    
    abstract void drawIcon(Graphics2D comp);
    
    /** What happens if the button is clicked. */
    abstract void execute();
    
    public void update(){
        an.update();
    }
    
    /** Checks if the button is being highlighted. */
    public void requestHighlight(int x, int y){
        if(x >= this.x + width / 8 && x <= this.x + width / 8 * 11
                && y >= this.y + height / 3 * 2 && y <= this.y + height + height / 3 * 2)
            highlighted = true;
        else
            highlighted = false;
        
        if(highlighted)
            an.setCoords(x, y);
    }
    
    /** Checks if the button was clicked. */
    public void requestClick(){
        if(highlighted)
            execute();
    }
    
    public void setX(int x){
        this.x = x;
    }
    
    public void setY(int y){
        this.y = y;
    }
}