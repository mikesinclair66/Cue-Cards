package pages;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import main.MainPanel;
import navigation.BackButton;
import navigation.Directory;
import navigation.FlipButton;
import navigation.SaveButton;
import navigation.SwitchCard;

public class FlashCards {
    static int width = 600, height = 400;
    public static final int lines = 8;//the amount of lines on the cue card - 1
    public static int curCardPack;//the current card pack that is selected
    
    static Color cardColor = new Color(205, 255, 124);
    static Color cardOutline = new Color(99, 124, 0);
    static Color indexColor = new Color(255, 124, 207);
    static Color highlightColor = new Color(99, 124, 0, 127);
    static Color hoverColor = new Color(99, 124, 0, 55);
    
    static FlipButton flip = new FlipButton(MainPanel.width / 2 - (MainPanel.navbarHeight - 15) / 2,
    MainPanel.height - (MainPanel.navbarHeight - 15) * 2,
    MainPanel.navbarHeight - 15, MainPanel.navbarHeight - 15);
    public static boolean front = false;//if the page is displaying the front of the cue card
    
    static SwitchCard leftSwitch = new SwitchCard(MainPanel.width / 4 - (MainPanel.navbarHeight - 15) / 2,
    MainPanel.height - (MainPanel.navbarHeight - 15) * 2,
    MainPanel.navbarHeight - 15, MainPanel.navbarHeight - 15, true);
    static SwitchCard rightSwitch = new SwitchCard(MainPanel.width / 4 * 3 - (MainPanel.navbarHeight - 15) / 2,
    MainPanel.height - (MainPanel.navbarHeight - 15) * 2,
    MainPanel.navbarHeight - 15, MainPanel.navbarHeight - 15, false);
    
    static BackButton backButton =
            new BackButton(6, 6, MainPanel.navbarHeight - 15, MainPanel.navbarHeight - 15);
    static SaveButton saveButton
            = new SaveButton(6 + MainPanel.navbarHeight, 6, MainPanel.navbarHeight - 15,
            MainPanel.navbarHeight - 15);
    
    
    static int selected = -1;//the selected line of the cue card
    static int hovered = -1;//the hovered line of the cue card
    
    public static String[][] frontSide = new String[Directory.PACK_LIMIT][lines + 1],
            backSide = new String[Directory.PACK_LIMIT][lines + 1];
    
    static int fontSize = 25;
    static Font font = new Font("Times New Roman", Font.PLAIN, fontSize);
    
    public static int page;//can go up to Directory.PACK_LIMIT
    
