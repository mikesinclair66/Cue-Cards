package navigation;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import main.MainPanel;
import pages.*;
import storage.CardPack;
import storage.Delete;
import storage.Folder;
import storage.Pack;

public class Directory {//TODO: scroll through folders, get rid of naming-pack bugs
    static Color folderColor = new Color(205, 255, 124);
    static Color folderOutline = new Color(99, 124, 0);
    static Color highlightColor = new Color(255, 255, 255, 200);
    
    public static int files;//number of files in the directory
    static boolean navigateFolders = true;//if the files are folders or cue-card packs
    
    static int width = 100, height = 100;
    final static int XSPACE = 30, YSPACE = 50;//space in between each folder
    
    final static int FONT_SIZE = 15;
    static Font font = new Font("Elephant", Font.BOLD, FONT_SIZE);
    public static boolean naming;//true is the user is currently naming a pack
    static String name = "";//the name that the user will make for the pack
    static int nameDisposition;//size of the name
    
    public final static int PACK_LIMIT = 50;//number of possible packs
    public static Pack[] packs = new Pack[PACK_LIMIT];
    
    static int filesPerRow = 1;
    static int fullRows = files / filesPerRow;
    static int extraFiles = files - fullRows * filesPerRow;
    public static int selected = -1;//the pack which is being selected
    
    public static Annotation an = new Annotation(true);
    public static boolean renaming;
    
    public static void draw(Graphics2D comp){
        comp.setColor(Color.black);
        comp.setFont(font);
        
        for(int i = 0; i < fullRows; i++)
            for(int j = 0; j < filesPerRow; j++){
                drawPackage(comp, j, i);
                comp.setColor(Color.black);
                if(packs[i * filesPerRow + j] != null)
                comp.drawString(packs[i * filesPerRow + j].name,
                        20 + j * (width + XSPACE) + width / 2 - 5 - packs[i * filesPerRow + j].name.length() * FONT_SIZE / 4,
                        90 + i * (height + YSPACE));
            }
        
        for(int i = 0; i < extraFiles; i++){
            drawPackage(comp, i, fullRows);
            comp.setColor(Color.black);
            if(packs[fullRows * filesPerRow + i] != null)
            comp.drawString(packs[fullRows * filesPerRow + i].name,
                    20 + i * (width + XSPACE) + width / 2 - 5 - packs[fullRows * filesPerRow + i].name.length() * FONT_SIZE / 4,
                    90 + fullRows * (height + YSPACE));
        }
        
        if(naming){
            comp.setColor(Color.black);
            nameDisposition = name.length() * FONT_SIZE / 4;
            comp.drawString(name + "_", 20 + (extraFiles - 1) * (width + XSPACE) + width / 2 - 5 - nameDisposition,
                    90 + (fullRows + 1) * (height + YSPACE) - height / 4);
        } else
            an.draw(comp);
    }
    
    public static void render(int filesNo){
        files = filesNo;
        
        filesPerRow = MainPanel.width / (width + XSPACE);
        fullRows = files / filesPerRow;
        extraFiles = files - fullRows * filesPerRow;
    }
    
