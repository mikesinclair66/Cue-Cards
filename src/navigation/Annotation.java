package navigation;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Stroke;
import main.MainPanel;
import pages.Categories;
import pages.Subject;
import storage.Delete;

public class Annotation {
    int x, y;
    int lastX, lastY;//if x==lastX & y==lastY, start count
    public boolean visible;
    public int count;//if this reaches a point, annotation becomes visible
    int lastCount;//every so counts, lastX and lastY are record
    
    boolean interactable;
    int rows;
    int selected = -1;
    int clicked = -1;//the folder/pack that was clicked
    String[] options;
    
    //graphics variables
    final Color INNER_COLOR = new Color(235, 235, 235);
    final Color HIGHLIGHT = new Color(200, 200, 200);
    final Color OUTER_COLOR = Color.black;
    final int FONT_SIZE = 13;
    final Font FONT = new Font("Times New Roman", Font.PLAIN, FONT_SIZE);
    final int ROW_HEIGHT = FONT_SIZE * 3 / 2;
    int width, height;
    final int WAIT_TIME = 15;
    
    /** Constructor creates interctable/non-interactable annotation. */
    public Annotation(boolean interactable){
        this.interactable = interactable;
    }
    
    public void draw(Graphics2D comp){
        if(visible){
            Stroke s = comp.getStroke();
            comp.setStroke(new BasicStroke(1));
            Font f = comp.getFont();
            comp.setFont(FONT);
            
            comp.setColor(INNER_COLOR);
            comp.fillRect(x, y, width, height * options.length);
            comp.setColor(OUTER_COLOR);
            comp.drawRect(x, y, width, height * options.length);
            
            //highlight the selected option if applicable
            if(selected > -1){
                comp.setColor(HIGHLIGHT);
                comp.fillRect(x, y + selected * height, width, height);
            }
            
            comp.setColor(Color.black);
            for(int i = 0; i < options.length; i++){
                comp.drawString(options[i], x + 5, y + height / 8 * 7 + i * height);
                if(i > 0)
                    comp.drawLine(x, y + height * i, x + width, y + height * i);
            }
            
            comp.setFont(f);
            comp.setStroke(s);
        }
    }
    
    public void update(){
        if(interactable){
            
        }
        
        else{
            if(visible){
                if(x != lastX || y != lastY){
                    count = 0;
                    visible = false;
                }
            } else {
                lastCount++;
                if(lastCount == 4){
                    lastX = x;
                    lastY = y;
                    lastCount = 0;
                }
                
                if(lastX == x && lastY == y){
                    if(count < WAIT_TIME)
                        count++;
                    else
                        visible = true;
                } else
                    count = 0;
            }
        }
    }
    
    /** If the user left-clicks on the annotation. */
    public void requestLeftClick(){
        if(visible && interactable){
            if(selected == 0){
                Directory.naming = true;
            } else if(selected == 1){
                if(MainPanel.page == 0)
                    Delete.deleteFolder(clicked);
                else
                    Delete.deleteCategory(clicked);
            }
            
            visible = false;
            selected = -1;
            clicked = -1;
        }
    }
    
    /** If the user right-clicks a folder, make the annotation visible. */
    public void requestRightClick(int x, int y){
        if(interactable){
            if(visible && (x < this.x || x > this.x + width
                    || y < this.y || y > this.y + height))
                setCoords(x, y);
            else if(!visible && !Directory.naming && !Subject.deleteButton.deleting
                    && !Categories.deleteButton.deleting){
                clicked = Directory.selected;
                setCoords(x, y);
                visible = true;
            }
        }
    }
    
    /** If the user hovers over the interactable annotation. */
    public void requestHover(int x, int y){
        if(visible && interactable){
            int hoverX = x - 7;
            int hoverY = y - 30;
            
            if(hoverX >= this.x && hoverX <= this.x + width){
                //check which option is hovered
                for(int i = 0; i < options.length; i++)
                    if(hoverY >= this.y + (i * height) && hoverY <= this.y + ((i + 1) * height))
                        selected = i;
            } else
                selected = -1;
        }
    }
    
    public void setCoords(int x, int y){
        this.x = x + 5;
        this.y = y - 30;
    }
    
    public void setMsg(String ... o){
        options = o;
        rows = o.length;
        width = getLargestMsgWidth(o) * 5 / 4;
        height = ROW_HEIGHT * o.length;
        
        //handle errors
        if(o.length == 1 && interactable)
            System.err.println("Error: an interactable annotation must have more than one option.");
        else if(o.length > 1 && !interactable)
            System.err.println("Error: a non-interactable annotation can't have multiple options.");
    }
    
    private int getLargestMsgWidth(String ... o){
        String largest = "";
        for(int i = 0; i < o.length; i++)
            if(o[i].length() > largest.length())
                largest = o[i];
        return largest.length() * FONT_SIZE / 2;
    }
}