    public static void draw(Graphics2D comp){
        comp.setFont(font);
        flip.draw(comp);
        leftSwitch.draw(comp);
        rightSwitch.draw(comp);
        backButton.draw(comp);
        saveButton.draw(comp);
        
        if(!Directory.naming){
            if(flip.highlighted)
                flip.an.draw(comp);
            if(leftSwitch.highlighted)
                leftSwitch.an.draw(comp);
            if(rightSwitch.highlighted)
                rightSwitch.an.draw(comp);
            if(backButton.highlighted)
                backButton.an.draw(comp);
            if(saveButton.highlighted)
                saveButton.an.draw(comp);
        } else {
            flip.an.count = 0;
            flip.an.visible = false;
            leftSwitch.an.count = 0;
            leftSwitch.an.visible = false;
            rightSwitch.an.count = 0;
            rightSwitch.an.visible = false;
            backButton.an.count = 0;
            backButton.an.visible = false;
            saveButton.an.count = 0;
            saveButton.an.visible = false;
        }
        
        //draw the page number
        comp.setColor(Color.white);
        comp.drawString((page + 1) + "/" + Directory.PACK_LIMIT, MainPanel.width -
                (((page + 1) + "/" + Directory.PACK_LIMIT).length() * fontSize) / 2 - 15,
                MainPanel.navbarHeight - 20);
        
        comp.setColor(cardColor);
        comp.fillRect(MainPanel.width / 2 - (width / 2), MainPanel.height / 2 - (height / 2),
                width, height);
        comp.setStroke(new BasicStroke(2));
        
        int lineSpace = height / (lines + 1);
        
        //highlight the selected line if applicable
        if(selected > -1 && selected < lines + 1){
            comp.setColor(highlightColor);
            /* (hovered >= lines / 2 + 1 ? 1 * (hovered - lines / 2) : 0) gets rid of the glitch
            where the highlighted line goes out of its line. */
            comp.fillRect(MainPanel.width / 2 - (width / 2), MainPanel.height / 2 - (height / 2)
            + selected * lineSpace + (selected >= lines / 2 + 1 ? 1 * (selected - lines / 2) : 0),
                    width, lineSpace);
        }
        
        //highlight the hovered line if applicable
        if(hovered > -1 && hovered < lines + 1){
            comp.setColor(hoverColor);
            /* (hovered >= lines / 2 + 1 ? 1 * (hovered - lines / 2) : 0) gets rid of the glitch
            where the highlighted line goes out of its line. */
            comp.fillRect(MainPanel.width / 2 - (width / 2), MainPanel.height / 2 - (height / 2)
            + hovered * lineSpace + (hovered >= lines / 2 + 1 ? 1 * (hovered - lines / 2) : 0),
                    width, lineSpace);
        }
        
        //draw lines on back of cue card
        if(!front){
            for(int i = 0; i < lines; i++){
                if(i == 0)
                    comp.setColor(indexColor);
                else
                    comp.setColor(cardOutline);
                
                int yPoint = MainPanel.height / 2 - (height / 2) + height / (lines + 1)
                        + i * height / (lines + 1);
                comp.drawLine(MainPanel.width / 2 - (width / 2) + 1, yPoint,
                        MainPanel.width / 2 + (width / 2) - 1, yPoint);
            }
        }
        
        //draw the information onto the cue card
        comp.setColor(Color.black);
        for(int i = 0; i < (lines + 1); i++){
            comp.drawString(front ? frontSide[page][i] : backSide[page][i], MainPanel.width / 2 - (width / 2),
                    MainPanel.height / 2 - (height / 2) + height / (lines + 1) + (i * height / (lines + 1)));
        }
    }
    
    public static void update(){
        flip.update();
        leftSwitch.update();
        rightSwitch.update();
        backButton.update();
        saveButton.update();
    }
    
    public static void requestHighlight(int x, int y){
        flip.requestHighlight(x, y);
        leftSwitch.requestHighlight(x, y);
        rightSwitch.requestHighlight(x, y);
        backButton.requestHighlight(x, y);
        saveButton.requestHighlight(x, y);
        
        //check if the user hovers over a line of the cue card
        int top = MainPanel.height / 2 - (height / 2);
        int bottom = MainPanel.height / 2 + (height / 2);
        
        //if the user is hovering over the card
        boolean somethingHovered = false;
        if(y >= top + (height / (lines + 1)) / 4 * 3 - 2 &&
                y <= bottom + (height / (lines + 1)) / 4 * 3 - 2 &&
                x >= MainPanel.width / 2 - (width / 2)
                && x <= MainPanel.width / 2 + (width / 2)){
            for(int i = 0; i < lines + 1; i++)
                if(y >= top + i * (height / (lines + 1)) + (height / (lines + 1)) / 4 * 3 - 2
                        && y <= top + (i + 1) * (height / (lines + 1)) + (height / (lines + 1)) / 4 * 3 - 2){
                    hovered = i;
                    somethingHovered = true;
                }
        }
        if(!somethingHovered)
                hovered = -1;
    }
    
    public static void requestLeftClick(){
        flip.requestClick();
        leftSwitch.requestClick();
        rightSwitch.requestClick();
        backButton.requestClick();
        saveButton.requestClick();
        
        //selected where the user is hovering over
        if(hovered > -1)
            selected = hovered;
        else
            selected = -1;
    }
    