    /** Draws either a folder or cue-card pack based on the page the program is in. */
    private static void drawPackage(Graphics2D comp, int x, int y){
        boolean thisSelected = selected == y * filesPerRow + x;//if this pack is highlighted
        
        if(navigateFolders){
            //back side of folder
            comp.setColor(folderColor);
            int[] xPoints = {20 + (width + XSPACE) * x, 20 + (width + XSPACE) * x + width / 5 * 3,
                20 + (width + XSPACE) * x + width / 5 * 4, 20 + (width + XSPACE) * x + width / 5};
            int[] yPoints = {90 + (height + YSPACE) * y, 90 + (height + YSPACE) * y,
                90 + (height + YSPACE) * y + height, 90 + (height + YSPACE) * y + height};
            comp.fillPolygon(xPoints, yPoints, 4);
            if(thisSelected){
                comp.setColor(highlightColor);
                comp.fillPolygon(xPoints, yPoints, 4);
            }
            
            //front side of folder
            xPoints[0] = 20 + (width + XSPACE) * x + width / 5 * 2;
            xPoints[1] = 20 + (width + XSPACE) * x + width;
            xPoints[2] = 20 + (width + XSPACE) * x + width / 5 * 4;
            xPoints[3] = 20 + (width + XSPACE) * x + width / 5;
            yPoints[0] = 90 + (height + YSPACE) * y + height / 5;
            yPoints[1] = yPoints[0];
            yPoints[2] = 90 + (height + YSPACE) * y + height;
            yPoints[3] = yPoints[2];
            comp.setColor(folderColor);
            comp.fillPolygon(xPoints, yPoints, 4);
            comp.setColor(folderOutline);
            comp.drawPolygon(xPoints, yPoints, 4);
            if(thisSelected){
                comp.setColor(highlightColor);
                comp.fillPolygon(xPoints, yPoints, 4);
            }
            
            //tab at top of folder
            int tabWidth = width / 3;
            xPoints[0] = 20 + (width + XSPACE) * x + tabWidth / 3;
            xPoints[1] = 20 + (width + XSPACE) * x + tabWidth / 3 * 2;
            xPoints[2] = 20 + (width + XSPACE) * x + tabWidth;
            xPoints[3] = 20 + (width + XSPACE) * x;
            yPoints[0] = 90 + (height + YSPACE) * y - height / 7;
            yPoints[1] = yPoints[0];
            yPoints[2] = 90 + (height + YSPACE) * y;
            yPoints[3] = yPoints[2];
            comp.setColor(folderColor);
            comp.fillPolygon(xPoints, yPoints, 4);
            if(thisSelected){
                comp.setColor(highlightColor);
                comp.fillPolygon(xPoints, yPoints, 4);
            }
            
            //draw lines to outline the back of the folder
            comp.setColor(folderOutline);
            comp.drawLine(20 + (width + XSPACE) * x + width / 5, 90 + (height + YSPACE) * y + height,
                    20 + (width + XSPACE) * x, 90 + (height + YSPACE) * y);
            //draw lines to outline the tab
            comp.drawLine(20 + (width + XSPACE) * x, 90 + (height + YSPACE) * y,
                    20 + (width + XSPACE) * x + tabWidth / 3, 90 + (height + YSPACE) * y - height / 7);
            comp.drawLine(20 + (width + XSPACE) * x + tabWidth / 3, 90 + (height + YSPACE) * y - height / 7,
                    20 + (width + XSPACE) * x + tabWidth / 3 * 2, 90 + (height + YSPACE) * y - height / 7);
            comp.drawLine(20 + (width + XSPACE) * x + tabWidth / 3 * 2, 90 + (height + YSPACE) * y - height / 7,
                    20 + (width + XSPACE) * x + tabWidth, 90 + (height + YSPACE) * y);
            comp.drawLine(20 + (width + XSPACE) * x + tabWidth, 90 + (height + YSPACE) * y,
                    20 + (width + XSPACE) * x + width / 5 * 3, 90 + (height + YSPACE) * y);
        } else {
            comp.setColor(folderColor);
            comp.fillRect(20 + (width + XSPACE) * x, 90 + (height + YSPACE) * y,
                        width, height);
            comp.setColor(folderOutline);
            comp.drawRect(20 + (width + XSPACE) * x, 90 + (height + YSPACE) * y,
                        width, height);
            if(thisSelected){
                comp.setColor(highlightColor);
                comp.fillRect(20 + (width + XSPACE) * x, 90 + (height + YSPACE) * y,
                        width, height);
            }
        }
    }
    