    public static void keyPressed(int keyCode, char key){
        switch(keyCode){
            case KeyEvent.VK_DOWN:
                if(selected < lines + 1)
                    selected++;
                else if(selected >= lines + 1)
                    selected = lines;
                break;
            case KeyEvent.VK_UP:
                if(selected < 0)
                    selected = 0;
                else if(selected >= lines + 1)
                    selected = lines;
                else
                    selected--;
                break;
                
            case KeyEvent.VK_BACK_SPACE:
                //backspace if possible
                if(front && frontSide[page][selected].length() > 0)
                        frontSide[page][selected] = frontSide[page][selected].substring(0, frontSide[page][selected].length() - 1);
                else if(!front && backSide[page][selected].length() > 0)
                    backSide[page][selected] = backSide[page][selected].substring(0, backSide[page][selected].length() - 1);

                //if the user backspaces on an empty line, delete the line, using reverse process of the enter key
                else if(front && frontSide[page][selected].trim().length() == 0){
                    for(int i = selected + 1; i <= lines; i++){
                        frontSide[page][i - 1] = frontSide[page][i];
                        frontSide[page][i] = "";
                    }
                    
                    selected--;
                } else if(!front && backSide[page][selected].trim().length() == 0
                        && selected > 0){
                    for(int i = selected + 1; i <= lines; i++){
                        backSide[page][i - 1] = backSide[page][i];
                        backSide[page][i] = "";
                    }
                    
                    selected--;
                }
                break;
                
            //the other keys that don't affect the string
            case KeyEvent.VK_ALT:
            case KeyEvent.VK_CAPS_LOCK:
            case KeyEvent.VK_CONTROL:
            case KeyEvent.VK_ESCAPE:
            case KeyEvent.VK_SHIFT:
                break;
                
            //change the page if the user hits right or left key
            case KeyEvent.VK_LEFT:
                leftSwitch.highlighted = true;
                break;
            case KeyEvent.VK_RIGHT:
                rightSwitch.highlighted = true;
                break;
            case KeyEvent.VK_BACK_QUOTE:
                flip.highlighted = true;
                break;
                    
            //if the user presses enter, make a new line if possible
            case KeyEvent.VK_ENTER:
                /*search for an empty line after all filled lines. If available, record the empty line
                number and send each line down in between the selected line and empty line.
                */
                boolean emptyLine, secondLastLine;
                int emptyLineNo;
                if(front){
                    emptyLine = frontSide[page][lines].trim().equals("");
                    secondLastLine = selected < lines - 1;
                    if(emptyLine && secondLastLine){
                        emptyLineNo = lines;
                        while(emptyLine){
                            frontSide[page][emptyLineNo] = frontSide[page][emptyLineNo - 1];
                            frontSide[page][emptyLineNo - 1] = "";
                            emptyLineNo--;

                            if(emptyLineNo == selected + 1)
                                emptyLine = false;
                        }
                        selected++;
                    } else if(emptyLine)
                        selected++;
                } else {
                    emptyLine = backSide[page][lines].trim().equals("");
                    secondLastLine = selected < lines - 1;
                    if(emptyLine && secondLastLine){
                        emptyLineNo = lines;
                        while(emptyLine){
                            backSide[page][emptyLineNo] = backSide[page][emptyLineNo - 1];
                            backSide[page][emptyLineNo - 1] = "";
                            emptyLineNo--;

                            if(emptyLineNo == selected + 1)
                                emptyLine = false;
                        }
                        selected++;
                    } else if(emptyLine)
                        selected++;
                }
                break;
                    
            default:
                if(front)
                    frontSide[page][selected] += key;
                else
                    backSide[page][selected] += key;
        }
    }
    
    public static void keyReleased(int keyCode){
        switch(keyCode){
            case KeyEvent.VK_LEFT:
                leftSwitch.highlighted = false;
                leftSwitch.execute();
                break;
            case KeyEvent.VK_RIGHT:
                rightSwitch.highlighted = false;
                rightSwitch.execute();
                break;
            case KeyEvent.VK_BACK_QUOTE:
                flip.highlighted = false;
                flip.execute();
                break;
        }
    }
}