    /** Gets user input to name a pack. */
    public static void namePackage(int keyCode, char key){
        if(naming){
            switch(keyCode){
                case KeyEvent.VK_BACK_SPACE:
                    //backspace if possible
                    if(name.length() > 0)
                        name = name.substring(0, name.length() - 1);
                    break;
                case KeyEvent.VK_ENTER:
                    naming = false;
                    if(MainPanel.page == 0 && !renaming){
                        packs[files - 1] = new Folder(name);
                        Subject.folders[files - 1] = new Folder(name);
                    } else if(MainPanel.page == 1 && !renaming){
                        packs[files - 1] = new CardPack(name);
                        Subject.folders[Categories.curFolder].packs[files - 1] = (CardPack) packs[files - 1];
                        
                        //make all strings in the cardpack empty
                        for(int page = 0; page < PACK_LIMIT; page++){
                            for(int i = 0; i <= FlashCards.lines; i++){
                                Subject.folders[Categories.curFolder].packs[files - 1].frontSide[page][i] = "";
                                Subject.folders[Categories.curFolder].packs[files - 1].backSide[page][i] = "";
                                
                                //make FlashCard's cardpack strings empty
                                FlashCards.frontSide[page][i] = "";
                                FlashCards.backSide[page][i] = "";
                            }
                        }
                        Subject.folders[Categories.curFolder].packsNo++;
                        FlashCards.page = 0;
                    } else if(renaming){
                        packs[files - 1].setName(name);
                    }
                    name = "";
                    break;
                //the keys that don't affect the string
                case KeyEvent.VK_ALT:
                case KeyEvent.VK_CAPS_LOCK:
                case KeyEvent.VK_CONTROL:
                case KeyEvent.VK_DOWN:
                case KeyEvent.VK_ESCAPE:
                case KeyEvent.VK_LEFT:
                case KeyEvent.VK_RIGHT:
                case KeyEvent.VK_SHIFT:
                case KeyEvent.VK_UP:
                    break;
                default:
                    if(nameDisposition * 2 < width)
                        name += key;
            }
        }
    }
    
    /** Checks if the user is highlighting a pack. */
    public static void requestHighlight(int x, int y){
        boolean somethingSelected = false;
        
        for(int i = 0; i < fullRows; i++){
            for(int j = 0; j < filesPerRow; j++){
                if(x >= 20 + (width + XSPACE) * j + width / 12
                        && x <= 20 + (width + XSPACE) * j + width / 12 * 13
                        && y >= 90 + (height + YSPACE) * i + height / 3
                        && y <= 90 + (height + YSPACE) * i + height / 3 * 4){
                    somethingSelected = true;
                    selected = i * filesPerRow + j;
                }
            }
        }
        
        for(int i = 0; i < extraFiles; i++)
            if(x >= 20 + (width + XSPACE) * i + width / 12
                    && x <= 20 + (width + XSPACE) * i + width / 12 * 13
                    && y >= 90 + (height + YSPACE) * fullRows + height / 3
                    && y <= 90 + (height + YSPACE) * fullRows + height / 3 * 4){
                somethingSelected = true;
                selected = fullRows * filesPerRow + i;
            }
        
        if(!somethingSelected)
            selected = -1;
    }
    
    /** Checks if the user clicked a pack. */
    public static void requestLeftClick(){
        if(!naming && selected >= 0){
            if(MainPanel.page == 0 && !Subject.deleteButton.deleting){
                Categories.curFolder = selected;
                if(Subject.folders[Categories.curFolder] != null)
                    Categories.packsNo = Subject.folders[Categories.curFolder].packsNo;
                else
                    Categories.packsNo = 0;
                
                //put the cue cards into the directory
                for(int i = 0; i < Categories.packsNo; i++)
                    packs[i] = Subject.folders[Categories.curFolder].packs[i];
                //clear make every other slot null
                for(int i = Categories.packsNo; i < Directory.PACK_LIMIT; i++)
                    packs[i] = null;

                navigateFolders = false;
                MainPanel.page = 1;
                an.setMsg("Rename pack", "Delete pack");
            } else if(MainPanel.page == 0 && Subject.deleteButton.deleting){
                Delete.deleteFolder(selected);
                Subject.deleteButton.deleting = false;
            } else if(MainPanel.page == 1 && !Categories.deleteButton.deleting){
                FlashCards.curCardPack = selected;
                
                //load the flash cards
                for(int page = 0; page < PACK_LIMIT; page++){
                    for(int line = 0; line <= FlashCards.lines; line++){
                        FlashCards.frontSide[page][line]
                        = Subject.folders[Categories.curFolder].packs[FlashCards.curCardPack].frontSide[page][line];
                        FlashCards.backSide[page][line]
                        = Subject.folders[Categories.curFolder].packs[FlashCards.curCardPack].backSide[page][line];
                    }
                }

                MainPanel.page = 2;
            } else if(MainPanel.page == 1 && Categories.deleteButton.deleting){
                Delete.deleteCategory(selected);
                Categories.deleteButton.deleting = false;
            }
        }
    }
}